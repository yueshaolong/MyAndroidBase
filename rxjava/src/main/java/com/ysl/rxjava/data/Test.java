package com.ysl.rxjava.data;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        MyList myList = new MyList();
        for (int i = 10; i < 15; i++) {
            myList.initData(i); // 循环添加5个元素
        }

        myList.printList(myList.head);   //打印链表
        myList.listLength(myList.head);
        myList.removeData(myList.head, 0);
        myList.addData(myList.head, 0, 99);
        myList.printList(myList.head);   //打印链表
        myList.listLength(myList.head);

        px();
    }

    public static void px() {
        int [] data = new int[]{2,5,-12,99,-68};
        int temp;
        for (int i = 0; i < data.length - 1; i++){//外层循环控制排序趟数
            for (int j = 0; j < data.length - 1 - i; j++){//内层循环控制每一趟排序多少次
                if (data[j] > data[j+1]) {
                    temp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(data));
    }

}
