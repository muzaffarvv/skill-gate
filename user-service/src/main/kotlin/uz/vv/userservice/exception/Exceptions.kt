package uz.vv.userservice.exception

import uz.vv.userservice.enum.ErrorCodes

sealed class BaseException(
    val errorCode: ErrorCodes,
    override val message: String = errorCode.message
) : RuntimeException(message)

class UserNotFoundException(errorCode: ErrorCodes = ErrorCodes.USER_NOT_FOUND) : BaseException(errorCode)
class AlreadyExistsException(errorCode: ErrorCodes = ErrorCodes.PHONE_NUMBER_ALREADY_EXISTS) : BaseException(errorCode)

class RoleNotFoundException(errorCode: ErrorCodes = ErrorCodes.ROLE_NOT_FOUND) : BaseException(errorCode)
