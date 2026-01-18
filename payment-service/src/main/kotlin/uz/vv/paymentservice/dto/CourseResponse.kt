package uz.vv.paymentservice.dto

import uz.vv.paymentservice.enum.LessonType
import java.math.BigDecimal

data class CourseResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val price: BigDecimal,
    val lessons: List<LessonResponse> = emptyList(),
    val totalDuration: Int?
)

data class LessonResponse(
    val id: Long?,
    val topic: String?,
    val contentType: LessonType,
    val contentUrl: String?
)