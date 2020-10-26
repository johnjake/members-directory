import Configs.androidAnnotation
import Configs.androidxAnnotation
import Configs.archCompVersion
import Configs.archVersion
import Configs.cardViewVersion
import Configs.circleImageViewVersion
import Configs.coilVersion
import Configs.constrainLayoutVersion
import Configs.converterScalars
import Configs.coroutineAndroid
import Configs.coroutineCore
import Configs.daggerVersion
import Configs.fragmentKtx
import Configs.ktxCore
import Configs.lifecycleVersion
import Configs.lottieVersion
import Configs.navFragmentKtxVersion
import Configs.navUiKtxVersion
import Configs.okhttpLoggingVersion
import Configs.okhttpVersion
import Configs.pagingVersion
import Configs.retrofitVersion
import Configs.roundImageVersion
import Configs.rxAndroidVersion
import Configs.rxBindingXVersion
import Configs.rxJava2Version
import Configs.rxKotlinVersion
import Configs.rxMathVersion
import Configs.sharedPreferencesVersion
import Configs.supportMaterialDesignVersion
import Configs.timberVersion

object Configs {
   /* const val app = ":app"
    const val persistenceApp = ":android-baseplate-persistence"
    const val projectCompleteName = "github-members-directory"

    const val daggerVersion = "2.29.1"
    const val coroutineCore = "1.3.7"
    const val coroutineAndroid = "1.3.6"
    const val lifecycleVersion = "2.2.0"
    const val cardViewVersion = "1.0.0"
    const val archVersion = "2.1.0"
    const val retrofitVersion = "2.9.0"
    const val supportMaterialDesignVersion = "1.3.0-alpha03"
    const val converterScalars = "2.9.0"
    const val okhttpVersion = "4.9.0"
    const val okhttpLoggingVersion = "4.9.0"
    const val rxJava2Version = "2.2.10"
    const val rxKotlinVersion = "2.3.0"
    const val rxAndroidVersion = "2.1.1"
    const val rxBindingXVersion = "3.1.0"
    const val rxMathVersion = "0.20.10"
    const val timberVersion = "4.7.1"
    const val ktxCore = "1.3.2"
    const val fragmentKtx = "1.2.5"
    const val sharedPreferencesVersion  = "1.1.1"
    const val archCompVersion = "2.3.0-alpha03"
    const val lottieVersion = "3.4.4"
    const val coilVersion = "0.13.0"
    const val pagingVersion  = "3.0.0-alpha07"
    const val constrainLayoutVersion = "2.0.2"
    const val roundImageVersion = "2.3.0"
    const val navFragmentKtxVersion  = "2.3.1"
    const val navUiKtxVersion = "2.3.1"
    const val androidAnnotation = "28.0.0"
    const val androidxAnnotation = "1.1.0" */
   const val circleImageViewVersion = "3.0.0"
   const val daggerVersion = "2.29.1"
   const val coroutineCore = "1.3.7"
   const val coroutineAndroid = "1.3.6"
   const val lifecycleVersion = "2.2.0"
   const val cardViewVersion = "1.0.0"
   const val archVersion = "2.1.0"
   const val retrofitVersion = "2.9.0"
   const val supportMaterialDesignVersion = "1.3.0-alpha03"
   const val converterScalars = "2.9.0"
   const val okhttpVersion = "4.9.0"
   const val okhttpLoggingVersion = "4.9.0"
   const val rxJava2Version = "2.2.10"
   const val rxKotlinVersion = "2.3.0"
   const val rxAndroidVersion = "2.1.1"
   const val rxBindingXVersion = "3.1.0"
   const val rxMathVersion = "0.20.10"
   const val timberVersion = "4.7.1"
   const val ktxCore = "1.3.2"
   const val fragmentKtx = "1.2.5"
   const val sharedPreferencesVersion  = "1.1.1"
   const val archCompVersion = "2.3.0-alpha03"
   const val lottieVersion = "3.4.4"
   const val coilVersion = "1.0.0"
   const val pagingVersion  = "3.0.0-alpha07"
   const val constrainLayoutVersion = "2.0.2"
   const val roundImageVersion = "2.3.0"
   const val navFragmentKtxVersion  = "2.3.1"
   const val navUiKtxVersion = "2.3.1"
   const val androidAnnotation = "28.0.0"
   const val androidxAnnotation = "1.1.0"
}

