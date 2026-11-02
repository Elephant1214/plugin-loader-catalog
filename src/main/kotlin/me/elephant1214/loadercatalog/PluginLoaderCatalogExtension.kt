package me.elephant1214.loadercatalog

import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty

abstract class PluginLoaderCatalogExtension {
    abstract val catalogName: Property<String>
    abstract val packageName: Property<String>
    abstract val className: Property<String>

    /**
     * Bundles to export. Empty means all bundles.
     */
    abstract val bundles: SetProperty<String>
}
