package uz.vv.paymentservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import uz.vv.paymentservice.dto.CourseResponse

@FeignClient(name = "course-service")
interface CourseClient {

    @GetMapping("/api/v1/courses/{id}")
    fun getCourseById(@PathVariable id: Long): CourseResponse

    @PostMapping("/api/v1/courses/{id}/enroll")
    fun enrollStudent(
        @PathVariable id: Long,
        @RequestParam studentPhone: String
    )
}