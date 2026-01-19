package uz.vv.courseservice.repo

import org.springframework.stereotype.Repository
import uz.vv.courseservice.base.BaseRepo
import uz.vv.courseservice.entity.Course
import java.math.BigDecimal

@Repository
interface CourseRepo : BaseRepo<Course> {
    fun findCoursesByPrice( price: BigDecimal) : List<Course>
    fun findAllByStudentsIdsContaining(userId: Long): List<Course>
}