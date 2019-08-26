package com.ysl.rxjava.data;


public class MyList {
    private static final String TAG = "MyList";
    public Node head; // 定义一个头节点
    public Node current; // 记录当前节点

    // 往链表中添加数据
    public void initData(int data) {
        if (head == null) {
            head = new Node(data); // 如果头节点为空，就新建一个头结点
            current = head; // 记录当前节点为头结点
        } else {
            current.next = new Node(data); // 新建一个节点，当前节点的指针域指向它
            current = current.next; // 当前节点位置后移
        }
    }
    // 打印链表
    public void printList(Node head) {
        if (head != null) { // 确定链表不为空
            current = head;
            while (current!= null) { // 最后一个节点的为空
                System.out.print(current.data + "-->");
                current = current.next; // 当前节点往后移动一个位置
            }
            System.out.println("\n");
        }
    }

    // 向链表中添加数据
    /**
     * 根据位置添加节点
     * @param head  头指针
     * @param index 要删除的位置
     * @param value 要添加的值
     */
    public void addData(Node head, int index, int value){
        if (head == null || index < 0 || index > listLength(head)){
            System.out.println("removeData: 参数不合法");
        }

        Node newNode = new Node(value);
        current = head;

        if (index == 0){
            newNode.next = current;
            this.head = newNode;
            return;
        }

        int currentPosition = 0;
        while (current != null){
            if (index - 1 == currentPosition){//查找上一个结点
                newNode.next = current.next;
                current.next = newNode;
                return;
            }
            currentPosition++;
            current = current.next;
        }
    }

    // 从链表中删除数据
    /**
     * 根据位置删除节点
     * @param head  头指针
     * @param index 要删除的位置
     */
    public void removeData(Node head, int index){
        if (head == null || index < 0 || index > listLength(head) - 1){
            System.out.println("removeData: 参数不合法");
        }

        current = head;
        if (index == 0){
            this.head = current.next;
            return;
        }

        int currentPosition = 0;
        while (current != null){
            if (index - 1 == currentPosition){//查找上一个结点
                current.next = current.next.next;
                return;
            }
            currentPosition++;
            current = current.next;
        }
    }

    public int listLength(Node head){
        int length = 0;
        while (head != null){
            length++;
            head = head.next;
        }
        System.out.println("链表的长度："+length);
        return length;
    }



    // 自定义节点类，包含数据域和指针域
    class Node {
        int data; // 数据域
        Node next; // 指针域

        public Node(int data) {
            this.data = data;
        }
    }
}
