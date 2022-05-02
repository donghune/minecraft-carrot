buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

val kotlinVersion = "1.6.10"

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10" apply false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${project.name}.jar")
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/") }
    }

    dependencies {
        testImplementation(kotlin("test"))
        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
        compileOnly("io.github.monun:kommand-api:2.10.0")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    }
}