package uz.vv.courseservice.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uz.vv.courseservice.dto.LessonCU
import uz.vv.courseservice.dto.LessonResponse
import uz.vv.courseservice.service.LessonService

@RestController
@RequestMapping("/api/v1/lessons")
class LessonController(
    private val service: LessonService
) {

    @PostMapping
    fun create(@Valid @RequestBody dto: LessonCU): ResponseEntity<LessonResponse> {
        return ResponseEntity.ok(service.create(dto))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody dto: LessonCU
    ): ResponseEntity<LessonResponse> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<LessonResponse> {
        return ResponseEntity.ok(service.getById(id))
    }

    @GetMapping("/by-course/{courseId}")
    fun getByCourseId(@PathVariable courseId: Long): ResponseEntity<List<LessonResponse>> {
        return ResponseEntity.ok(service.getLessonsByCourseId(courseId))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}