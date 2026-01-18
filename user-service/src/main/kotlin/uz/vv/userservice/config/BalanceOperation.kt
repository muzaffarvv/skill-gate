package uz.vv.userservice.config

import uz.vv.userservice.dto.UserResponseDto
import java.math.BigDecimal

interface BalanceOperation {
    fun withdraw(phoneNumber: String, amount: BigDecimal): Boolean
    fun fill(phoneNumber: String, amount: BigDecimal): UserResponseDto
}