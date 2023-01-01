package io.github.oleksandrpodoliako.nearest.utils;

import java.io.*;

public class FilesUtil {

    public void writeToFile(String fileName, String content) {
        File file = new File(fileName);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
