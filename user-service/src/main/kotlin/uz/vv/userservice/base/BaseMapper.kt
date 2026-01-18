package uz.vv.userservice.base

interface BaseMapper<E, R> {
    fun toDto(entity: E): R
    fun toDtoList(entities: List<E>): List<R> = entities.map { toDto(it) }
}