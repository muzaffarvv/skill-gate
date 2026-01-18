package uz.vv.statisticservice.service

import org.springframework.stereotype.Service
import uz.vv.statisticservice.dto.AdminDashboardStats
import uz.vv.statisticservice.feign.PaymentStatClient
import uz.vv.statisticservice.feign.UserStatClient

@Service
class StatisticService(
    private val paymentClient: PaymentStatClient,
    private val userClient: UserStatClient
) {
    fun getDashboardStats(): AdminDashboardStats {
        // 1. User-Servicedan jami foydalanuvchilar sonini olamiz
        val totalUsers = userClient.getTotalStudentsCount()

        // 2. Payment-Servicedan barcha moliyaviy ma'lumotlarni olamiz
        val totalEarnings = paymentClient.getTotalEarnings()
        val monthlyEarnings = paymentClient.getMonthlyEarnings()
        val recentTransactions = paymentClient.getRecentTransactions(10)
        val popularCourseId = paymentClient.getMostPopularCourseId()

        return AdminDashboardStats(
            totalEarnings = totalEarnings,
            earningsThisMonth = monthlyEarnings,
            totalStudents = totalUsers,
            activeEnrollments = paymentClient.getCountOfSuccessfulPayments(),
            popularCourseId = popularCourseId,
            recentTransactions = recentTransactions
        )
    }
}