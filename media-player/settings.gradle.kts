pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MediaPlayer"

include(":app")
include(":feature:medialist")
include(":common:ui")
include(":data:datasource")
include(":data:model")
include(":data:repository")
include(":domain")
