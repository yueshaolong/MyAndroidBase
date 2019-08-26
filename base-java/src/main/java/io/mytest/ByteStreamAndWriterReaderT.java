package io.mytest;

import java.io.*;
import java.nio.charset.CharsetEncoder;

public class ByteStreamAndWriterReaderT {

    private static String path;

    public static void main(String[] args) {
        String relativelyPath=System.getProperty("user.dir");
        System.out.println("relativelyPath"+relativelyPath);


        write(relativelyPath);

        read(relativelyPath);

    }

    public static void read(String relativelyPath) {
        try {
            path = relativelyPath + "/base/src/main/java/io/mytest/streamTowr1.txt";
            System.out.println("path"+ path);
            InputStreamReader inputStreamReader1 = new InputStreamReader(new FileInputStream(new File(path)));
            System.out.println(inputStreamReader1.getEncoding());
            BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
            String s1;
            while ((s1 = bufferedReader1.readLine()) != null){
                System.out.println(s1);
            };
            inputStreamReader1.close();

            path = relativelyPath + "/base/src/main/java/io/mytest/streamTowr2.txt";
            System.out.println("path"+ path);
            InputStreamReader inputStreamReader2 = new InputStreamReader(new FileInputStream(new File(path)), "GBK");
            System.out.println(inputStreamReader2.getEncoding());
            BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);
            String s2;
            while ((s2 = bufferedReader2.readLine()) != null){
                System.out.println(s2);
            };
            inputStreamReader2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String relativelyPath) {
        try {
            path = relativelyPath + "/base/src/main/java/io/mytest/streamTowr1.txt";
            System.out.println("path"+ path);
            OutputStreamWriter outputStreamWriter1 = new OutputStreamWriter(new FileOutputStream(new File(path)));
            outputStreamWriter1.write("abcd");
            System.out.println(outputStreamWriter1.getEncoding());
            outputStreamWriter1.flush();
            outputStreamWriter1.close();

            path = relativelyPath + "/base/src/main/java/io/mytest/streamTowr2.txt";
            System.out.println("path"+ path);
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(new File(path)), "GBK");
            outputStreamWriter2.write("abcd");
            System.out.println(outputStreamWriter2.getEncoding());
            outputStreamWriter2.flush();
            outputStreamWriter2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
