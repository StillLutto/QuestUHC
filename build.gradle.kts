plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation(kotlin("stdlib-jdk8"))
}

group = "me.lutto"
version = "1.0-SNAPSHOT"
description = "TreasureBattle"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
    // customize this to your needs
    destinationDirectory = File("C:\\Users\\iAmEi\\Desktop\\Files\\Programming\\Minecraft\\Plugins\\Servers\\treasure-battle\\plugins")
}

kotlin {
    jvmToolchain(17)
}