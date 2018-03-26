

plugins {
    base
    id("org.jetbrains.kotlin.jvm") version "1.2.31"
}

allprojects {

    // Dependency version declarations are fixed at the moment.
    extra["jackson.version"] = "2.9.4.1"
    extra["junit-jupiter.version"] = "5.1.0"
    extra["kodein.version"] = "4.1.0"
    extra["ktor.version"] = "0.9.1"
    extra["logback.version"] = "1.2.3"

    group = "com.tristanjuricek.asciilab"
    version = "0.1.0"

    repositories {
        jcenter()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/ktor")
    }
}
