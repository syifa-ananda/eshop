plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "4.3.1.3277"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"

sonar {
	properties {
		property("sonar.projectKey", "syifa-ananda_eshop")
		property("sonar.organization", "syifa-ananda")
		property("sonar.host.url", "https://sonarcloud.io")

		// Include your main code directories (adjust as needed).
		property("sonar.sources", "src/main/java,src/main/resources")

		// IMPORTANT: SonarCloud needs the JaCoCo XML report path here:
		property("sonar.coverage.jacoco.xmlReportPaths",
			"${buildDir}/reports/jacoco/test/jacocoTestReport.xml")
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
	testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
	testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
	testImplementation("org.junit.jupiter:junit-jupiter")
}

// Separate tasks for unit & functional tests (optional):
tasks.register<Test>("unitTest") {
	description = "Runs unit tests."
	group = "verification"
	filter {
		excludeTestsMatching("*FunctionalTest")
	}
}

tasks.register<Test>("functionalTest") {
	description = "Runs functional tests."
	group = "verification"
	filter {
		includeTestsMatching("*FunctionalTest")
	}
}

// Default test task excludes functional tests:
tasks.test {
	filter {
		excludeTestsMatching("*FunctionalTest")
	}
	finalizedBy(tasks.jacocoTestReport)
}

// JaCoCo task must generate XML so SonarCloud can read coverage data
tasks.jacocoTestReport {
	dependsOn(tasks.test) // or dependsOn("unitTest") if you want coverage only from unit tests
	reports {
		xml.required.set(true)  // crucial for SonarCloud
		html.required.set(true) // optional, for local viewing
	}
}

// Ensure all tests use JUnit Platform
tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
