import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"

    id("io.quarkus") version "2.3.0.Final"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.31"
}

group = "com.example"
version = "1.0-SNAPSHOT"

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val quarkusPluginVersion: String by project
val kotlinVersion: String by project

repositories {
    mavenCentral()
}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "16"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform("io.quarkus.platform:quarkus-bom:2.3.0.Final"))
                implementation("io.quarkus:quarkus-undertow")
            }
        }
        val jvmTest by getting {
            kotlin.srcDir("$projectDir/src/jvmTest/kotlin")

            dependencies {
                implementation("io.quarkus:quarkus-junit5:$quarkusPlatformVersion")
                implementation(kotlin("test"))
            }


        }
    }
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

quarkus {
    setSourceDir("$projectDir/src/jvmMain/kotlin")
    setOutputDirectory("$projectDir/build/classes/kotlin/jvm/main")
}

tasks {
    quarkusDev {
        setSourceDir("$projectDir/src/jvmMain/kotlin")
    }
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

