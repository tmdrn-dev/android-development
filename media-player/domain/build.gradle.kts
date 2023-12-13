plugins {
    alias(libs.plugins.generic.library)
    alias(libs.plugins.generic.hilt)
}

android {
    namespace = "com.example.mediaplayer.domain"
}

dependencies {
    implementation(project(":data:model"))
    implementation(project(":data:repository"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}