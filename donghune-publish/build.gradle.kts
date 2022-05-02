plugins {
    `maven-publish`
}
apply(plugin = "com.github.johnrengelman.shadow")
val rootName = "donghune-publish"
val repoName = "donghune"

dependencies {
    implementation(project(":donghune-modules"))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    archiveFileName.set("$rootName.jar")
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>(rootName) {
            groupId = "io.github.$repoName"
            artifactId = rootName
            version = rootProject.version.toString()
            artifact(tasks["shadowJar"])
        }
    }
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/donghune/minecraft-donghune")
            credentials {
                val prop = org.jetbrains.kotlin.konan.properties.Properties()
                val file = File(rootProject.projectDir, "env.properties")
                if (file.exists()) {
                    prop.load(file.inputStream())
                }
                username = prop["gpr.user"]?.toString()?: ""
                password = prop["gpr.key"]?.toString()?: ""
            }
        }
    }
}
