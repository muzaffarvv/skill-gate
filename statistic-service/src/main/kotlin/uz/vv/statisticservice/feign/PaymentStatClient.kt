package uz.vv.statisticservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import uz.vv.statisticservice.dto.TransactionDTO
import java.math.BigDecimal

@FeignClient(name = "payment-service")
interface PaymentStatClient {
    @GetMapping("/api/v1/payments/internal/total-earnings")
    fun getTotalEarnings(): BigDecimal

    @GetMapping("/api/v1/payments/internal/monthly-earnings")
    fun getMonthlyEarnings(): BigDecimal

    @GetMapping("/api/v1/payments/internal/recent-transactions")
    fun getRecentTransactions(@RequestParam limit: Int): List<TransactionDTO>

    @GetMapping("/api/v1/payments/internal/most-popular-course")
    fun getMostPopularCourseId(): Long?

    @GetMapping("/api/v1/payments/internal/success-count")
    fun getCountOfSuccessfulPayments(): Long
}