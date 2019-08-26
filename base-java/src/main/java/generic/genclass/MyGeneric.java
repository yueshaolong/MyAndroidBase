package generic.genclass;

public class MyGeneric<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        MyGeneric<String> myGeneric = new MyGeneric<>();
        myGeneric.setData("kj");
        System.out.println(myGeneric.getData());
    }
}
