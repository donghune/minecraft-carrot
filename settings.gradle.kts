//if((JavaVersion.current() != JavaVersion.VERSION_17)) {
//    throw kotlin.NullPointerException("Java 17 is required")
//}

rootProject.name = "donghune"

val plugins = "${rootProject.name}-plugins"
val modules = "${rootProject.name}-modules"


listOf(plugins, modules).forEach { sub ->
    include(sub)
    file(sub).listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        include(":${sub}:${file.name}")
    }
}

include("${rootProject.name}-publish")
