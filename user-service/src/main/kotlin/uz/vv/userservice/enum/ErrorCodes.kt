package uz.vv.userservice.enum

enum class ErrorCodes(val code: Int, val message: String) {

    INTERNAL_SERVER_ERROR(11, "An unexpected error occurred on the server"),
    VALIDATION_ERROR(12, "The provided data is incorrect or invalid"),
    BAD_REQUEST(13, "The request is incorrect or malformed"),
    METHOD_NOT_ALLOWED(14, "This HTTP method is not allowed for this request"),
    INVALID_PARAMETER_TYPE(15, "One or more parameters have an invalid type"),

    ROLE_NOT_FOUND(55, "The requested role was not found"),

    USER_NOT_FOUND(100, "User not found in our database"),
    PHONE_NUMBER_ALREADY_EXISTS(101, "This phone number is already registered"),

    UNAUTHORIZED(200, "Authentication is required to access this resource"),
    ACCESS_DENIED(201, "You do not have permission to perform this action")
}