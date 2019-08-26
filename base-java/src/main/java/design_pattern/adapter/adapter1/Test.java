package design_pattern.adapter.adapter1;

public class Test {
    public static void main(String[] args) {
        MultPower multPower = new MultPower();
        multPower.power();

        IOTG otg = new OTG();
        MultPower2 multPower2 = new MultPower2(otg);
        multPower2.power();
    }
}
