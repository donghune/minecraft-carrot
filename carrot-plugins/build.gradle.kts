val pluginProject = project

subprojects {
    tasks.withType<Jar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        doLast {
            copy {
                val sep = File.separator
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${pluginProject.rootDir.absolutePath}${sep}.server-mod${sep}plugins${sep}")
            }
            copy {
                val sep = File.separator
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${pluginProject.rootDir.absolutePath}${sep}.server${sep}plugins${sep}")
            }
        }
    }

    if (version == "unspecified") {
        version = rootProject.version
    }


    tasks {
        processResources {
            filesMatching("**/*.yml") {
                expand(rootProject.properties)
            }
        }
    }

    dependencies {
        implementation(project(":carrot-library"))
    }

}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}
