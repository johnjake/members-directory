import Configs.circleImageViewVersion

object Configs {
    const val app = ":app"
    const val persistenceApp = ":android-baseplate-persistence"
    const val projectCompleteName = "github-members-directory"
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
    const val coilVersion = "0.13.0"
    const val pagingVersion  = "3.0.0-alpha07"
    const val constrainLayoutVersion = "2.0.2"
    const val roundImageVersion = "2.3.0"
    const val navFragmentKtxVersion  = "2.3.1"
    const val navUiKtxVersion = "2.3.1"
    const val androidAnnotation = "28.0.0"
    const val androidxAnnotation = "1.1.0"
}

object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${PluginVersion.buildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersion.kotlin}"
    const val dexCountPlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${PluginVersion.dexCount}"

    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${PluginVersion.navigationSafeArgs}"
    const val jetifier = "com.android.tools.build.jetifier:jetifier-processor:${PluginVersion.jetifier}"

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
}