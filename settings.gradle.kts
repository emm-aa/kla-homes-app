pluginManagement {
    repositories {
        google()
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

rootProject.name = "KampalaHomes"
include(
    ":app",
    ":core-ui",
    ":core-model",
    ":core-data",
    ":feature-auth",
    ":feature-onboarding",
    ":feature-home",
    ":feature-map",
    ":feature-property",
    ":feature-saved",
    ":feature-profile",
    ":feature-support"
)
