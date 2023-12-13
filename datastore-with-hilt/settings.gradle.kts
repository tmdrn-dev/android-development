pluginManagement {
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

rootProject.name = "DatastoreWithHilt"
include(":app")
include(":data:datastore")
include(":data:repository")
include(":data:model")
include(":domain")
include(":data:datasource")
