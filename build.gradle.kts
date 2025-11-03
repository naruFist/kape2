plugins {
    kotlin("jvm") version "2.2.21"
    `maven-publish`
}

group = properties["group"]!!
version = properties["version"]!!

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = group.toString()
            artifactId = "kape2"
            version = project.version.toString()
        }
    }
}