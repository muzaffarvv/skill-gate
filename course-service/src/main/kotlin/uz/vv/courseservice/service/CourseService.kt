package uz.vv.courseservice.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.vv.courseservice.base.BaseServiceImpl
import uz.vv.courseservice.dto.CourseCU
import uz.vv.courseservice.dto.CourseResponse
import uz.vv.courseservice.entity.Course
import uz.vv.courseservice.enum.ErrorCodes
import uz.vv.courseservice.exception.EntityNotFound
import uz.vv.courseservice.mapper.CourseMapper
import uz.vv.courseservice.repo.CourseRepo

@Service
class CourseService(
    repository: CourseRepo,
) : BaseServiceImpl<Course, CourseCU, CourseResponse, CourseMapper, CourseRepo>(
    repository,
    CourseMapper
) {

    @Transactional
    override fun toEntity(dto: CourseCU): Course {
        val course = CourseMapper.toEntity(dto)
        course.soldCount = 0
        course.studentsIds = mutableListOf()
        return course
    }

    @Transactional
    override fun updateEntity(dto: CourseCU, entity: Course): Course {
        entity.title = dto.title
        entity.description = dto.description
        entity.price = dto.price
        return entity
    }

    override fun getEntityById(id: Long): Course {
        return repository.findByIdAndDeletedFalse(id)
            ?: throw EntityNotFound(ErrorCodes.COURSE_NOT_FOUND)
    }

    @Transactional
    fun enrollStudent(courseId: Long, studentId: Long) {
        val course = getEntityById(courseId)

        if (!course.studentsIds.contains(studentId)) {
            course.studentsIds.add(studentId)
            course.soldCount += 1
            repository.save(course)
        }
    }
}