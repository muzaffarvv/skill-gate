package uz.vv.userservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class RoleDTO(
    @field:NotBlank(message = "Role code is required")
    @field:Size(min = 3, message = "Role code must be at least 3 characters long")
    @field:Size(max = 16, message = "Role code must not exceed 16 characters")
    val code: String,

    @field:NotBlank(message = "Role name is required")
    @field:Size(min = 3, message = "Role name must be at least 3 characters long")
    @field:Size(max = 38, message = "Role name must not exceed 38 characters")
    val name: String,

    @field:NotEmpty(message = "Role must have at least one permission")
    val permissions: List<PermissionDTO>
)