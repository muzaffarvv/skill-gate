package uz.vv.courseservice.enum

enum class ErrorCodes(
    val code: Int,
    val message: String) {

    INTERNAL_SERVER_ERROR(1, "An unexpected error occurred on the server"),
    VALIDATION_ERROR(2, "The provided data is incorrect or invalid"),
    BAD_REQUEST(3, "The request is incorrect or malformed"),
    METHOD_NOT_ALLOWED(4, "This HTTP method is not allowed for this request"),
    INVALID_PARAMETER_TYPE(5, "One or more parameters have an invalid type"),

    COURSE_NOT_FOUND(300, "The requested course was not found"),
    LESSON_NOT_FOUND(301, "The requested lesson was not found"),
    INSUFFICIENT_PERMISSIONS(303, "Only the author or admin can modify this course"),
    INVALID_COURSE_PRICE(304, "Course price cannot be negative"),

    UNAUTHORIZED(200, "Authentication is required to access this resource"),
    ACCESS_DENIED(201, "You do not have permission to perform this action")
}