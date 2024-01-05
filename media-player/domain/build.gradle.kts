plugins {
    alias(libs.plugins.generic.library)
    alias(libs.plugins.generic.hilt)
}

android {
    namespace = "com.example.mediaplayer.domain"
}

dependencies {
    implementation(project(":common:controller"))
    implementation(project(":data:model"))
    implementation(project(":data:repository"))
    implementation("androidx.media3:media3-session:1.2.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}