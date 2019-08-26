package design_pattern.builder;

public class Test {
    public static void main(String[] args) {
        House house = new Builder().build(new BuildHouse());
        System.out.println(house);
    }
}
