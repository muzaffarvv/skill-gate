package uz.vv.userservice.base

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BaseService<CreateDTO, ResponseDTO> {
    fun create(dto: CreateDTO): ResponseDTO
    fun update(id: Long, dto: CreateDTO): ResponseDTO
    fun getById(id: Long): ResponseDTO
    fun getAllList(): List<ResponseDTO>
    fun getAll(pageable: Pageable): Page<ResponseDTO>
    fun delete(id: Long)
}