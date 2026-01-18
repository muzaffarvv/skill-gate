package uz.vv.userservice.repo

import org.springframework.stereotype.Repository
import uz.vv.userservice.base.BaseRepo
import uz.vv.userservice.entity.User
import java.time.Instant

@Repository
interface UserRepo : BaseRepo<User> {

    fun findByPhoneNumberAndDeletedFalse(phoneNumber: String): User?

    fun existsByPhoneNumberAndDeletedFalse(phoneNumber: String): Boolean

    fun countByDeletedFalse(): Long

    fun countByCreatedAtAfterAndDeletedFalse(date: Instant): Long

}