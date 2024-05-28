buildscript {
    extra.apply{
         set("compose_version" , "1.5.1")
        set("compose_coil" , "2.4.0")
        set("compose_nav_version" ,"2.5.3")
        set("compose_compiler_version" , "1.2.0")
        set("hilt_version" ,"2.46.1")
        set("hilt_navigation","1.0.0")
        set("compose_constraint" ,"1.0.1")
        set("lifecycle_version" ,"2.4.0")
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id ("com.android.library") version "7.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}