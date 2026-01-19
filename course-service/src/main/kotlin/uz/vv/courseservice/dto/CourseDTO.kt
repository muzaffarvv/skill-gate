package uz.vv.courseservice.dto
import jakarta.validation.constraints.*
import java.math.BigDecimal

data class CourseCU(
    @field:NotBlank(message = "Course title cannot be empty")
    @field:Size(min = 3, max = 72, message = "Title must be between 3 and 72 characters")
    val title: String,

    @field:Size(max = 120, message = "Description is too long (max 120 characters)")
    val description: String?,

    @field:NotNull(message = "Price is required")
    @field:DecimalMin(value = "0.0", inclusive = true, message = "Price must be zero or positive")
    val price: BigDecimal
)

data class CourseResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val price: BigDecimal,
    val lessons: List<LessonResponse> = emptyList(),
    val totalDuration: Int?
)

data class UserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val balance: BigDecimal,
)