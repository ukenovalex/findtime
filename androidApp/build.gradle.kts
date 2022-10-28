plugins {
    id(androidApp)
    kotlin(androidPlugin)
}

android {
    namespace = "ru.ukenov.findtime.android"
    compileSdk = Versions.compile_sdk
    defaultConfig {
        applicationId = "ru.ukenov.findtime.android"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    with(Deps) {
        implementation(napier)
        implementation(material)
    }
    with(Compose) {
        implementation(compiler)
        implementation (runtime)
        implementation (runtime_livedata)
        implementation (ui)
        implementation (tooling)
        implementation (foundation)
        implementation (foundationLayout)
        implementation (material)
        implementation(material_icons)
        implementation(activity)
    }
}