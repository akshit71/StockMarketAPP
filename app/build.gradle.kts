plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
//    id("com.google.devtools.ksp" )
}
kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    namespace = "com.example.stockmarketapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.stockmarketapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_version = rootProject.extra["compose_version"]
    val compose_coil=rootProject.extra["compose_coil"]
    val lifecycle_version=rootProject.extra["lifecycle_version"]
    val compose_nav_version=rootProject.extra["compose_nav_version"]
    val compose_constraint=rootProject.extra["compose_constraint"]

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.3.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_version")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.5")

    // OpenCSV
    implementation ("com.opencsv:opencsv:5.5.2")

    // Compose dependencies
    implementation("com.google.android.material:compose-theme-adapter:1.1.10")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.compose.material:material-icons-extended:$compose_version")
    implementation("io.coil-kt:coil-compose:$compose_coil")
    implementation("io.coil-kt:coil-svg:$compose_coil")
    implementation("androidx.navigation:navigation-compose:$compose_nav_version")
    implementation ("androidx.constraintlayout:constraintlayout-compose:$compose_constraint")

//    ksp ("io.github.raamcosta.compose-destinations:ksp:1.1.2-beta")

    // Coil
    implementation ("io.coil-kt:coil-compose:1.4.0")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.40.5")
    kapt ("com.google.dagger:hilt-android-compiler:2.40.5")
    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")

    // Room
    implementation ("androidx.room:room-runtime:2.4.2")
    kapt ("androidx.room:room-compiler:2.4.2")

    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.4.2")
}