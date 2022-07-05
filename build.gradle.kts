import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"

	kotlin("plugin.allopen") version "1.6.21"
	kotlin("plugin.noarg") version "1.6.21"
}

allOpen {
	annotation("javax.persistence.Entity")
}

noArg {
	annotation("javax.persistence.Entity")
}

group = "com.sia"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven("https://jitpack.io")

}

val kotestVersion = "5.2.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test"){
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(module = "mockito-core")
	}
	testImplementation("org.springframework.security:spring-security-test")


	// kassava
	implementation("com.github.consoleau:kassava:2.1.0")

	// postgresql
	// https://mvnrepository.com/artifact/org.postgresql/postgresql
	runtimeOnly("org.postgresql:postgresql")


	// https://mvnrepository.com/artifact/org.hibernate/hibernate-spatial
	implementation("org.hibernate:hibernate-spatial:5.1.0.Final")

	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6")

	// kotest
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")

	// mockK
	testImplementation("io.mockk:mockk:1.12.4")

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
