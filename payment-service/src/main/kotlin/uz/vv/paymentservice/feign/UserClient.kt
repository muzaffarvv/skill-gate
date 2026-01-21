package uz.vv.paymentservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import uz.vv.paymentservice.dto.BalanceRequest
import uz.vv.paymentservice.dto.UserResponseDto

@FeignClient(name = "user-service")
interface UserClient {

    @GetMapping("/api/v1/users/phone/{phoneNumber}")
    fun getByPhone(@PathVariable phoneNumber: String): UserResponseDto

    @PostMapping("/api/v1/users/internal/withdraw")
    fun withdraw(@RequestBody request: BalanceRequest): Boolean
}