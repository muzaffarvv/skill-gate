package uz.vv.userservice.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uz.vv.userservice.dto.ChangePasswordRequest
import uz.vv.userservice.dto.UserDTO
import uz.vv.userservice.dto.UserResponseDto
import uz.vv.userservice.service.UserService
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("/internal/count")
    fun getTotalCount(): ResponseEntity<Long> {
        return ResponseEntity.ok(userService.getTotalStudentsCount())
    }

    @GetMapping("/internal/new-count")
    fun getNewCount(): ResponseEntity<Long> {
        return ResponseEntity.ok(userService.getNewStudentsCount())
    }

    @PostMapping("/fill-balance")
    fun fillBalance(
        @RequestParam phoneNumber: String,
        @RequestParam amount: BigDecimal
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.fill(phoneNumber, amount))
    }

    @GetMapping("/phone/{phoneNumber}")
    fun getByPhone(@PathVariable phoneNumber: String): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.getByPhoneNumber(phoneNumber))
    }

    @PostMapping("/internal/withdraw")
    fun withdrawInternal(
        @RequestParam phoneNumber: String,
        @RequestParam amount: BigDecimal
    ): ResponseEntity<Boolean> {
        val result = userService.withdraw(phoneNumber, amount)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.getById(id))
    }

    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<UserResponseDto>> {
        return ResponseEntity.ok(userService.getAll(pageable))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody dto: UserDTO
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.update(id, dto))
    }

    @PatchMapping("/change-password")
    fun changePassword(
        @Valid @RequestBody request: ChangePasswordRequest
    ): ResponseEntity<Unit> {
        userService.changePassword(request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}