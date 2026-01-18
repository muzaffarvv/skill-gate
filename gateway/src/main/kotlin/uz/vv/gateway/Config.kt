package uz.vv.gateway

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "gateway")
class RestrictedPathProperties {
    var restrictedPaths: List<String> = emptyList()
}
