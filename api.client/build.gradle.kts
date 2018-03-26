import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    api(project(":api.model"))
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("io.ktor:ktor-client-apache:${extra["ktor.version"]}")
    api("io.ktor:ktor-client-json:${extra["ktor.version"]}")

    testCompile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.4.1")
    testCompile("com.squareup.okhttp3:mockwebserver:3.10.0")
    testCompile("org.junit.jupiter:junit-jupiter-api:${extra["junit-jupiter.version"]}")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:${extra["junit-jupiter.version"]}")
}
