import gg.essential.gradle.util.noRunConfigs
import gg.essential.gradle.util.noServerRunConfigs

plugins {
    id("java")
    kotlin("jvm") version "2.0.0"
    id("gg.essential.multi-version.root") version "0.6.10"
    id("gg.essential.defaults.loom") version "0.6.10"
    id("gg.essential.defaults.repo") version "0.6.10"
    id("gg.essential.defaults") version "0.6.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java.withSourcesJar()
loom.noRunConfigs()
loom.noServerRunConfigs()

val version: String by project

val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    shadowImpl(kotlin("stdlib-jdk8"))
}

tasks {
    jar {
        archiveBaseName.set(version)
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        configurations = listOf(shadowImpl)
        mergeServiceFiles()
    }
}