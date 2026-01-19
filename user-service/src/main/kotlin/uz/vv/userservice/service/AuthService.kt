package uz.vv.userservice.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.vv.userservice.dto.LoginRequest
import uz.vv.userservice.dto.RegisterRequest
import uz.vv.userservice.dto.UserDTO
import uz.vv.userservice.dto.UserResponseDto
import uz.vv.userservice.enum.ErrorCodes
import uz.vv.userservice.exception.UserNotFoundException
import uz.vv.userservice.mapper.UserMapper

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun register(dto: RegisterRequest): UserResponseDto {
        val userDTO = UserDTO(
            firstName = dto.firstName,
            lastName = dto.lastName,
            phoneNumber = dto.phoneNumber,
            password = dto.password
        )
        return userService.create(userDTO)
    }

    @Transactional(readOnly = true)
    fun login(dto: LoginRequest): UserResponseDto {
        val user = userService.getUserByPhoneNumber(dto.phoneNumber)

        if (!passwordEncoder.matches(dto.password, user.password)) {
            throw UserNotFoundException(ErrorCodes.UNAUTHORIZED)
        }

        return UserMapper.toDto(user)
    }
}