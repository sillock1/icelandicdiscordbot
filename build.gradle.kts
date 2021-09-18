import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.spring") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}

group = "sillock"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.javacord:javacord:3.3.2")
    implementation("org.springframework.boot:spring-boot-starter-security:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter:2.5.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.ktor:ktor-client-core:1.6.3")
    implementation("io.ktor:ktor-client-cio:1.6.3")
    implementation("io.ktor:ktor-client-logging:1.6.3")
    implementation("io.ktor:ktor-client-serialization:1.6.3")
    testImplementation("io.ktor:ktor-server-test-host:1.6.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.5.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.4")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.1")
    testImplementation ("io.kotest:kotest-assertions-core-jvm:4.6.1")// for kotest core jvm assertions
    testImplementation ("io.kotest:kotest-property-jvm:4.6.1")// for kotest property test
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging { events(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED); exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL }
}
