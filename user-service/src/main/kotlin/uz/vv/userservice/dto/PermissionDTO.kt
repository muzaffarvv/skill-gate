package uz.vv.userservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class PermissionDTO(
    @field:NotBlank(message = "Permission code is required")
    @field:Size(min = 3, message = "Permission code must be at least 3 characters long")
    @field:Size(max = 34, message = "Permission code must not exceed 34 characters")
    val code: String,

    @field:NotBlank(message = "Permission name is required")
    @field:Size(min = 3, message = "Permission name must be at least 3 characters long")
    @field:Size(max = 128, message = "Permission name must not exceed 128 characters")
    val name: String
)