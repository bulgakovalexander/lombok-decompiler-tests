subprojects {
    var lombokRootDir: String by extra
    lombokRootDir = "$rootDir/../lombok"
    var lombokJarPath: String by extra
    lombokJarPath = "$lombokRootDir/dist/lombok.jar"
}