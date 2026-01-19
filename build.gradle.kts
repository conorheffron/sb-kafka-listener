plugins {
    id("org.springframework.boot") version "3.5.9"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
}

group = "net.ironoc.kafka"
version = "0.0.4-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    val kotlinMockitoVersion = "6.2.1"

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(kotlin("reflect"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$kotlinMockitoVersion")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.test {
    // Show detailed test results in console
    testLogging {
        // Show events for passed, skipped, and failed tests
        events("passed", "skipped", "failed")

        // Show full exception stack traces
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

        // Show output from tests (System.out/System.err)
        showStandardStreams = true
    }

    // Optional: run tests sequentially for easier reading
    outputs.upToDateWhen { false } // Always run tests

    afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
        if (desc.parent == null) { // will match the outermost suite
            println(
                "Results: ${result.resultType} " +
                        "(${result.testCount} tests, " +
                        "${result.successfulTestCount} passed, " +
                        "${result.failedTestCount} failed, " +
                        "${result.skippedTestCount} skipped)"
            )
        }
    }))
}
