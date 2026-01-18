package uz.vv.statisticservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(basePackages = ["uz.vv.statisticservice.feign"])
class StatisticServiceApplication

fun main(args: Array<String>) {
	runApplication<StatisticServiceApplication>(*args)
}
