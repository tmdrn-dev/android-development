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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}