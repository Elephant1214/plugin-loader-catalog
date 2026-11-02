plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "me.elephant1214"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    website = "https://github.com/Elephant1214/plugin-loader-catalog"
    vcsUrl = "https://github.com/Elephant1214/plugin-loader-catalog"

    plugins {
        create("plugin-loader-catalog") {
            id = "me.elephant1214.plugin-loader-catalog"
            implementationClass = "me.elephant1214.loadercatalog.PluginLoaderCatalogPlugin"
            displayName = "Plugin Loader Catalog"
            description = "Generates Java dependency lists from version catalog bundles for the Paper PluginLoader."
            tags = listOf("paper", "minecraft", "version-catalog", "codegen")
        }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
