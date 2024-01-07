plugins {
    alias(libs.plugins.generic.library)
    alias(libs.plugins.generic.hilt)
}

android {
    namespace = "com.example.mediaplayer.data.repository"
}

dependencies {
    implementation(project(":data:datasource"))
    implementation(project(":data:model"))
    implementation("androidx.media3:media3-common:1.2.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}