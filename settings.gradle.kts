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

rootProject.name = "movies"
include(":app")
include(":core:base")
include(":core:data:remote")
include(":core:data:repository")
include(":core:data:local")
include(":feature:auth:domain:usecase")
include(":feature:auth:presentation:ui")
include(":feature:auth:presentation:viewModel")
include(":feature:home:domain:useCase")
include(":core:domain:useCase")
include(":core:domain:entities")
include (":feature:home:presentation:viewModel")
include(":feature:home:presentation:ui")
