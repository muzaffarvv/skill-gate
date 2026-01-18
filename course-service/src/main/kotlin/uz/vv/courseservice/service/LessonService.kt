package uz.vv.courseservice.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.vv.courseservice.base.BaseServiceImpl
import uz.vv.courseservice.dto.LessonCU
import uz.vv.courseservice.dto.LessonResponse
import uz.vv.courseservice.entity.Lesson
import uz.vv.courseservice.enum.ErrorCodes
import uz.vv.courseservice.exception.EntityNotFound
import uz.vv.courseservice.mapper.LessonMapper
import uz.vv.courseservice.repo.LessonRepo

@Service
class LessonService(
    repository: LessonRepo,
    private val courseService: CourseService
) : BaseServiceImpl<Lesson, LessonCU, LessonResponse, LessonMapper, LessonRepo>(
    repository,
    LessonMapper
) {

    @Transactional
    override fun toEntity(dto: LessonCU): Lesson {
        val lesson = LessonMapper.toEntity(dto)
        val course = courseService.getEntityById(dto.courseId)
        lesson.course = course
        return lesson
    }

    @Transactional
    override fun updateEntity(dto: LessonCU, entity: Lesson): Lesson {
        entity.topic = dto.topic
        entity.contentType = dto.contentType
        entity.contentUrl = dto.contentUrl

        if (entity.course?.id != dto.courseId) {
            val newCourse = courseService.getEntityById(dto.courseId)
            entity.course = newCourse
        }

        return entity
    }

    override fun getEntityById(id: Long): Lesson {
        return repository.findByIdAndDeletedFalse(id)
            ?: throw EntityNotFound(ErrorCodes.LESSON_NOT_FOUND)
    }

    fun getLessonsByCourseId(courseId: Long): List<LessonResponse> {
        val lessons = repository.findByCourseIdAndDeletedFalse(courseId)
        return LessonMapper.toDtoList(lessons)
    }

    override fun delete(id: Long) {
        repository.trash(id) ?: throw EntityNotFound(ErrorCodes.LESSON_NOT_FOUND)
    }

}