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
}

extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
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

dependencies {
    compile(project(":api.model"))

    compile("com.github.salomonbrys.kodein:kodein:${extra["kodein.version"]}")
    compile("org.jetbrains.exposed:exposed:0.10.1")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.slf4j:slf4j-api")
    compile("ch.qos.logback:logback-classic:${extra["logback.version"]}")

    compile("io.ktor:ktor-server-netty:${extra["ktor.version"]}")
    compile("io.ktor:ktor-gson:${extra["ktor.version"]}")

    compile("org.postgresql:postgresql:42.2.2")
    compile("org.apache.commons:commons-dbcp2:2.2.0")

    testCompile("org.junit.jupiter:junit-jupiter-api:${extra["junit-jupiter.version"]}")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:${extra["junit-jupiter.version"]}")
}