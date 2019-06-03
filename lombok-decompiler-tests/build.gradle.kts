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
    add("ecj", "org.eclipse.jdt.core.compiler:ecj:4.4.2")

    compileOnly(lombokJar)
    annotationProcessor(lombokJar)
//    compile("com.google.guava:guava:27.1-jre")

}

val rootSrc = "${lombokRootDir}/test/transform/resource"
sourceSets.main {
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

sourceSets.forEach { ss ->
    val name = ss.name
    val suffixName = name[0].toUpperCase() + name.substring(1)
    val allJava = ss.java

    val decompileJavac = tasks.create("decompile${suffixName}Java") {
        dependsOn(gradle.includedBuild("fernflower").task(":build"))
        dependsOn("classes")
        doLast {
            val source = ss.output.classesDirs.files.first()
            val destination = File("build/decompiled/${name}")
            destination.mkdirs()
            val args = arrayOf("-rsy=1", source.toString(), destination.toString())
            ConsoleDecompiler.main(args)
        }
    }

    val compileEcj = tasks.create<org.gradle.api.tasks.compile.JavaCompile>("compile${suffixName}Ecj") {
        source = allJava
        destinationDir = File("build/classes/java/${name}Ecj")
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
                        "org.eclipse.jdt.internal.compiler.batch.Main", "-nowarn", "-g:lines,vars,source"
                )
            }
        }
    }

    val decompileEcj = tasks.create("decompile${suffixName}Ecj") {
        dependsOn(gradle.includedBuild("fernflower").task(":build"))
        dependsOn(compileEcj)
        doLast {
            val source = File("build/classes/java/${name}Ecj")
            val destination = File("build/decompiled/${name}Ecj")
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




