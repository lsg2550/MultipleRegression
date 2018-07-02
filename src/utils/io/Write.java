package utils.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Luis Garay
 */
public class Write {

    public static void output(String output) {
        File outputDirectory = new File("output/");

        if (!outputDirectory.exists()) {
            System.out.println("Creating Output Directory...");
            outputDirectory.mkdirs();
        } else {
            System.out.println("Output Directory Found...");
        }

        int counter = 0;
        boolean fileExists = true;

        while (fileExists) {
            String outputName = "/output" + counter + ".txt";
            File outputFileName = new File(outputDirectory.getAbsolutePath() + outputName);

            if (outputFileName.exists()) {
                counter++;
            } else {
                System.out.println("Writing Output In: " + outputFileName.getAbsolutePath());
                Path path = FileSystems.getDefault().getPath(outputFileName.getAbsolutePath());

                try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                    bw.write(output);
                    bw.close();
                } catch (IOException e) {
                    System.out.println("Issue Saving File!");
                } finally {
                    fileExists = false;
                }
            }
        }
    }
}
