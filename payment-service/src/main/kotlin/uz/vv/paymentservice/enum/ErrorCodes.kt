package uz.vv.paymentservice.enum

enum class ErrorCodes( val code: Int, val message: String = "") {
    INSUFFICIENT_FUNDS_EXCEPTION(888, "Insufficient funds")

}