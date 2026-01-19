package uz.vv.userservice.config

import uz.vv.userservice.dto.BalanceRequest
import uz.vv.userservice.dto.UserResponseDto

interface BalanceOperation {
    fun withdraw(request: BalanceRequest): Boolean
    fun fill(request: BalanceRequest): UserResponseDto
}