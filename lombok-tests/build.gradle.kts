import java.io.FileInputStream
import java.util.*

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

val lombokRootDir: String by extra
val lombokJarPath: String by extra
val lombokJar = files(lombokJarPath)

val testBootclasspath = configurations.create("testBootclasspath")
val rtLib6 = configurations.create("rtLib6")
val rtLib8 = configurations.create("rtLib8")
val moduleBuild = configurations.create("moduleBuild")


val props = Properties()
props.load(FileInputStream(File(lombokRootDir, "testenvironment.properties")))

props.forEach { prop ->
    project.ext[prop.key.toString()] = prop.value
}

dependencies {
    //    compileOnly(gradleApi())

    this.add("moduleBuild", fileTree("$lombokRootDir/lib/moduleBuild"))
    this.add("testBootclasspath", files("$lombokRootDir/" + project.ext.get("test.location.bootclasspath")))
//    this.add("rtLib6", files("lib/openJDK6Environment/rt-openjdk6.jar"))
    val dependency = this.add("rtLib8", files("$lombokRootDir/lib/openJDK8Environment/openjdk8_rt.jar"))
    compileOnly(dependency!!)

    compileOnly(fileTree("$lombokRootDir/lib/build"))

    compileOnly(fileTree("$lombokRootDir/lib/unexpected"))
    compileOnly(fileTree("$lombokRootDir/" + project.ext.get("test.location.ecj") as String))
    //for debugging EclipsePatcher
//    compileOnly("org.ow2.asm:asm:5.0.1")
//    compileOnly("org.ow2.asm:asm-tree:5.0.1")
//    compileOnly("org.ow2.asm:asm-commons:5.0.1")

    testCompile("junit:junit:4.12")

    testCompile(fileTree("$lombokRootDir/lib/test").include("*.jar").exclude("junit-junit.jar"))
    testCompile(fileTree("$lombokRootDir/" + project.ext.get("test.location.ecj") as String))
    testCompile(fileTree("$lombokRootDir/" + project.ext.get("test.location.javac") as String))

}


sourceSets {
    val mainOutput = sourceSets["main"].output
    val mainOutDir = mainOutput.classesDirs.singleFile
    val stubsStubs = create("stubsStubs") {
        java.srcDir(listOf("$lombokRootDir/src/stubsstubs"))
    }
    val stubs = create("stubs") {
        java {
            srcDir(listOf("$lombokRootDir/src/stubs", "$lombokRootDir/src/javac-only-stubs"))
            compileClasspath = stubsStubs.output
        }
    }

    val utils1 = create("utils1") {
        java {
            srcDir(listOf("$lombokRootDir/src/utils/"))
            exclude("lombok/javac/**")
//            outputDir = mainOutDir
        }
        compileClasspath = configurations["compileOnly"]
    }

    val utils2 = create("utils2") {
        java {
            srcDir(listOf("$lombokRootDir/src/utils/"))
            include("lombok/javac/**")
//            outputDir = mainOutDir
        }
        compileClasspath = stubs.output + utils1.output + configurations["compileOnly"]
    }

    main {
        //        compileClasspath += /*lombok1.output + lombok2.output + */ + configurations["compileOnly"]
        java {
            compileClasspath = stubs.output + utils1.output + utils2.output + configurations["compileOnly"]
            srcDirs(listOf(
                    "src/launch",
                    "src/core",
                    "src/installer",
                    "src/eclipseAgent",
                    "src/delombok"
            ).map { "$lombokRootDir/$it" })
            exclude("**/*Transplants.java")
        }

        resources {
            srcDirs(sourceSets["main"].java.srcDirs)
            exclude("**/*.java")
        }
    }

    val class50 = create("class50") {
        compileClasspath += stubs.output + utils1.output + utils2.output + rtLib8 + configurations["compileOnly"] + mainOutput
        java {
            srcDirs(listOf(
                    "$lombokRootDir/src/eclipseAgent"
            ))
            include("**/*Transplants.java", "lombok/launch/PatchFixesHider.java")
            outputDir = File(mainOutDir, "Class50")
        }
    }

    if (JavaVersion.current().isJava9Compatible()) {
        create("core9") {
            java {
                srcDirs(listOf("$lombokRootDir/src/core9"))
            }
        }
    }

    val lombokMapstruct = create("lombokMapstruct") {
        java {
            srcDirs(listOf("$lombokRootDir/src/j9stubs"))
            outputDir = File(mainOutDir, "lombok/secondaryLoading.SCL.lombok")
        }
        compileClasspath = sourceSets["main"].output
    }


    test {
        java {
            srcDir(listOf(
                    "test/bytecode/src",
                    "test/configuration/src",
                    "test/core/src",
                    "test/transform/src"
            ).map { "$lombokRootDir/$it" })
        }
        compileClasspath += sourceSets["main"].compileClasspath + utils1.output + utils2.output
        resources {
            srcDir(listOf(
                    "test/bytecode/resource",
                    "test/configuration/resource",
                    "test/transform/resource"
            ).map { "$lombokRootDir/$it" })
        }
    }
    all {
        //exclude empty dirs from source sets
        tasks[processResourcesTaskName].setProperty("includeEmptyDirs", false)
    }
}


