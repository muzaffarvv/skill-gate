package uz.vv.courseservice.mapper

import uz.vv.courseservice.base.BaseMapper
import uz.vv.courseservice.dto.LessonCU
import uz.vv.courseservice.dto.LessonResponse
import uz.vv.courseservice.entity.Lesson

object LessonMapper : BaseMapper<Lesson, LessonResponse> {
    override fun toDto(entity: Lesson) = LessonResponse(
        id = entity.id,
        topic = entity.topic,
        contentType = entity.contentType,
        contentUrl = entity.contentUrl
    )

    fun toEntity(dto: LessonCU) = Lesson(
        topic = dto.topic,
        contentType = dto.contentType,
        contentUrl = dto.contentUrl
    )
}