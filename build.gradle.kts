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
        maven("https://jitpack.io")
    }

    dependencies {
        testImplementation(kotlin("test"))
        compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0-SNAPSHOT")

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    }
}