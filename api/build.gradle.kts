import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


buildscript {
    repositories {
        mavenCentral()
    }
}

repositories {
    maven("https://dl.bintray.com/kotlin/exposed" )
}

plugins {
    application
    id("org.jetbrains.kotlin.jvm")
    id("com.github.johnrengelman.shadow") version "2.0.1"
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
//    id("org.junit.platform.gradle.plugin") version "1.0.2"
}

extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

application {
    mainClassName = "com.tristanjuricek.asciilab.api.APIApplicationKt"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

dependencyManagement {
    imports {
        // Should use Spring IO platform milestone or release when available
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.0.RELEASE")

    }
}

dependencies {
    compile(project(":api.model"))

    compile("org.jetbrains.exposed:exposed:0.10.1")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:0.22.5")

    compile("org.springframework:spring-webflux")
    compile("org.springframework:spring-context") {
        exclude(module = "spring-aop")
    }
    compile("io.projectreactor.ipc:reactor-netty")

    compile("org.slf4j:slf4j-api")
    compile("ch.qos.logback:logback-classic")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    testCompile("io.projectreactor:reactor-test")

    testCompile("org.junit.jupiter:junit-jupiter-api")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}