package uz.vv.userservice.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import uz.vv.userservice.dto.LoginRequest
import uz.vv.userservice.dto.RegisterRequest
import uz.vv.userservice.dto.UserResponseDto
import uz.vv.userservice.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody dto: RegisterRequest): String {
        return authService.register(dto)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody dto: LoginRequest): UserResponseDto {
        return authService.login(dto)
    }
}