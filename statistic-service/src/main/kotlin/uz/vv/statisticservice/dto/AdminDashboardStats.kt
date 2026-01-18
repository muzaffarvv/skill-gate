package uz.vv.statisticservice.dto

import java.math.BigDecimal

data class AdminDashboardStats(
    val totalEarnings: BigDecimal,        // Jami tushum (Hamma vaqt uchun)
    val earningsThisMonth: BigDecimal,   // Joriy oydagi tushum
    val totalStudents: Long,             // Tizimdagi jami talabalar soni
    val activeEnrollments: Long,         // Sotib olingan kurslarning umumiy soni
    val popularCourseId: Long?,          // Eng ko'p sotilgan kursning IDsi
    val recentTransactions: List<TransactionDTO> // Oxirgi 10 ta muvaffaqiyatli to'lov
)

data class TransactionDTO(
    val id: Long,
    val phoneNumber: String,
    val amount: BigDecimal,
    val courseId: Long,
    val status: String,
    val createdAt: java.time.Instant
)