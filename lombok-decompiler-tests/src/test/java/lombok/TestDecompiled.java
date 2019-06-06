package lombok;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDecompiled {

    @Test
    public void testDecompiled() {

        String baseDir = System.getProperty("base.dir");
        File baseDirFile;
        if (baseDir != null) {
            baseDirFile = new File(baseDir);
        } else try {
            baseDirFile = new File(".").getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File rootDecompiledDir = new File(baseDirFile, "/build/decompiled");
        Path rootDecompiledPath = rootDecompiledDir.toPath();
        File rootExpectedDir = new File(baseDirFile, "/src/test/resources/expected");
        Path rootExpectedPath = rootExpectedDir.toPath();

        stream(nonNull(rootExpectedDir.listFiles(File::isDirectory))).forEach(expectedDir ->
                stream(nonNull(expectedDir.listFiles((dir, name) -> isJavaFile(name))))
                        .forEach(expectedFile -> {
                            Path relativize = rootExpectedPath.relativize(expectedFile.toPath());
                            Path decompiledPath = rootDecompiledPath.resolve(relativize);
                            File decompiledFile = decompiledPath.toFile();
                            assertTrue(decompiledFile.exists(), decompiledFile + " desn't exist");
                            assertContentEquals(expectedFile, decompiledFile);
                        }));

    }

    private void assertContentEquals(File expected, File actual) {
        int lineNum = 0;
        try (BufferedReader decompiledReader = new BufferedReader(new FileReader(actual));
             BufferedReader expectedReader = new BufferedReader(new FileReader(expected))) {
            lineNum++;
            String decompiledLine = decompiledReader.readLine();
            String expectedLine = expectedReader.readLine();
            assertEquals(expectedLine, decompiledLine,
                    "file:" + actual + ", line:" + lineNum + ", expected:" + expectedLine +
                            ", but decompiled:" + decompiledLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isJavaFile(String name) {
        int extensionIndex = name.lastIndexOf(".");
        String extension = extensionIndex > 0 ? name.substring(extensionIndex + 1).toLowerCase() : "";
        return "java".equals(extension);
    }

    private File[] nonNull(File[] files) {
        if (files == null) files = new File[0];
        return files;
    }
}
