import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("com.github.ben-manes.versions") version "0.47.0"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"

    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.10"

    kotlin("plugin.jpa") version "2.1.10"
}

group = "com.github.jactor.rises"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // main dependencies
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // runtime only
    runtimeOnly("org.postgresql:postgresql")

    // test
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.hamcrest")
        exclude(group = "org.mockito")
    }

    // test - database
    testImplementation("io.zonky.test:embedded-database-spring-test:2.6.0")
    testImplementation("io.zonky.test:embedded-postgres:2.1.0")
    testImplementation(platform("io.zonky.test.postgres:embedded-postgres-binaries-bom:17.2.0"))

    // test - runtime only
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict --release 21")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
    }
}
