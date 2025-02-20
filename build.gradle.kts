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
		property("sonar.sources", "src/main/java,src/main/resources")
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
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
	testImplementation("org.seleniumhq.selenium:selenium-java:4.14.1")
	testImplementation("io.github.bonigarcia:selenium-jupiter:5.0.1")
	testImplementation("io.github.bonigarcia:webdrivermanager:5.6.3")
	testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.register<Test>( "unitTest") {
	description = "Runs unit tests."
	group = "verification"

	filter {
		excludeTestsMatching("*FunctionalTest")

	}
}
tasks.register<Test>( "functionalTest") {
	description = "Runs functional tests."
	group = "verification"

	filter {
		includeTestsMatching("*FunctionalTest")

	}

}

tasks.test {
	filter {
		excludeTestsMatching("*FunctionalTest")
	}
	finalizedBy(tasks.jacocoTestReport)

}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}