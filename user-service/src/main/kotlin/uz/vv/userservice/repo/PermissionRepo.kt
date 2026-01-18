package uz.vv.userservice.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uz.vv.userservice.entity.Permission

@Repository
interface PermissionRepo : JpaRepository<Permission, Long> {
    fun findByCode(code: String): Permission?
}