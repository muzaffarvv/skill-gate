package uz.vv.paymentservice.service

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.vv.paymentservice.dto.BalanceRequest
import uz.vv.paymentservice.dto.CourseResponse
import uz.vv.paymentservice.dto.PaymentRequest
import uz.vv.paymentservice.dto.TransactionDTO
import uz.vv.paymentservice.entity.Payment
import uz.vv.paymentservice.enum.PaymentStatus
import uz.vv.paymentservice.feign.CourseClient
import uz.vv.paymentservice.feign.UserClient
import uz.vv.paymentservice.repo.PaymentRepo
import uz.vv.paymentservice.exception.DuplicatePurchaseException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId

@Service
class PaymentService(
    private val paymentRepo: PaymentRepo,
    private val userClient: UserClient,
    private val courseClient: CourseClient
) {

    @Transactional
    fun buyCourse(request: PaymentRequest) {
        val alreadyOwned = paymentRepo.existsByPhoneNumberAndCourseIdAndPaymentStatus(
            request.phoneNumber,
            request.courseId,
            PaymentStatus.SUCCESS
        )
        if (alreadyOwned) {
            throw DuplicatePurchaseException("You already own this course")
        }

        val course = courseClient.getCourseById(request.courseId)
        val user = userClient.getByPhone(request.phoneNumber)

        val userBalance = user.balance
        if (userBalance == null || userBalance < course.price) {
            throw uz.vv.paymentservice.exception.InsufficientBalanceException(
                "Insufficient balance. Please top up your account."
            )
        }

        val payment = Payment(
            phoneNumber = request.phoneNumber,
            courseId = request.courseId,
            amount = course.price,
            paymentStatus = PaymentStatus.PENDING
        )
        val savedPayment = paymentRepo.save(payment)

        try {
            val isWithdrawn = userClient.withdraw(BalanceRequest(request.phoneNumber, course.price))
            if (isWithdrawn) {
                courseClient.enrollStudent(request.courseId, user.id)
                savedPayment.paymentStatus = PaymentStatus.SUCCESS
            } else {
                savedPayment.paymentStatus = PaymentStatus.FAILED
            }
        } catch (ex: Exception) {
            savedPayment.paymentStatus = PaymentStatus.FAILED
        } finally {
            paymentRepo.save(savedPayment)
        }
    }

    @Transactional(readOnly = true)
    fun getCoursesByUserId(phoneNumber: String): List<CourseResponse> {
        val coursesIds: List<Long> = paymentRepo.findCoursesIdByPhoneNumber(phoneNumber)

        return coursesIds.map { id ->
            courseClient.getCourseById(id)
        }
    }

    @Transactional(readOnly = true)
    fun getTotalEarnings(): BigDecimal = paymentRepo.sumTotalEarnings()

    @Transactional(readOnly = true)
    fun getMonthlyEarnings(): BigDecimal {
        val startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
        return paymentRepo.sumMonthlyEarnings(startOfMonth)
    }

    @Transactional(readOnly = true)
    fun getRecentTransactions(limit: Int): List<TransactionDTO> {
        val items = paymentRepo.findTop10ByPaymentStatusOrderByCreatedAtDesc(PaymentStatus.SUCCESS)
        return items.take(limit.coerceIn(1, 50)).map {
            TransactionDTO(
                id = it.id!!,
                phoneNumber = it.phoneNumber!!,
                amount = it.amount,
                courseId = it.courseId!!,
                status = it.paymentStatus.name,
                createdAt = it.createdAt!!
            )
        }
    }

    @Transactional(readOnly = true)
    fun getMostPopularCourseId(): Long? =
        paymentRepo.findTopSellingCourseIds(PageRequest.of(0, 1)).firstOrNull()

    @Transactional(readOnly = true)
    fun getCountOfSuccessfulPayments(): Long = paymentRepo.countByPaymentStatus(PaymentStatus.SUCCESS)
}