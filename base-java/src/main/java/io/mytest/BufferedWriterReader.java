package io.mytest;

import java.io.*;

public class BufferedWriterReader {

    private static String path;

    public static void main(String[] args) {
        String relativelyPath=System.getProperty("user.dir");
        System.out.println("relativelyPath"+relativelyPath);
        path = relativelyPath + "/base/src/main/java/io/mytest/bufferedWriterReader.txt";
        System.out.println("path"+ path);

        writer();

        reader();
    }

    public static void reader() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
            String line = bufferedReader.readLine();
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writer() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));
            bufferedWriter.write(65);
            bufferedWriter.write("sdfhsklgf");
            bufferedWriter.write("ljen", 1, 2);
            bufferedWriter.write(new char[]{'r'});
            bufferedWriter.write(new char[]{'r', 'e', 't'}, 0, 3);

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
