plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

val openFeignVersion = "11.9.1"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    implementation("io.github.openfeign:feign-gson:$openFeignVersion")
    implementation("io.github.openfeign:feign-core:$openFeignVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}