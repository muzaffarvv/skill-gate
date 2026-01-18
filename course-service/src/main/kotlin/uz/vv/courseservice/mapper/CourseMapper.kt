package uz.vv.courseservice.mapper

import uz.vv.courseservice.base.BaseMapper
import uz.vv.courseservice.entity.Course
import uz.vv.courseservice.dto.CourseResponse
import uz.vv.courseservice.dto.CourseCU

object CourseMapper : BaseMapper<Course, CourseResponse> {

    override fun toDto(entity: Course): CourseResponse {
        return CourseResponse(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            price = entity.price,
            totalDuration = 0
        )
    }

    fun toEntity(dto: CourseCU) = Course (
        title = dto.title,
        description = dto.description,
        price = dto.price
    )
}