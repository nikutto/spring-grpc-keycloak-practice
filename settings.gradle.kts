rootProject.name = "grpc-kotlin-examples"

// when running the assemble task, ignore the android & graalvm related subprojects

include("protos", "stub", "server", "spring-key-cloak")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}
