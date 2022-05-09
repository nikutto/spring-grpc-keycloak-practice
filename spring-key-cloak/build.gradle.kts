import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.keycloak:keycloak-spring-boot-starter:18.0.0")

    implementation("org.springframework.security:spring-security-config")

    implementation(project(":stub"))
    implementation("org.springframework.security:spring-security-oauth2-jose")
    
    implementation("io.github.lognet:grpc-spring-boot-starter:4.7.0")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
