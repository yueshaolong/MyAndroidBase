package io.mytest;

import java.io.*;

public class BufferedStream {

    public static byte[] bytes = new byte[]{0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A};
    private static String path;

    public static void main(String[] args) {
//        path();
        String relativelyPath=System.getProperty("user.dir");
        System.out.println("relativelyPath"+relativelyPath);
        path = relativelyPath + "/base/src/main/java/io/mytest/bufferedStream.txt";
        System.out.println("path"+ path);

//        bufferedOutputStreamTest();
        bufferedInputStreamTest();
    }



    private static void bufferedInputStreamTest() {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
            int read = bufferedInputStream.read();
            System.out.println("read:"+read);//97;读取了第一个字符
            System.out.println("read:"+new String(new byte[]{(byte) read}));
            for(int i = 0; i < 10; i++) {
                if (bufferedInputStream.available() >= 0) {
                    byte b = (byte) bufferedInputStream.read();
                    System.out.print(b+" ");
                    System.out.print(new String(new byte[]{b})+" ");
                }
            }
//            97 a 98 b 99 c 100 d 101 e 102 f 103 g 104 h 105 i 106 j
//            连续读取10个数据;
            System.out.println();
            bufferedInputStream.mark(1024);//在标记位置变为无效之前可以读取的最大字节数限制。

            long skip = bufferedInputStream.skip(2);
            System.out.println("skip:"+skip);//skip:10  实际跳过的字节数

            byte[] bytes = new byte[1024];
            int b1 = bufferedInputStream.read(bytes);
            System.out.println(b1);//文件的byte数组剩余的长度
            for(byte b: bytes) {
                if (b != 0) {
                    System.out.print(new String(new byte[]{b}) + " ");
                }
            }
            System.out.println();

            bufferedInputStream.reset();//配合mark使用的；把流从上面调用mark的地方从新开始读取数据
            System.out.println(bufferedInputStream.read());
            byte[] bytes2 = new byte[1024];
            int b2 = bufferedInputStream.read(bytes2, 2, 10);
            System.out.println(b2);
            for(byte b: bytes2) {
                if (b != 0) {
                    System.out.print(new String(new byte[]{b}) + " ");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bufferedOutputStreamTest() {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            bufferedOutputStream.write(bytes[0]);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.write(bytes, 2, bytes.length - 2);

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void path() {
        System.out.println("Java运行时环境版本:"+System.getProperty("java.version"));
        System.out.println("Java 运行时环境供应商:"+System.getProperty("java.vendor"));
        System.out.println("Java 供应商的URL:"+System.getProperty("java.vendor.url"));
        System.out.println("Java安装目录:"+System.getProperty("java.home"));
        System.out.println("Java 虚拟机规范版本:"+System.getProperty("java.vm.specification.version"));
        System.out.println("Java 类格式版本号:"+System.getProperty("java.class.version"));
        System.out.println("Java类路径："+System.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表:"+System.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径:"+System.getProperty("java.io.tmpdir"));
        System.out.println("要使用的 JIT 编译器的名称:"+System.getProperty("java.compiler"));
        System.out.println("一个或多个扩展目录的路径:"+System.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称:"+System.getProperty("os.name"));
        System.out.println("操作系统的架构:"+System.getProperty("os.arch"));
        System.out.println("操作系统的版本:"+System.getProperty("os.version"));
        System.out.println("文件分隔符（在 UNIX 系统中是“/”）:"+System.getProperty("file.separator"));
        System.out.println("路径分隔符（在 UNIX 系统中是“:”）:"+System.getProperty("path.separator"));
        System.out.println("行分隔符（在 UNIX 系统中是“/n”）:"+System.getProperty("line.separator"));
        System.out.println("用户的账户名称:"+System.getProperty("user.name"));
        System.out.println("用户的主目录:"+System.getProperty("user.home"));
        System.out.println("用户的当前工作目录:"+System.getProperty("user.dir"));
        System.out.println("当前的classpath的绝对路径的URI表示法:" + Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println("得到的是当前的classpath的绝对URI路径:"+ BufferedStream.class.getResource("/"));
        System.out.println("得到的是当前类BufferedStream.class文件的URI目录:"+BufferedStream.class.getResource(""));
    }
}
