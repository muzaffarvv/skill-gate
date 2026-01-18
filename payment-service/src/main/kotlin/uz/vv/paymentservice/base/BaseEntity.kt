package uz.vv.paymentservice.base

import jakarta.persistence.*
import uz.vv.paymentservice.enum.Status
import java.time.Instant

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(updatable = false, nullable = false)
    var createdAt: Instant = Instant.now()

    @Column(nullable = false)
    var status: Status = Status.ACTIVE

    @Column(nullable = false)
    var deleted: Boolean = false
}