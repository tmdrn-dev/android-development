plugins {
    alias(libs.plugins.generic.library)
    alias(libs.plugins.generic.hilt)
}

android {
    namespace = "com.example.mediaplayer.data.model"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}