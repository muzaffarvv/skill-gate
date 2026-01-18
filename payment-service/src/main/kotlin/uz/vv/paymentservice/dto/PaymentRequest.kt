package uz.vv.paymentservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class PaymentRequest(

    @field:NotBlank(message = "Phone number is required")
    @field:Pattern(
        regexp = "^[0-9]{9,15}$",
        message = "Phone number must contain only digits and be 9 to 15 digits long"
    )
    val phoneNumber: String,

    @field:NotNull(message = "Course ID is required")
    val courseId: Long
)
