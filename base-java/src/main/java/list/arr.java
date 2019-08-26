package list;

import java.util.Arrays;

public class arr {
    public static void main(String[] args) {
        String[] strings = new String[4];
        strings[0] = "a";
        strings[1] = "b";
        strings[2] = "c";
        System.out.println(Arrays.toString(strings));
        System.arraycopy(strings, 1, strings, 2, 2);
        strings[1] = "add";
        System.out.println(Arrays.toString(strings));
    }
}
