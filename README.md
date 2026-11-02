# plugin-loader-catalog

A simple Gradle plugin for dumping your dependency bundles into a Java class.

# Usage

`settings.gradle.kts`:

```kt
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "me.elephant1214.plugin-loader-catalog") {
                useModule("com.github.Elephant1214:plugin-loader-catalog:${requested.version}")
            }
        }
    }
}
```

`build.gradle.kts`:

```kt
plugins {
    id("com.github.Elephant1214.plugin-loader-catalog") version "latestRelease"
}

// ...

pluginLoaderCatalog {
    packageName = "com.example.plugin"
    className = "VersionCatalog"
    bundles = setOf("bundle1", "bundle2")
}
```
