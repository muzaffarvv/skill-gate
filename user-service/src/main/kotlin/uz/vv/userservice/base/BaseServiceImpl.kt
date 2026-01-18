package uz.vv.userservice.base

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import uz.vv.userservice.enum.ErrorCodes
import uz.vv.userservice.exception.UserNotFoundException

abstract class BaseServiceImpl<
        E : BaseEntity,
        CreateDTO,
        ResponseDTO,
        Mapper : BaseMapper<E, ResponseDTO>,
        Repo : BaseRepo<E>
        >(
    protected val repository: Repo,
    protected val mapper: Mapper
) : BaseService<CreateDTO, ResponseDTO> {

    protected abstract fun toEntity(dto: CreateDTO): E
    protected abstract fun updateEntity(dto: CreateDTO, entity: E): E
    protected abstract fun getUserById(id: Long): E

    @Transactional
    override fun create(dto: CreateDTO): ResponseDTO {
        val entity = toEntity(dto)
        val saved = repository.saveAndRefresh(entity)
        return mapper.toDto(saved)
    }

    @Transactional
    override fun update(id: Long, dto: CreateDTO): ResponseDTO {
        val existingEntity = getUserById(id)

        val updatedEntity = updateEntity(dto, existingEntity)

        val saved = repository.saveAndRefresh(updatedEntity)
        return mapper.toDto(saved)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): ResponseDTO {
        val entity = getUserById(id)
        return mapper.toDto(entity)
    }

    @Transactional(readOnly = true)
    override fun getAllList(): List<ResponseDTO> =
        repository.findAllNotDeleted().map { mapper.toDto(it) }

    @Transactional(readOnly = true)
    override fun getAll(pageable: Pageable): Page<ResponseDTO> =
        repository.findAllNotDeleted(pageable).map { mapper.toDto(it) }

    @Transactional
    override fun delete(id: Long) {
        repository.trash(id) ?: throw UserNotFoundException(ErrorCodes.USER_NOT_FOUND)
    }


}