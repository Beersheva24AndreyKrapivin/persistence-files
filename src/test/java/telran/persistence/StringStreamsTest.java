package telran.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class StringStreamsTest {
    static final String PRINT_STREAM_FILE = "printStreamFile.txt";
    static final String PRINT_WRITER_FILE = "printWriterFile.txt";

    @Test
    @Disabled
    void printStreamTest() throws Exception {
        PrintStream printStream = new PrintStream(PRINT_STREAM_FILE);
        printStream.println("HELLO");
        printStream.close();
    }

    @Test
    @Disabled
    void printWriterTest() throws Exception {
        PrintWriter printWriter = new PrintWriter(PRINT_WRITER_FILE);
        printWriter.println("HELLO");
        printWriter.close();
    }

    @Test
    @Disabled
    void bufferedReaderTest() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(PRINT_WRITER_FILE));
        assertEquals("HELLO", reader.readLine());
        reader.close();
    }

    @Test
    void printingDirectoryTest() throws IOException {
        printDirectoryContent("D:\\Programming\\Test", 3);
    }

    /**
     * 
     * @param path  - path to a directory
     * @param depth - number of been walked levels
     * @throws IOException
     */
    void printDirectoryContent(String path, int dept) throws IOException {
        // TODO
        //dir1
          //dir11
            //file
            //dir111
          //dir12
        // Consider class Path
        // Consider class Files
        // Consider method Files.walkFileTree
        Path root = Paths.get(path);
        int startCount = root.getNameCount();
        Files.walkFileTree(root, EnumSet.noneOf(FileVisitOption.class), dept, new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                printPath(dir, startCount);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                printPath(file, startCount);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                exc.printStackTrace();
                printPath(file, startCount);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    exc.printStackTrace();
                    printPath(dir, startCount);
                }
                return FileVisitResult.CONTINUE;
            }

            private void printPath(Path path, int startCount) {
                System.out.printf("%s//%s", " ".repeat((path.getNameCount() - startCount) * 2), path.getFileName());
                System.out.print("\n");
            }

        });
    }
}
