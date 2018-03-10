

plugins {
    base
    id("org.jetbrains.kotlin.jvm") version "1.2.30"
}



allprojects {

    group = "com.tristanjuricek.asciilab"
    version = "0.1.0"

    repositories {
        jcenter()
        mavenCentral()
        maven("https://repo.spring.io/releases")
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}

