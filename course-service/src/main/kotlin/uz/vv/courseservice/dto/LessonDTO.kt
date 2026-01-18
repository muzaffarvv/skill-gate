package uz.vv.courseservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import uz.vv.courseservice.enum.LessonType

data class LessonCU(
    @field:NotBlank(message = "Lesson topic is required")
    @field:Size(min = 5, max = 120, message = "Topic must be between 5 and 120 characters")
    val topic: String,

    @field:NotNull(message = "Content type (VIDEO, SLIDE, etc.) is required")
    val contentType: LessonType,

    @field:NotBlank(message = "Content URL or path cannot be empty")
    val contentUrl: String,

    @field:NotNull(message = "Course ID is mandatory to link the lesson")
    val courseId: Long
)

data class LessonResponse(
    val id: Long?,
    val topic: String?,
    val contentType: LessonType,
    val contentUrl: String?
)