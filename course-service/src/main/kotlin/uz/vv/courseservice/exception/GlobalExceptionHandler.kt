package uz.vv.courseservice.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import uz.vv.courseservice.enum.ErrorCodes

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

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error occurred: ", ex)
        return ResponseEntity(
            ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR.name, "An internal server error occurred."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ErrorCodes.BAD_REQUEST.name, "Malformed JSON request"),
            HttpStatus.BAD_REQUEST
        )
    }
}

data class ErrorResponse(val code: String, val message: String)
data class ValidationErrorResponse(val code: String, val message: String, val details: Map<String, String?>)