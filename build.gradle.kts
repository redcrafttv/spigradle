import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("jvm") version "1.4.0"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

/**
 * Location of developers plugins directory in gradle.properties.
 */
val spigotPluginsDir: String? by project

repositories {
    jcenter()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.dmulloy2.net/nexus/repository/public/")
}

dependencies {
    compileOnly(files("libs/paper.jar"))
    compileOnly(files("libs/InformationSystem.jar"))
    compileOnly("net.luckperms", "api", "5.1")
    implementation("fr.minuskube.inv", "smart-invs", "1.2.7")
    compileOnly("org.jetbrains", "annotations", "20.0.0")
    implementation("me.schlaubi", "kaesk", "1.2")
    implementation("com.zaxxer", "HikariCP", "3.4.5")
    implementation("org.jetbrains.exposed", "exposed-core", "0.24.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.24.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.24.1")
    implementation("org.jetbrains.exposed", "exposed-java-time", "0.24.1")
    implementation("mysql", "mysql-connector-java", "8.0.21")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.5.0")
}

tasks {
    processResources {
        from(sourceSets["main"].resources) {
            val tokens = mapOf("version" to version)
            filter(ReplaceTokens::class, mapOf("tokens" to tokens))
        }
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    task<Copy>("installPlugin") {
        dependsOn(jar)
        from(jar)
        include("*.jar")
        into(spigotPluginsDir ?: error("Please set spigotPluginsDir in gradle.properties"))
    }

    shadowJar {
        relocate("com.mysql", "eu.brickpics.staffsys.dependencies.com.mysql")
    }
}
