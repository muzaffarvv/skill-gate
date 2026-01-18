package uz.vv.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import uz.vv.userservice.base.BaseRepoImpl

@SpringBootApplication
@EnableJpaRepositories(
	basePackages = ["uz.vv.userservice.repo"],
	repositoryBaseClass = BaseRepoImpl::class
)
class UserServiceApplication

fun main(args: Array<String>) {
	runApplication<UserServiceApplication>(*args)
}
