package uz.vv.paymentservice.dto

import java.math.BigDecimal
import java.time.Instant

data class TransactionDTO(
    val id: Long,
    val phoneNumber: String,
    val amount: BigDecimal,
    val courseId: Long,
    val status: String,
    val createdAt: Instant
)
