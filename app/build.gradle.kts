plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.habitflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habitflow"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Activities em Views (AppCompatActivity) e tema AppCompat
    implementation("androidx.appcompat:appcompat:1.7.0")
    // RecyclerView da lista de hábitos
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
