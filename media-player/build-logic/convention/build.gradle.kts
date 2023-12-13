plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.tools.build.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("Hilt") {
            id = "generic.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("Feature") {
            id = "generic.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        register("Application") {
            id = "generic.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("Library") {
            id = "generic.library"
            implementationClass = "LibraryConventionPlugin"
        }
        register("LibraryCompose") {
            id = "generic.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }
        register("ApplicationCompose") {
            id = "generic.application.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }
    }
}