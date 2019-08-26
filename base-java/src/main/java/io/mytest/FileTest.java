package io.mytest;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileTest {
    public static void main(String[] args) {
        String relativelyPath=System.getProperty("user.dir");
//        System.out.println("relativelyPath"+relativelyPath);
        String path = relativelyPath + "/base/src/main/java/io/mytest";
        System.out.println("path:"+ path);

        File file = new File(path);
        System.out.println(file.isDirectory());//true
        String[] list = file.list();
        System.out.println(Arrays.toString(list));//列出所有的文件名称
        String[] list1 = file.list((dir, name) -> {
                return name.endsWith(".txt");
        });
//        System.out.println(Arrays.toString(list1));//默认是正序，不区分大小写
//        Arrays.sort(list1);//正序,区分大小写；先大写再小写
        Arrays.sort(list1, new Comparator<String>() {//倒序
            @Override
            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);//区分大小写
                return o2.compareToIgnoreCase(o1);//不区分
            }
        });
//        Arrays.sort(list1, String::compareTo);//正序，区分大小写
//        Arrays.sort(list1, String::compareToIgnoreCase);//正序，忽略大小写
//        Arrays.sort(list1, String.CASE_INSENSITIVE_ORDER);//正序，忽略大小写
        System.out.println(Arrays.toString(list1));//列出所有的txt文件

    }
}
