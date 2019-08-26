package generic.genclass;

import java.util.ArrayList;
import java.util.List;

//MyGeneric3<? extends List>不允许这种写法
public class MyGeneric2<T extends List> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        MyGeneric2<ArrayList<String>> myGeneric = new MyGeneric2<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("kj");
        myGeneric.setData(arrayList);
        System.out.println(myGeneric.getData());
    }
}
