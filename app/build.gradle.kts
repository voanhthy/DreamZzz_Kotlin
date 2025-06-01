import java.io.FileInputStream
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}
val apiKey = localProperties.getProperty("API_KEY") ?: ""

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // navigation
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // ROOM
    alias(libs.plugins.kotlin.ksp)
    // firebase
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "de.syntax_institut.androidabschlussprojekt"
    compileSdk = 35

    defaultConfig {
        applicationId = "de.syntax_institut.androidabschlussprojekt"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true // Aktiviere BuildConfig
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // extended icons
        implementation(libs.androidx.compose.material.icons.extended)
    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization)
    // api
    implementation(libs.moshi)
    implementation(libs.retrofit)
    implementation(libs.converterMoshi)
    // async image
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    // data store
    implementation(libs.androidx.datastore.preferences)
    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // koin
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    // location
    implementation(libs.play.services.location)
    implementation(kotlin("test"))
    // Für LazyVerticalStaggeredGrid
    implementation(libs.androidx.compose.foundation)
    // firebase
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)
    // Kalender
    implementation("com.kizitonwose.calendar:compose:2.7.0")
}