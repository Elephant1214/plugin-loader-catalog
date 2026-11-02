package me.elephant1214.loadercatalog

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class PluginLoaderCatalogTest {
    @TempDir
    lateinit var dir: File

    @Test
    fun `generates class from bundle`() {
        dir.resolve("settings.gradle.kts").writeText("""rootProject.name = "test"""")

        dir.resolve("gradle").mkdirs()
        dir.resolve("gradle/libs.versions.toml").writeText(
            """
            [libraries]
            gson = "com.google.code.gson:gson:2.14.0"
            
            [bundles]
            runtime = ["gson"]
            """.trimIndent()
        )

        dir.resolve("build.gradle.kts").writeText(
            """
            plugins {
                java
                id("me.elephant1214.plugin-loader-catalog")
            }
            
            pluginLoaderCatalog {
                packageName = "test.pkg"
            }
            """.trimIndent()
        )

        GradleRunner.create()
            .withProjectDir(dir)
            .withPluginClasspath()
            .withArguments("generateLoaderCatalog", "--configuration-cache")
            .build()

        val out = dir.resolve(
            "build/generated/sources/pluginLoaderCatalog/java/main/test/pkg/LoaderCatalog.java"
        )
        assertTrue(out.exists(), "Generated file not found")
        assertTrue(out.readText().contains("com.google.code.gson:gson:2.14.0"))
        assertTrue(out.readText().contains("RUNTIME"))
    }
}
