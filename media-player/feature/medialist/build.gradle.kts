plugins {
    alias(libs.plugins.generic.library)
    alias(libs.plugins.generic.library.compose)
    alias(libs.plugins.generic.hilt)

    alias(libs.plugins.generic.feature)
}

android {
    namespace = "com.example.mediaplayer.feature.medialist"
}

dependencies {
    implementation(project(":common:controller"))
    implementation(project(":common:ui"))
    implementation(project(":domain"))
    implementation("androidx.media3:media3-common:1.2.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}