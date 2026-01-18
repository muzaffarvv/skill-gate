package uz.vv.userservice.entity

import jakarta.persistence.*
import uz.vv.userservice.base.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "users")
class User : BaseEntity() {

    @Column(nullable = false, length = 72)
    var firstName: String? = null

    @Column(nullable = false, length = 60)
    var lastName: String? = null

    @Column(nullable = false, unique = true, length = 15)
    var phoneNumber: String? = null

    @Column(nullable = false, length = 120)
    var password: String? = null

    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO

    @ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableList<Role> = mutableListOf()
}
