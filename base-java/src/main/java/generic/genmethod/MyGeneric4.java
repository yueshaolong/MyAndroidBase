package generic.genmethod;

public class MyGeneric4 <T>{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        MyGeneric4<Food> myGeneric4 = new MyGeneric4<>();
        myGeneric4.setData(new Food());
        myGeneric4.setData(new Fruit());
        myGeneric4.setData(new Apple());
        myGeneric4.setData(new Orange());

        MyGeneric4<Fruit> myGeneric = new MyGeneric4();
        myGeneric.setData(new Fruit());
        myGeneric.setData(new Apple());
        myGeneric.setData(new Orange());
//        myGeneric.setData(new Food());不行


    }
}
