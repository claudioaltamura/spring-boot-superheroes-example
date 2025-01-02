plugins {
    id ("java")
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("org.springframework.boot") version "3.4.1"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "de.claudioaltamura"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-webflux")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly ("org.springframework.boot:spring-boot-devtools")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
	runtimeOnly ("com.h2database:h2")
    testCompileOnly ("org.projectlombok:lombok")
    testAnnotationProcessor ("org.projectlombok:lombok")
	testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api-docs")
    outputDir.set(file("$projectDir/src/main/resources"))
    outputFileName.set("openapi.yaml")
    waitTimeInSeconds.set(10)
}