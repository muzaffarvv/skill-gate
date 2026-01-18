package uz.vv.userservice.entity

import jakarta.persistence.*
import uz.vv.userservice.base.BaseEntity

@Entity
@Table(name = "roles")
class Role : BaseEntity() {

    @Column(nullable = false, unique = true, length = 16)
    var code: String? = null

    @Column(nullable = false, length = 38)
    var name: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    var permissions: MutableList<Permission> = mutableListOf()
}
