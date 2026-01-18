package uz.vv.paymentservice.feign


import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal

@FeignClient(name = "user-service")
interface UserClient {
    @PostMapping("/api/v1/users/internal/withdraw")
    fun withdraw(
        @RequestParam phoneNumber: String,
        @RequestParam amount: BigDecimal
    ): Boolean
}