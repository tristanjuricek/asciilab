rootProject.name = "asciilab"

include("api")
include("docker")
include("testing")


//pluginManagement {
//	repositories {
//		maven("https://jcenter.bintray.com/")
//		gradlePluginPortal()
//	}
//	resolutionStrategy {
//		eachPlugin {
//			if (requested.id.id == "org.junit.platform.gradle.plugin") {
//				useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
//			}
//		}
//	}
//}