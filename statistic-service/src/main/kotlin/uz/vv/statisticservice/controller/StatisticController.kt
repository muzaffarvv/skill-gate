package uz.vv.statisticservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.vv.statisticservice.dto.AdminDashboardStats
import uz.vv.statisticservice.service.StatisticService

@RestController
@RequestMapping("/api/v1/statistics")
class StatisticController(
    private val statisticService: StatisticService
) {
    @GetMapping("/dashboard")
    fun getDashboardStats(): ResponseEntity<AdminDashboardStats> {
        val stats = statisticService.getDashboardStats()
        return ResponseEntity.ok(stats)
    }
}