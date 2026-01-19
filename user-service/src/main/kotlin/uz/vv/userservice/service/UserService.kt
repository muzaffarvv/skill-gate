package uz.vv.userservice.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.vv.userservice.base.BaseServiceImpl
import uz.vv.userservice.config.BalanceOperation
import uz.vv.userservice.dto.ChangePasswordRequest
import uz.vv.userservice.dto.UserDTO
import uz.vv.userservice.dto.UserResponseDto
import uz.vv.userservice.entity.Role
import uz.vv.userservice.entity.User
import uz.vv.userservice.enum.ErrorCodes
import uz.vv.userservice.exception.AlreadyExistsException
import uz.vv.userservice.exception.RoleNotFoundException
import uz.vv.userservice.exception.UserNotFoundException
import uz.vv.userservice.mapper.UserMapper
import uz.vv.userservice.repo.RoleRepo
import uz.vv.userservice.repo.UserRepo
import java.math.BigDecimal
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.springframework.beans.factory.annotation.Value
import uz.vv.userservice.dto.BalanceRequest

@Service
class UserService(
    repository: UserRepo,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepo: RoleRepo,
    @Value("\${user.signup}")
    private val signupBonus: Int,
    @Value("\${user.new-window-days}")
    private val newWindowDays: Long,
) : BaseServiceImpl<User, UserDTO, UserResponseDto, UserMapper, UserRepo>(
    repository,
    UserMapper
), BalanceOperation {

    companion object {
        private const val ROLE_STUDENT = "STUDENT"
    }

    fun getTotalStudentsCount(): Long {
        return repository.countByDeletedFalse()
    }

    fun getNewStudentsCount(): Long {
        val threshold = Instant.now().minus(newWindowDays, ChronoUnit.DAYS)
        return repository.countByCreatedAtAfterAndDeletedFalse(threshold)
    }

    @Transactional
    override fun withdraw(request: BalanceRequest): Boolean {
        val user = getUserByPhoneNumber(request.phoneNumber)

        if (user.balance < request.amount) return false

        user.balance = user.balance.subtract(request.amount)
        repository.save(user)
        return true
    }

    @Transactional
    override fun fill(request: BalanceRequest): UserResponseDto {
        val user = getUserByPhoneNumber(request.phoneNumber)
        user.balance = user.balance.add(request.amount)
        val saved = repository.save(user)
        return mapper.toDto(saved)
    }

    @Transactional
    override fun create(dto: UserDTO): UserResponseDto {
        val user = toEntity(dto)

        val defaultRole = getRoleByCode(ROLE_STUDENT)
        user.roles.add(defaultRole)
        user.balance = signupBonus.toBigDecimal()

        val saved = repository.saveAndRefresh(user)
        return mapper.toDto(saved)
    }

    override fun updateEntity(dto: UserDTO, entity: User): User {
        if (entity.phoneNumber != dto.phoneNumber) {
            checkPhoneExists(dto.phoneNumber)
        }

        entity.firstName = dto.firstName
        entity.lastName = dto.lastName
        entity.phoneNumber = dto.phoneNumber

        return entity
    }

    override fun toEntity(dto: UserDTO): User {
        checkPhoneExists(dto.phoneNumber)
        val user = UserMapper.toEntity(dto)
        user.password = passwordEncoder.encode(dto.password)
        return user
    }

    @Transactional // only admin will change the password
    fun changePassword(dto : ChangePasswordRequest) {
        val user = getUserByPhoneNumber(dto.phoneNumber)
        user.password = passwordEncoder.encode(dto.newPassword)
        repository.save(user)
    }

    @Transactional(readOnly = true)
    fun getByPhoneNumber(phoneNumber: String): UserResponseDto {
        return mapper.toDto(getUserByPhoneNumber(phoneNumber))
    }

    fun getRoleByCode(code: String): Role =
        roleRepo.findByCode(code) ?:
        throw RoleNotFoundException(ErrorCodes.ROLE_NOT_FOUND)

    fun getUserByPhoneNumber(phoneNumber: String): User =
        repository.findByPhoneNumberAndDeletedFalse(phoneNumber)
            ?: throw UserNotFoundException(ErrorCodes.USER_NOT_FOUND)

    override fun getUserById(id: Long): User =
        repository.findByIdAndDeletedFalse(id)
            ?: throw UserNotFoundException(ErrorCodes.USER_NOT_FOUND)

    fun checkPhoneExists(phoneNumber: String) {
        if (repository.existsByPhoneNumberAndDeletedFalse(phoneNumber)) {
            throw AlreadyExistsException(ErrorCodes.PHONE_NUMBER_ALREADY_EXISTS)
        }
    }
}