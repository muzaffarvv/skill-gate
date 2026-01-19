package uz.vv.userservice.exception

import jakarta.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import uz.vv.userservice.enum.ErrorCodes
import org.slf4j.LoggerFactory

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.errorCode.name, ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }
        return ResponseEntity(
            ValidationErrorResponse(
                ErrorCodes.VALIDATION_ERROR.name,
                "Input validation failed",
                errors
            ),
            HttpStatus.UNPROCESSABLE_ENTITY
        )
    }

    @ExceptionHandler(ValidationException::class)
    fun handleCustomValidation(ex: ValidationException): ResponseEntity<ValidationErrorResponse> {
        return ResponseEntity(
            ValidationErrorResponse(
                ErrorCodes.VALIDATION_ERROR.name,
                "Business validation failed",
                ex.message?.let { mapOf("message" to it) } ?: emptyMap()
            ),
            HttpStatus.UNPROCESSABLE_ENTITY
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val message = "Parameter '${ex.name}' should be of type ${ex.requiredType?.simpleName}"
        return ResponseEntity(
            ErrorResponse(ErrorCodes.INVALID_PARAMETER_TYPE.name, message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ErrorCodes.BAD_REQUEST.name, "Malformed JSON request or empty body"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupported(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ErrorCodes.METHOD_NOT_ALLOWED.name, "HTTP method ${ex.method} is not supported"),
            HttpStatus.METHOD_NOT_ALLOWED
        )
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        // Common cause during registration: duplicate unique fields (e.g., phone number)
        return ResponseEntity(
            ErrorResponse(ErrorCodes.PHONE_NUMBER_ALREADY_EXISTS.name, "This phone number is already registered"),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error occurred: ", ex)
        return ResponseEntity(
            ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR.name, "An unexpected error occurred. Please try again."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}

data class ErrorResponse(
    val code: String,
    val message: String
)

data class ValidationErrorResponse(
    val code: String,
    val message: String,
    val details: Map<String, String?>
)