package uz.vv.userservice.mapper

import uz.vv.userservice.base.BaseMapper
import uz.vv.userservice.dto.PermissionDTO
import uz.vv.userservice.dto.RoleDTO
import uz.vv.userservice.dto.UserDTO
import uz.vv.userservice.dto.UserResponseDto
import uz.vv.userservice.entity.Permission
import uz.vv.userservice.entity.Role
import uz.vv.userservice.entity.User

object UserMapper : BaseMapper<User, UserResponseDto> {
    override fun toDto(entity: User): UserResponseDto = UserResponseDto(
        id = entity.id!!,
        firstName = entity.firstName!!,
        lastName = entity.lastName!!,
        phoneNumber = entity.phoneNumber!!,
        balance = entity.balance,
        roles = RoleMapper.toDtoList(entity.roles)
    )

    fun toEntity(dto: UserDTO): User = User().apply {
        firstName = dto.firstName
        lastName = dto.lastName
        phoneNumber = dto.phoneNumber
        password = dto.password
    }
}

object RoleMapper : BaseMapper<Role, RoleDTO> {
    override fun toDto(entity: Role): RoleDTO = RoleDTO(
        code = entity.code!!,
        name = entity.name!!,
        permissions = PermissionMapper.toDtoList(entity.permissions)
    )

    fun toEntity(dto: RoleDTO): Role = Role().apply {
        code = dto.code
        name = dto.name
        permissions = mutableListOf()
    }

}


object PermissionMapper : BaseMapper<Permission, PermissionDTO> {
    override fun toDto(entity: Permission): PermissionDTO = PermissionDTO(
        code = entity.code!!,
        name = entity.name!!
    )

    fun toEntity(dto: PermissionDTO): Permission = Permission().apply {
        code = dto.code
        name = dto.name
    }
}
