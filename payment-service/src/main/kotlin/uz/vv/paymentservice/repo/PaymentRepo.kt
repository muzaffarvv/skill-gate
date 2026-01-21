package uz.vv.paymentservice.repo

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import uz.vv.paymentservice.entity.Payment
import uz.vv.paymentservice.enum.PaymentStatus
import java.math.BigDecimal
import java.time.Instant

interface PaymentRepo : JpaRepository<Payment, Long> {

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentStatus = uz.vv.paymentservice.enum.PaymentStatus.SUCCESS")
    fun sumTotalEarnings(): BigDecimal

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentStatus = uz.vv.paymentservice.enum.PaymentStatus.SUCCESS AND p.createdAt >= :startOfMonth")
    fun sumMonthlyEarnings(startOfMonth: Instant): BigDecimal

    fun findTop10ByPaymentStatusOrderByCreatedAtDesc(status: PaymentStatus): List<Payment>

    @Query("SELECT p.courseId FROM Payment p WHERE p.paymentStatus = uz.vv.paymentservice.enum.PaymentStatus.SUCCESS GROUP BY p.courseId ORDER BY COUNT(p.courseId) DESC")
    fun findTopSellingCourseIds(pageable: Pageable): List<Long>

    fun countByPaymentStatus(status: PaymentStatus): Long

    fun findCoursesIdByPhoneNumber(phoneNumber: String): List<Long>

    fun existsByPhoneNumberAndCourseIdAndPaymentStatus(phoneNumber: String, courseId: Long, paymentStatus: PaymentStatus): Boolean
}