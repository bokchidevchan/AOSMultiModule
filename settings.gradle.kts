pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "AOSMultiModule"
include(":app")

// Core modules
include(":core:model")
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:domain")
include(":core:data")
include(":core:navigation")
include(":core:ui")

// Feature modules
include(":features:home")
include(":features:detail")
include(":features:search")
include(":features:settings")
include(":features:profile")
