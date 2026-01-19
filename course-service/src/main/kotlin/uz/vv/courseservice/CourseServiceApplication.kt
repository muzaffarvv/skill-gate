package uz.vv.courseservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import uz.vv.courseservice.base.BaseRepoImpl

@SpringBootApplication
@EnableJpaRepositories(
    basePackages = ["uz.vv.courseservice.repo"],
    repositoryBaseClass = BaseRepoImpl::class
)
class CourseServiceApplication

fun main(args: Array<String>) {
    runApplication<CourseServiceApplication>(*args)
}
