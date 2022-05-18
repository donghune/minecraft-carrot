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
        compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
        compileOnly(files("/Users/yudonghun/IdeaProjects/minecrafte-template/libs/spigot-1.18.2-R0.1-SNAPSHOT.jar"))
        compileOnly(files("/Users/yudonghun/IdeaProjects/minecrafte-template/libs/CrazyAdvancementsAPI.jar"))

        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("io.github.monun:kommand-api:2.10.0")
        compileOnly("io.github.monun:heartbeat-coroutines:0.0.3")
        compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0-SNAPSHOT")

        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
        compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.2")
        compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    }
}