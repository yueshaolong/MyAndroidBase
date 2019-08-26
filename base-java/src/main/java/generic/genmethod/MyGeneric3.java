package generic.genmethod;

public class MyGeneric3 {
    public <E> void getData(E e){
        System.out.println(e);
    }

    public static void main(String[] args) {
        MyGeneric3 myGeneric = new MyGeneric3();
        myGeneric.getData("kj");
        myGeneric.getData(100);
        myGeneric.getData(1.32);
    }
}
