import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "dev.sjaramillo.jmom"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("J-son validator")
        }
    }
}

kotlin {
    // This example is frontend only. However, for a fullstack app, you can uncomment the includeServer parameter
    // and the `jvmMain` source set below.
    configAsKobwebApplication("j-mom" /*, includeServer = true*/)

    js(IR) {
        browser {
            testTask {
                useKarma {
                    // Use Chrome by default for browser tests
                    useChrome()
                }
            }
        }
        nodejs {
            // Configure NodeJS for running tests
            testTask {
                useMocha()
            }
        }
    }

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.kobwebx.markdown)
            implementation(libs.kotlinx.serialization.json)
        }

        jsTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
