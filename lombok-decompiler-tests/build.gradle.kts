import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler

buildscript {
    var fernflowerJar: String by extra

    fernflowerJar = gradle.includedBuild("fernflower").projectDir.toString() + "/build/libs/fernflower.jar"
    dependencies {
        classpath(files(fernflowerJar))
    }
}

plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

val ecj = configurations.create("ecj")
val lombokRootDir: String by extra
val lombokJarPath: String by extra
val lombokJar = files(lombokJarPath)
val rtJar = files("${lombokRootDir}/lib/oracleJDK8Environment/rt.jar")

dependencies {
    //    compileOnly(gradleApi())
    val ecj = add("ecj", "org.eclipse.jdt.core.compiler:ecj:4.4.2")
    compileOnly(ecj!!)

    compileOnly(lombokJar)
    annotationProcessor(lombokJar)
//    compile("com.google.guava:guava:27.1-jre")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.4.2")
}

val rootSrc = "${lombokRootDir}/test/transform/resource"
//sourceSets.remove(sourceSets.main)

sourceSets.create("val") {
    val main = sourceSets.main.get()
    runtimeClasspath = main.runtimeClasspath
    compileClasspath = main.compileClasspath
    annotationProcessorPath = main.annotationProcessorPath
    java {
        srcDirs(listOf("$rootSrc/before"))
        include("ValLambda.java", "ValInLambda.java", "Val*.java")
        exclude("ValErrors.java", "ValInBasicFor.java", "ValInMultiDeclaration.java")
    }
}

//tasks {
//    compileJava {
//        options.forkOptions.jvmArgs = listOf("-g:lines,vars,source")
//    }
//}

sourceSets.filter { !(it.name in listOf("main", "test")) }.forEach { ss ->
    val compileTaskName = ss.compileJavaTaskName
    val name = ss.name
    val suffixName = name[0].toUpperCase() + name.substring(1)
    val allJava = ss.java

    val decompileJavac = tasks.create("decompile${suffixName}Java") {
        dependsOn(gradle.includedBuild("fernflower").task(":jar"))
        dependsOn(compileTaskName)
        doLast {
            val source = ss.output.classesDirs.files.first()
            val destination = File(projectDir, "build/decompiled/${name}")
            destination.mkdirs()
            val args = arrayOf("-rsy=1", source.toString(), destination.toString())
            ConsoleDecompiler.main(args)
        }
    }

    val compileEcj = tasks.create<org.gradle.api.tasks.compile.JavaCompile>("compile${suffixName}Ecj") {
        source = allJava
        destinationDir = File(projectDir, "build/classes/java/${name}Ecj")
        classpath = ecj.asFileTree
        targetCompatibility = "8"
        sourceCompatibility = "8"
        classpath = rtJar + lombokJar

        options.apply {
            isFork = true
            forkOptions.apply {
                executable = "java"
                jvmArgs = listOf(
                        "-javaagent:" + lombokJar.asPath,
                        "-classpath", ecj.asPath,
                        "org.eclipse.jdt.internal.compiler.batch.Main", "-nowarn", "-g:lines,vars,source",
                        "-preserveAllLocals"
                )
            }
        }
    }

    val decompileEcj = tasks.create("decompile${suffixName}Ecj") {
        dependsOn(gradle.includedBuild("fernflower").task(":jar"))
        dependsOn(compileEcj)
        doLast {
            val source = File(projectDir, "build/classes/java/${name}Ecj")
            val destination = File(projectDir, "build/decompiled/${name}Ecj")
            destination.mkdirs()
            val args = arrayOf("-rsy=1", source.toString(), destination.toString())
            ConsoleDecompiler.main(args)
        }
    }

    tasks["test"].dependsOn(decompileJavac, decompileEcj)
}


//project(":lombok") {
//    ant.importBuild("build.xml")
//    System.out.println(ant.antProject.baseDir)
//}




