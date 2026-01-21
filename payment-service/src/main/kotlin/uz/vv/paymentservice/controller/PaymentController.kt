package uz.vv.paymentservice.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uz.vv.paymentservice.dto.PaymentRequest
import uz.vv.paymentservice.dto.TransactionDTO
import uz.vv.paymentservice.service.PaymentService
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController(
    private val paymentService: PaymentService
) {

    @PostMapping("/buy")
    fun buy(@RequestBody request: PaymentRequest): ResponseEntity<String> {
        return try {
            paymentService.buyCourse(request)
            ResponseEntity
                .status(HttpStatus.OK)
                .body("Course purchased successfully")
        } catch (ex: uz.vv.paymentservice.exception.DuplicatePurchaseException) {
            ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.message ?: "You already own this course")
        } catch (ex: uz.vv.paymentservice.exception.InsufficientBalanceException) {
            ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(ex.message ?: "Insufficient balance. Please top up your account.")
        }
    }

    @GetMapping("/courses/{phoneNumber}")
    fun coursesByPhoneNumber(@PathVariable phoneNumber: String) = ResponseEntity.ok(
        paymentService.getCoursesByUserId(phoneNumber)
    )

    @GetMapping("/internal/total-earnings")
    fun getTotalEarnings(): ResponseEntity<BigDecimal> {
        val result = paymentService.getTotalEarnings()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/internal/monthly-earnings")
    fun getMonthlyEarnings(): ResponseEntity<BigDecimal> {
        val result = paymentService.getMonthlyEarnings()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/internal/recent-transactions")
    fun getRecentTransactions(
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<List<TransactionDTO>> {
        val result = paymentService.getRecentTransactions(limit)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/internal/most-popular-course")
    fun getMostPopularCourse(): ResponseEntity<Long?> {
        val result = paymentService.getMostPopularCourseId()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/internal/success-count")
    fun getCountOfSuccessfulPayments(): ResponseEntity<Long> {
        val result = paymentService.getCountOfSuccessfulPayments()
        return ResponseEntity.ok(result)
    }
}
