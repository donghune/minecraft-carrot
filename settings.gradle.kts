rootProject.name = "carrot"

val plugins = "${rootProject.name}-plugins"
val modules = "${rootProject.name}-library"


listOf(modules, plugins).forEach { sub ->
    include(sub)
    file(sub).listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        include(":${sub}:${file.name}")
    }
}