object BuildPlugins {
   /* const val androidGradlePlugin = "com.android.tools.build:gradle:${PluginVersion.buildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersion.kotlin}"
    const val dexCountPlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${PluginVersion.dexCount}"

    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${PluginVersion.navigationSafeArgs}"
    const val jetifier = "com.android.tools.build.jetifier:jetifier-processor:${PluginVersion.jetifier}" */
}

object Android {
    // Manifest version information!
    private const val versionMajor = 0
    private const val versionMinor = 4
    private const val versionPatch = 1 // 0.0.0-dev
    private const val versionBuild = 1 // bump for dogfood builds, public betas, etc.

    const val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
    const val minSdkVersion = 26
}

object Libs {
    const val circleImageView = "de.hdodenhof:circleimageview:$circleImageViewVersion"
    const val shimmering = "com.facebook.shimmer:shimmer:0.5.0@aar"
    const val appCompat = "androidx.appcompat:appcompat:1.2.0"
    const val constrainLayout = "androidx.constraintlayout:constraintlayout:2.0.2"
    const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val material = "com.google.android.material:material:1.2.1"
    const val supportMaterials = "com.google.android.material:material:$supportMaterialDesignVersion"
    const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:1.4.10"
    const val coreKtx = "androidx.core:core-ktx:$ktxCore"
    const val annotation = "androidx.annotation:annotation:$androidxAnnotation"
    const val supportAnnotation = "com.android.support:support-annotations:$androidAnnotation"
    const val koinAndroid = "org.koin:koin-android:2.0.1"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:2.0.1"
    const val koinScope = "org.koin:koin-androidx-scope:2.0.1"
    const val httpClient = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    const val httpLogging = "com.squareup.okhttp3:logging-interceptor:$okhttpLoggingVersion"
    const val circleIndicator = "me.relex:circleindicator:2.1.4"
    const val sharedPreferrences = "androidx.preference:preference-ktx:$sharedPreferencesVersion"
    const val roomRuntime = "androidx.room:room-runtime:$archCompVersion"
    const val roomKtx = "androidx.room:room-ktx:$archCompVersion"
    const val roomCompiler = "androidx.room:room-compiler:$archCompVersion"
    const val timber = "com.jakewharton.timber:timber:$timberVersion"
    const val cardView = "androidx.cardview:cardview:$cardViewVersion"
    const val airBnb = "com.airbnb.android:lottie:$lottieVersion"
    const val supportDesign = "com.android.support:design:30.0.0"
    const val roundedImage = "com.makeramen:roundedimageview:$roundImageVersion"
    const val rxJava = "io.reactivex.rxjava2:rxjava:$rxJava2Version"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"
    const val rxExtension = "com.github.akarnokd:rxjava2-extensions:$rxMathVersion"
    const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:$rxBindingXVersion"
    const val rxBindingCore = "com.jakewharton.rxbinding3:rxbinding-core:$rxBindingXVersion"
    const val rxAppCompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:$rxBindingXVersion"
    const val fragmentKtxs = "androidx.fragment:fragment-ktx:$fragmentKtx"
    const val navigationKtx = "androidx.navigation:navigation-fragment-ktx:$navFragmentKtxVersion"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$navUiKtxVersion"
    const val lifeCycleExtension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val viewModelKtxt = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val liveDataKtxt = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val liveDataRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val saveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"
    const val lifeCycleCommon = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
    const val lifeCycleService = "androidx.lifecycle:lifecycle-service:$lifecycleVersion"
    const val lifeCycleProcess = "androidx.lifecycle:lifecycle-process:$lifecycleVersion"
    const val lifeCycleReactive = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycleVersion"
    const val coreTesting = "androidx.arch.core:core-testing:$archVersion"
    const val lifeCycleCompiler = "androidx.lifecycle:lifecycle-compiler:2.2.0"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineCore"
    const val androidCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineAndroid"
    const val coil = "io.coil-kt:coil:$coilVersion"
    const val paging = "androidx.paging:paging-runtime:$pagingVersion"
    const val jUnit = "junit:junit:4.13.1"
    const val jUnitExtension = "androidx.test.ext:junit:1.1.2"
    const val expressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    const val refrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val rxRetrofit = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    const val gSonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val scalarConverter = "com.squareup.retrofit2:converter-scalars:$converterScalars"
    const val legacyContrainLayout = "com.android.support.constraint:constraint-layout:$constrainLayoutVersion"
    const val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    const val daggerSupport = "com.google.dagger:dagger-android-support:$daggerVersion"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    const val daggerProcessor = "com.google.dagger:dagger-android-processor:$daggerVersion"

}