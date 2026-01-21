package uz.vv.courseservice.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import uz.vv.courseservice.dto.CourseCU
import uz.vv.courseservice.dto.CourseResponse
import uz.vv.courseservice.feign.UserClient
import uz.vv.courseservice.service.CourseService
import feign.FeignException

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(
    private val service: CourseService,
    private val userClient: UserClient
) {

    @PostMapping
    fun create(@Valid @RequestBody dto: CourseCU): ResponseEntity<CourseResponse> {
        val created = service.create(dto)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.id)
            .toUri()
        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(created)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody dto: CourseCU
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(service.getById(id))
    }

    @GetMapping("/all")
    fun getAll(pageable: Pageable) = ResponseEntity.ok(service.getAll(pageable))

    @GetMapping("/list")
    fun getList() = ResponseEntity.ok(service.getAllList())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/enroll")
    fun enroll(@PathVariable id: Long, @RequestParam studentId: Long): ResponseEntity<Unit> {
        service.enrollStudent(id, studentId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/user/{id}")
    fun getUserCourseDetails(@PathVariable id: Long): ResponseEntity<List<CourseResponse>> {
        return try {
            val user = userClient.getUserById(id)
            val courses = service.getCoursesByUser(user.id)
            ResponseEntity.ok(courses)
        } catch (e: FeignException.NotFound) {
            println("Feign xatosi: ${e.message}")
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (e: FeignException) {
            println("Feign xatosi: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()
        }
    }
}