package uz.vv.paymentservice.exception

import uz.vv.paymentservice.enum.ErrorCodes

sealed class BaseException(
    val errorCode: ErrorCodes,
    override val message: String = errorCode.message
) : RuntimeException(message)

class InsufficientFundsException(errorCode: ErrorCodes = ErrorCodes.INSUFFICIENT_FUNDS_EXCEPTION) : BaseException(errorCode)
