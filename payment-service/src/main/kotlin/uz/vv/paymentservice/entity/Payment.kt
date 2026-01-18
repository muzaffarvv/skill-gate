package uz.vv.paymentservice.entity

import jakarta.persistence.*
import uz.vv.paymentservice.base.BaseEntity
import uz.vv.paymentservice.enum.PaymentStatus
import uz.vv.paymentservice.enum.PaymentType
import java.math.BigDecimal

@Entity
@Table(name = "payments")
class Payment(
    @Column(nullable = false)
    var phoneNumber: String? = null,

    @Column(nullable = false)
    var courseId: Long? = null,

    @Column(nullable = false, precision = 19, scale = 2)
    var amount: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: PaymentType = PaymentType.BUY_COURSE
) : BaseEntity()