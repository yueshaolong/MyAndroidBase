package io.mytest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RandomAccessFileT {

    private static String path;

    public static void main(String[] args) {
        String relativelyPath=System.getProperty("user.dir");
        System.out.println("relativelyPath"+relativelyPath);
        path = relativelyPath + "/base/src/main/java/io/mytest/randomAccessFile.txt";
        System.out.println("path"+ path);

        write();

        read();

    }

    public static void read() {
        //从文件中读取内容 这里我们要清楚现在文件中有什么内容、而且还要清楚这些内容起始字节下标、长度
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(path), "r");
            /**
             * 0-50 null
             * 51-60  a
             * 61-100 null
             * 101-111 汉字
             * 112-117 abc
             * 118-1000 null
             */

            //读取我的名字
            randomAccessFile.seek(100);
            String s = randomAccessFile.readUTF();
            System.out.println(s);//岳少龙

            //读取后面添加的
            randomAccessFile.seek(111);
            byte[] bytes = new byte[6];
            randomAccessFile.read(bytes);
            System.out.print(new String(bytes));// a b c
            System.out.println();

            //读取中间的数组
            randomAccessFile.seek(50);
            byte[] bytes1 = new byte[20];
            randomAccessFile.read(bytes1);
            System.out.print(new String(bytes1));// a a a a a a a a a a

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(path), "rw");

            //不会改变文件大小、但是他会将下一个字符的写入位置标识为100、
            //也就是说此后只要写入内容、就是从101开始存
            randomAccessFile.seek(100);
            System.out.println("file length: " + randomAccessFile.length()
                    + "  file pointer: " + randomAccessFile.getFilePointer());//file length: 1000  file pointer: 100

            //会改变文件大小、只是把文件的size改变、
            //并没有改变下一个要写入的内容的位置、
            //这里注释掉是为了验证上面的seek方法的说明内容
            randomAccessFile.setLength(1000);
            System.out.println("file length: " + randomAccessFile.length()
                    + "  file pointer: " + randomAccessFile.getFilePointer());//file length: 1000  file pointer: 100

            //每个汉子占3个字节、写入字符串的时候会有一个记录写入字符串长度的两个字节
            randomAccessFile.writeUTF("岳少龙");//3*3+2=11
            System.out.println("file length: " + randomAccessFile.length()
                    + "  file pointer: " + randomAccessFile.getFilePointer());//file length: 1000  file pointer: 111

            //每个字符占两个字节
            randomAccessFile.writeChar('a');
            randomAccessFile.writeChars("bc");//3*2=6
            System.out.println("file length: " + randomAccessFile.length()
                    + "  file pointer: " + randomAccessFile.getFilePointer());//file length: 1000  file pointer: 117

            //再从“文件指针”为50的地方插一个长度为10、内容全是'a'的字符
            //这里file长依然是117、因为他是从“文件指针”为50的地方覆盖后面
            //的10个字节、下标并没有超过文件长度
            randomAccessFile.seek(50);
            for (int i = 0; i < 10; i++){
                randomAccessFile.writeChar('a');
            }
            System.out.println("file length: " + randomAccessFile.length()
                    + "  file pointer: " + randomAccessFile.getFilePointer());//file length: 1000  file pointer: 70



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
