plugins {
    alias(libs.plugins.generic.library)
    alias(libs.plugins.generic.library.compose)
}

android {
    namespace = "com.example.mediaplayer.common.ui"
}

dependencies {
    implementation("androidx.media3:media3-common:1.2.0")
    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}