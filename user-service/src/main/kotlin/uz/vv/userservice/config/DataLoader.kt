package uz.vv.userservice.config

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import uz.vv.userservice.dto.PermissionDTO
import uz.vv.userservice.dto.RoleDTO
import uz.vv.userservice.entity.*
import uz.vv.userservice.mapper.PermissionMapper
import uz.vv.userservice.mapper.RoleMapper
import uz.vv.userservice.repo.PermissionRepo
import uz.vv.userservice.repo.RoleRepo
import uz.vv.userservice.repo.UserRepo

@Component
class DataLoader(
    private val roleRepo: RoleRepo,
    private val permissionRepo: PermissionRepo,
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val permissions = mapOf(
            "USER_MANAGE" to "Manage users",
            "ROLE_MANAGE" to "Manage roles and permissions",
            "COURSE_MANAGE" to "Manage courses completely",
            "PAYMENT_VIEW" to "Track payments",
            "STAT_VIEW" to "View all statistics",
            "COURSE_BUY" to "Permission to buy courses"
        ).map { (code, name) ->
            code to createPermissionIfNotFound(code, name)
        }.toMap()

        val adminRole = createRoleIfNotFound("ADMIN", "Platform Administrator")
        val managerRole = createRoleIfNotFound("MANAGER", "Course Manager")
        val studentRole = createRoleIfNotFound("STUDENT", "Standard User/Student")

        if (adminRole.permissions.isEmpty()) {
            adminRole.permissions.addAll(permissions.values)
            roleRepo.save(adminRole)
        }

        if (managerRole.permissions.isEmpty()) {
            val managerPerms = setOfNotNull(permissions["COURSE_MANAGE"], permissions["STAT_VIEW"])
            managerRole.permissions.addAll(managerPerms)
            roleRepo.save(managerRole)
        }

        if (studentRole.permissions.isEmpty()) {
            permissions["COURSE_BUY"]?.let {
                studentRole.permissions.add(it)
                roleRepo.save(studentRole)
            }
        }

        createSuperAdmin(adminRole)
    }

    private fun createPermissionIfNotFound(code: String, name: String): Permission {
        val found = permissionRepo.findByCode(code)
        if (found != null) return found

        val newPermission = permissionRepo.save(PermissionMapper.toEntity(PermissionDTO(code, name)))
        println("INFO: Permission $code created successfully.")
        return newPermission
    }

    private fun createRoleIfNotFound(code: String, name: String): Role {
        val found = roleRepo.findByCode(code)
        if (found != null) return found

        val newRole = roleRepo.save(
            RoleMapper.toEntity(
                RoleDTO(
                    code, name,
                    permissions = emptyList()
                )
            )
        )
        println("INFO: Role $code created successfully.")
        return newRole
    }

    private fun createSuperAdmin(adminRole: Role) {
        val superAdminPhone = "998912345678"
        if (!userRepo.existsByPhoneNumberAndDeletedFalse(superAdminPhone)) {
            val admin = User().apply {
                firstName = "Super"
                lastName = "Admin"
                phoneNumber = superAdminPhone
                password = passwordEncoder.encode("Admin123!")
                roles.add(adminRole)
            }
            userRepo.save(admin)
            println("INFO: Super Admin created successfully.")
        }
    }
}