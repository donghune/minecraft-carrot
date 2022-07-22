buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

val kotlinVersion = "1.6.21"

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.21"
    id("ru.kinca.google-drive-uploader") version "1.1.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${project.name}.jar")
    }

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")
        maven("https://maven.enginehub.org/repo/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }

    dependencies {
        implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        implementation(files("./libs/forge-1.16.5-36.2.34.jar"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    }
}