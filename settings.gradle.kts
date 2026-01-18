pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "skill-gate"

include(
    "gateway",
    "user-service",
    "course-service",
    "payment-service",
    "config-service",
    "statistic-service"
)

