package uz.vv.courseservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import uz.vv.courseservice.dto.UserResponse

@FeignClient(name = "user-service")
interface UserClient {
    @GetMapping("/api/v1/users/{id}")
    fun getUserById(@PathVariable id: Long): UserResponse
}
