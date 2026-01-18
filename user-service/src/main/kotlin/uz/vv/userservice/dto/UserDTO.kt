package uz.vv.userservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class UserDTO(

    @field:NotBlank(message = "First name is required")
    @field:Size(min = 3, max = 72, message = "First name must be between 2 and 50 characters")
    @field:Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    @field:Size(min = 5, max = 60, message = "Last name must be between 2 and 50 characters")
    @field:Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    val lastName: String,

    @field:NotBlank(message = "Phone number is required")
    @field:Pattern(
        regexp = "^[0-9]{9,15}$",
        message = "Phone number must be digits only and between 9 to 15 digits long"
    )
    val phoneNumber: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    @field:Size(max = 120, message = "Password must not exceed 120 characters")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[@#$%!?&]).*$",
        message = "Password must contain at least one digit and one special character"
    )
    val password: String
)

data class UserResponseDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val balance: BigDecimal,
    val roles: List<RoleDTO> = emptyList()
)

data class RegisterRequest(
    @field:NotBlank(message = "First name is required")
    @field:Size(min = 3, max = 72)
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    @field:Size(min = 5, max = 60)
    val lastName: String,

    @field:NotBlank(message = "Phone number is required")
    @field:Pattern(regexp = "^[0-9]{9,15}$")
    val phoneNumber: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 120)
    @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%!?&]).*$")
    val password: String
)

data class LoginRequest(
    @field:NotBlank(message = "Phone number is required")
    val phoneNumber: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)

data class ChangePasswordRequest(
    val phoneNumber: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 120, message = "Password must be between 6 and 120 characters")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[@#$%!?&]).*$",
        message = "Password must contain at least one digit and one special character (@#$%!?&)"
    )
    val newPassword: String
)