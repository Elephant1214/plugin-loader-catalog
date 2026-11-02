# plugin-loader-catalog

A simple Gradle plugin for dumping your dependency bundles into a Java class.

# Usage

`settings.gradle.kts`:

```kt
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
```

`build.gradle.kts`:

```kt
plugins {
    id("com.github.Elephant1214.plugin-loader-catalog") version "latestRelease"
}
```
