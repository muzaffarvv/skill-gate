plugins {
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.5.9" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
    if (tasks.findByName("prepareKotlinBuildScriptModel") == null) {
        tasks.register("prepareKotlinBuildScriptModel") {}
    }
}
