import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId = "com.github.members.directory"
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode = 4163
        versionName = "0.9.9"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {

        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".dev"
            getProps(rootProject.file("debug.properties")).forEach { prop ->
                buildConfigField("String", prop.key.toString(), prop.value.toString())
            }
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    (kotlinOptions as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(
        fileTree(
            mapOf(
                "include" to listOf("*.jar"),
                "dir" to "libs"
            )
        )
    )
    implementation(project(mapOf("path" to ":baseplate-persistence")))
    implementation(Libs.supportAnnotation)
    implementation(Libs.annotation)
    implementation(Libs.kotlinStd)
    implementation(Libs.coreKtx)
    implementation(Libs.appCompat)
    implementation(Libs.constrainLayout)
    implementation(Libs.legacySupport)
    implementation(Libs.recyclerView)
    implementation(Libs.material)
    implementation(Libs.circleImageView)
    implementation(Libs.shimmering)
    api(Libs.daggerAndroid)
    api(Libs.daggerSupport)
    kapt(Libs.daggerCompiler)
    kapt(Libs.daggerProcessor)
    implementation(Libs.koinAndroid)
    implementation(Libs.koinViewModel)
    implementation(Libs.koinScope)
    implementation(Libs.httpClient)
    implementation(Libs.httpLogging)
    implementation(Libs.circleIndicator)
    implementation(Libs.sharedPreferrences)
    implementation(Libs.roomRuntime)
    implementation(Libs.roomKtx)
    kapt(Libs.roomCompiler)
    implementation(Libs.timber)
    implementation(Libs.refrofit)
    implementation(Libs.rxRetrofit)
    implementation(Libs.gSonConverter)
    implementation(Libs.scalarConverter)
    implementation(Libs.cardView)
    implementation(Libs.supportMaterials)
    implementation(Libs.legacyContrainLayout)
    implementation(Libs.roundedImage)
    implementation(Libs.airBnb)
    implementation(Libs.supportDesign)
    implementation(Libs.rxJava)
    implementation(Libs.rxKotlin)
    implementation(Libs.rxAndroid)
    implementation(Libs.rxExtension)
    implementation(Libs.rxBinding)
    implementation(Libs.rxBindingCore)
    implementation(Libs.rxAppCompat)
    implementation(Libs.fragmentKtxs)
    implementation(Libs.navigationKtx)
    implementation(Libs.navigationUiKtx)
    implementation(Libs.lifeCycleExtension)
    implementation(Libs.viewModelKtxt)
    implementation(Libs.liveDataKtxt)
    implementation(Libs.liveDataRuntimeKtx)
    implementation(Libs.saveState)
    implementation(Libs.lifeCycleCommon)
    implementation(Libs.lifeCycleService)
    implementation(Libs.lifeCycleProcess)
    implementation(Libs.lifeCycleReactive)
    testImplementation(Libs.coreTesting)
    kapt(Libs.lifeCycleCompiler)
    implementation(Libs.coroutinesCore)
    implementation(Libs.androidCoroutines)
    implementation(Libs.coil)
    implementation(Libs.paging)
    implementation("com.google.firebase:firebase-bom:26.5.0")
    implementation ("com.google.firebase:firebase-messaging:21.0.1")

    testImplementation(Libs.jUnit)
    androidTestImplementation(Libs.jUnitExtension)
    androidTestImplementation(Libs.expressoCore)
}

fun Project.android(configure: com.android.build.gradle.internal.dsl.BaseAppModuleExtension.() -> Unit) {
    return (this as ExtensionAware).extensions.configure(
        "android",
        configure
    )
}

fun getProps(file: File): java.util.Properties {
    val props = Properties()
    props.load(file.inputStream())
    return props
}