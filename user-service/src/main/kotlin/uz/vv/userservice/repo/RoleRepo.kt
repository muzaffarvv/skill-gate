package uz.vv.userservice.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uz.vv.userservice.entity.Role

@Repository
interface RoleRepo : JpaRepository<Role, Long> {
    fun findByCode(code: String): Role?
}