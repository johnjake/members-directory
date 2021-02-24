// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion by extra("1.4.10")
    repositories {
        google()
        jcenter()
        jcenter {
            content {
                includeGroup ("org.jetbrains.kotlinx")
            }
        }
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.1.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.3")
        classpath("org.gradle.kotlin:gradle-kotlin-dsl-conventions:0.5.0")

        classpath ("com.google.gms:google-services:4.3.5")

    }
}

plugins {
    id ("io.gitlab.arturbosch.detekt") version "1.10.0"
    id ("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

subprojects {
    apply (plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint.debug.set(true)
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
