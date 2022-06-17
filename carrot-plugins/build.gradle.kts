val pluginProject = project

subprojects {
    tasks.withType<Jar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        doLast {
            val sep = File.separator
            copy {
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${pluginProject.rootDir.absolutePath}${sep}.server-mod${sep}plugins${sep}")
            }
            copy {
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${pluginProject.rootDir.absolutePath}${sep}.server${sep}plugins${sep}")
            }
            copy {
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${pluginProject.rootDir.absolutePath}${sep}releases${sep}")
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
