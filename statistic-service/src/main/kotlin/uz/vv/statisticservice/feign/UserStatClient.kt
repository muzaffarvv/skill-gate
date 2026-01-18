package uz.vv.statisticservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "user-service")
interface UserStatClient {

    @GetMapping("/api/v1/users/internal/count")
    fun getTotalStudentsCount(): Long

    @GetMapping("/api/v1/users/internal/new-count")
    fun getNewStudentsCount(): Long
}