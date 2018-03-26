import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    application
    id("org.jetbrains.kotlin.jvm")
}

extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

application {
    mainClassName = "com.tristanjuricek.asciilab.admin.AdminApplicationKt"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

dependencies {
    compile( project(":api.client"))

    compile("com.github.salomonbrys.kodein:kodein:${extra["kodein.version"]}")

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.9")

    compile("io.ktor:ktor-server-netty:${extra["ktor.version"]}")
    compile("io.ktor:ktor-html-builder:${extra["ktor.version"]}")
    compile("io.ktor:ktor-gson:${extra["ktor.version"]}")

    compile("org.slf4j:slf4j-api")
    compile("ch.qos.logback:logback-classic:${extra["logback.version"]}")

    testCompile("org.junit.jupiter:junit-jupiter-api:${extra["junit-jupiter.version"]}")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:${extra["junit-jupiter.version"]}")
}