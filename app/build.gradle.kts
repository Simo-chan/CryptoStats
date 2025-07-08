import org.gradle.kotlin.dsl.debugImplementation
import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.devtools.ksp)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.simochan.cryptostats"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.simochan.cryptostats"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"${localProperties["apiKey"]}\"")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://rest.coincap.io/v3/\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"https://rest.coincap.io/v3/\"")
            signingConfig = signingConfigs.getByName("debug")
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
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.slf4j.nop)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //Koin
    implementation(libs.bundles.koin)

    //Ktor
    implementation(libs.bundles.ktor)

    //Lottie animation
    implementation(libs.lottie.compose)

    //Data store
    implementation(libs.androidx.datastore.preferences)

    //Splash screen
    implementation(libs.androidx.core.splashscreen)

    //Room db
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    //Compose navigation
    implementation(libs.androidx.navigation.compose)
    ksp(libs.androidx.room.compiler)

    //Glance
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
}