package uz.vv.userservice.entity

import jakarta.persistence.*
import uz.vv.userservice.base.BaseEntity

@Entity
@Table(name = "permissions")
class Permission : BaseEntity() {

    @Column(nullable = false, unique = true, length = 34)
    var code: String? = null

    @Column(nullable = false, length = 128)
    var name: String? = null
}
