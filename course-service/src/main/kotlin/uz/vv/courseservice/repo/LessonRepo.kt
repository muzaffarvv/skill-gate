package uz.vv.courseservice.repo

import org.springframework.stereotype.Repository
import uz.vv.courseservice.base.BaseRepo
import uz.vv.courseservice.entity.Lesson

@Repository
interface LessonRepo : BaseRepo<Lesson> {
    fun findByCourseIdAndDeletedFalse(courseId: Long): List<Lesson>
}