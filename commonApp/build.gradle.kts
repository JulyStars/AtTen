plugins {
    alias(libs.plugins.convention.kmp.application)
}

kotlin {
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(project(":core:core"))
                implementation(project(":core:ui"))
            }
        }
    }
}
