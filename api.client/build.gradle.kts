import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
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

dependencyManagement {
    imports {
        // Should use Spring IO platform milestone or release when available
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.0.RELEASE")
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api(project(":api.model"))
    api("org.springframework:spring-webflux")

    testCompile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.4.1")
    testCompile("com.squareup.okhttp3:mockwebserver:3.10.0")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testCompile("io.projectreactor:reactor-test")
    testRuntime("io.projectreactor.ipc:reactor-netty")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}
