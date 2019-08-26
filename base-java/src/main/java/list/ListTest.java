package list;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("a");
        strings.add("c");
        strings.add("a");
        strings.add("d");
        System.out.println(strings);//[a, b, a, c, a, d]
//        strings.remove(1);//[a, c, a, d]
        strings.remove("a");//[b, a, c, a, d]只会删除list中的第一个
        System.out.println(strings);

        List<Integer> integers = new ArrayList<>();
        integers.add(10);
        integers.add(1);
        integers.add(0);
        integers.add(3);
        integers.add(14);
        integers.add(1);
        System.out.println(integers);//[10, 1, 0, 3, 14, 1]
//        integers.remove(1);//[10, 0, 3, 14, 1]
        integers.remove(new Integer(1));//[10, 0, 3, 14, 1]  用对象删除时，数字要使用包装类型；且只会删除list中的第一个
        System.out.println(integers);
    }
}
