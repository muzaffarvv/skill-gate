package uz.vv.courseservice.exception

import uz.vv.courseservice.enum.ErrorCodes

sealed class BaseException(
    val errorCode: ErrorCodes,
    override val message: String = errorCode.message
) : RuntimeException(message)

class EntityNotFound(errorCode: ErrorCodes = ErrorCodes.COURSE_NOT_FOUND) : BaseException(errorCode)

class CourseAccessException(errorCode: ErrorCodes = ErrorCodes.ACCESS_DENIED) : BaseException(errorCode)

