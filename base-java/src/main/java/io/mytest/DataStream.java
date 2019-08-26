package io.mytest;

import java.io.*;

public class DataStream {

    private static String path;

    public static void main(String[] args) {
        String relativelyPath=System.getProperty("user.dir");
        System.out.println("relativelyPath"+relativelyPath);
        path = relativelyPath + "/base/src/main/java/io/mytest/dataStream.txt";
        System.out.println("path"+ path);

        write();

        read();
    }

    public static void read() {
        try {
            //读和写要一一对应
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(path)));
            System.out.println(dataInputStream.readLong());
            System.out.println(dataInputStream.read());
            System.out.println(dataInputStream.readBoolean());
            System.out.println(dataInputStream.readChar());
            System.out.println(dataInputStream.readInt());
            System.out.println(dataInputStream.readShort());
            System.out.println(dataInputStream.readByte());
            System.out.println(dataInputStream.readByte());
            System.out.println(dataInputStream.readChar());
            System.out.println(dataInputStream.readChar());
            System.out.println(dataInputStream.readChar());
            System.out.println(dataInputStream.readDouble());
            System.out.println(dataInputStream.readFloat());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(path)));
            dataOutputStream.writeLong(12l);
            dataOutputStream.write(1);
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeChar('a');
            dataOutputStream.writeInt(56);
            dataOutputStream.writeShort(68);
            dataOutputStream.writeBytes("sf");
            dataOutputStream.writeChars("jkl");
            dataOutputStream.writeDouble(23.21);
            dataOutputStream.writeFloat(3.2f);

            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
