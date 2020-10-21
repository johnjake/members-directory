buildscript {
    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
    }
}

plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.0")
}