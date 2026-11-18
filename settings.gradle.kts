pluginManagement {
    // The suite guard lives in its own repository; check it out next to this one.
    //   git clone https://github.com/nijatva/no-gms-check ../no-gms-check
    // Its plugin is not published yet, so it is consumed as an included build.
    includeBuild("../no-gms-check")

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

        // com.nijatva:signature — published by nijatva-core on a release.
        val actor = System.getenv("GITHUB_ACTOR")
        val token = System.getenv("GITHUB_TOKEN")
        if (actor != null && token != null) {
            maven {
                name = "NijatvaGitHubPackages"
                url = uri("https://maven.pkg.github.com/nijatva/nijatva-core")
                credentials {
                    username = actor
                    password = token
                }
            }
        }

        // Local iteration only: ./gradlew :signature:publishToMavenLocal in nijatva-core.
        mavenLocal()
    }
}

rootProject.name = "NijatvaTranslate"
include(":app")
