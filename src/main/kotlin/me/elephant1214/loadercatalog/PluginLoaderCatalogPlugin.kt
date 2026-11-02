package me.elephant1214.loadercatalog

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

@Suppress("unused")
class PluginLoaderCatalogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create<PluginLoaderCatalogExtension>("pluginLoaderCatalog").apply {
            catalogName.convention("libs")
            className.convention("LoaderCatalog")
        }

        val generate = project.tasks.register<GenerateLoaderCatalog>("generateLoaderCatalog") {
            packageName.set(ext.packageName)
            className.set(ext.className)
            outputDir.set(project.layout.buildDirectory.dir("generated/sources/pluginLoaderCatalog/java/main"))
            bundles.set(project.provider {
                val catalog = project.extensions.getByType<VersionCatalogsExtension>()
                    .named(ext.catalogName.get())
                val selected = ext.bundles.get().ifEmpty { catalog.bundleAliases.toSet() }

                selected.associateWith { alias ->
                    val bundle = catalog.findBundle(alias)
                        .orElseThrow { GradleException("No bundle '$alias' in catalog '${ext.catalogName.get()}'") }
                    bundle.get().map { lib ->
                        val version = lib.version
                            ?: throw GradleException("${lib.module} in bundle '$alias' has no version")
                        "${lib.group}:${lib.name}:$version"
                    }
                }
            })
        }

        project.pluginManager.withPlugin("java") {
            project.extensions.getByType<SourceSetContainer>().named("main") {
                java.srcDir(generate)
            }
        }
    }
}