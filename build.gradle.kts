plugins {
    id("xyz.wagyourtail.unimined") version "1.2.0-SNAPSHOT"

}

group = "xyz.wagyourtail"
version = "1.0-SNAPSHOT"

base {
    archivesName = "sound-categories"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
}

unimined.minecraft {
    version("b1.7.3")
    side("client")

    mappings {
        calamus()
        feather(20)
    }

    legacyFabric {
        customIntermediaries = true
        loader("0.15.3")
        accessWidener(file("src/main/resources/sound-categories.accesswidener"))
    }
}

dependencies {
    val modImplementation by configurations.getting
    modImplementation("net.ornithemc.osl:core:0.5.0")
    modImplementation("net.ornithemc.osl:entrypoints:0.4.2+client-mca1.0.6-mc12w30e")
    modImplementation("net.ornithemc.osl:lifecycle-events:0.5.2+client-mcb1.3-1750-mcb1.7.3")
    modImplementation("net.ornithemc.osl:resource-loader:0.3.0+client-mca1.2.2-1624-mc11w48a")

    val minecraftLibraries by configurations.getting
    minecraftLibraries("org.apache.logging.log4j:log4j-core:2.8.1")
    minecraftLibraries("org.apache.logging.log4j:log4j-api:2.8.1")
}

tasks.processResources {
    inputs.property("version", project.version)

    from("fabric.mod.json") {
        expand("version" to project.version)
    }
}