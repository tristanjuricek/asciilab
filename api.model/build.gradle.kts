import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
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


dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    api("org.jetbrains.kotlin:kotlin-reflect")
}
repositories {
    mavenCentral()
}