val stubsOutput = sourceSets["stubs"].output.classesDirs
tasks.withType<JavaCompile> {
    val stubsStubsOutput = sourceSets["stubsStubs"].output.classesDirs

    options.encoding = "UTF-8"
    sourceCompatibility = "1.6"
    targetCompatibility = sourceCompatibility

    if (name in listOf("compileStubsStubsJava", "compileStubsJava")) {
        if (JavaVersion.current().isJava9Compatible()) {
            options.compilerArgs = listOf("--release", "8")
        }
        sourceCompatibility = "1.5"
        targetCompatibility = sourceCompatibility
        options.bootstrapClasspath = rtLib8.asFileTree
    }

    if (this.name == "compileUtils1Java") {
        if (JavaVersion.current().isJava9Compatible()) {
            options.compilerArgs = listOf("--release", "6")
        }
        sourceCompatibility = "1.5"
        targetCompatibility = sourceCompatibility
        options.bootstrapClasspath = stubsStubsOutput + stubsOutput + rtLib8.asFileTree
    }

    if (this.name == "compileUtils2Java") {
        if (JavaVersion.current().isJava9Compatible()) {
            options.compilerArgs = listOf("--release", "6")
        }
        sourceCompatibility = "1.6"
        targetCompatibility = sourceCompatibility
        options.bootstrapClasspath = stubsStubsOutput + stubsOutput + rtLib8.asFileTree
    }

    if (name == "compileLombok1Java") {
        doFirst {
            if (JavaVersion.current().isJava9Compatible()) {
                options.compilerArgs = listOf("--release", "8")
            }
        }
        sourceCompatibility = "1.5"
        targetCompatibility = sourceCompatibility
        options.bootstrapClasspath = stubsStubsOutput + stubsOutput + rtLib8.asFileTree
    }

    if (name == "compileCore9Java") {
        dependsOn("compileJava")
        mustRunAfter("coreBySpiProcessor")

        val mainOutput = sourceSets["main"].output.classesDirs.asPath

        doFirst {
            options.compilerArgs = listOf(
                    "--release", "9",
                    "-Xlint:none",
                    "-d", mainOutput,
                    "--module-path", moduleBuild.asPath
            )
        }

        val source = this.source
        val destinationDir = this.destinationDir

        doLast {
            source.forEach { f ->
                val fileName = f.nameWithoutExtension + ".class"
                val classFile = File(mainOutput, fileName)
                val copyTo = classFile.copyTo(File(destinationDir, fileName))
                System.out.println("$classFile->$copyTo")
                classFile.delete()

            }
        }
    }

    if (name == "compileLombokMapstructJava") {
        doLast {
            this.outputs.files.asFileTree.filter({ f -> f.isFile }).forEach { f ->
                f.renameTo(File(f.parentFile, f!!.nameWithoutExtension + ".SCL.lombok"))
            }
        }
    }


    if (name == "compileJava") {
        options.bootstrapClasspath = stubsOutput + rtLib8.asFileTree
    }
}

tasks.create<JavaCompile>("coreBySpiProcessor") {
    dependsOn("compileJava")
//    options.isVerbose = true
    options.compilerArgs = listOf(
            "--release", "9",
            "-proc:only",
            "-processor", "org.mangosdk.spi.processor.SpiProcessor"
    )
    val main = sourceSets["main"]
    classpath = main.compileClasspath
    options.annotationProcessorPath = configurations["compileOnly"]
    source = main.allSource
    destinationDir = main.output.classesDirs.singleFile
}

tasks.withType<Test> {
    systemProperty("file.encoding", "utf-8")
    this.workingDir = File(lombokRootDir)
}

val echoProcessor = tasks.create("echoProcessor") {
    doLast {
        val path = sourceSets["main"].output.classesDirs.singleFile
        val file = File(path, "/META-INF/services/javax.annotation.processing.Processor")
        file.parentFile.mkdirs()
        file.delete()
//        file.createNewFile()
        file.appendText("lombok.launch.AnnotationProcessorHider\$AnnotationProcessor" +
                "\n" +
                "lombok.launch.AnnotationProcessorHider\$ClaimingProcessor"
        )
    }
}

tasks["classes"].dependsOn(echoProcessor)

tasks.withType<Jar> {
    if (name == "jar") {
        var utilsOutput = sourceSets["utils1"].output + sourceSets["utils2"].output
        if (sourceSets.names.contains("core9")) {
            utilsOutput += sourceSets["core9"].output
        }
        from(utilsOutput)
        if (tasks.names.contains("compileCore9Java")) {
            dependsOn("compileCore9Java")
        }
        dependsOn("compileClass50Java", "compileLombokMapstructJava", "coreBySpiProcessor")
        destinationDir = file("dist")
        archiveName = "$baseName.$extension"
        manifest {
            attributes(
                    mapOf(
                            "Premain-Class" to "lombok.launch.Agent",
                            "Agent-Class" to "lombok.launch.Agent",
                            "Can-Redefine-Classes" to "true",
                            "Main-Class" to "lombok.launch.Main"//,
//                            "Lombok-Version" to archiveVersion.get()
                    )
            )
        }
    }
}

tasks.withType<Test> {
    val jar = tasks["jar"] as Jar

    //        "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
    this.jvmArgs(listOf(
            "-javaagent:${jar.archivePath}",
//            "-Ddelombok.bootclasspath", testBootclasspath.asPath,
            "--add-opens", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
            "--add-opens", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED"
    ))
    //use all classes except RunAllTests
//    include("lombok/RunAllTests.class")
    include("lombok/transform/RunTransformTests.class")
    testLogging.showStandardStreams = true
}

//for supporing eclipse compiler
tasks["test"].dependsOn(tasks["jar"])

