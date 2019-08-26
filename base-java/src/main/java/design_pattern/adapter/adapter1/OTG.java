package design_pattern.adapter.adapter1;

public class OTG implements IOTG {
    @Override
    public void powerOTG() {
        System.out.println("使用OTG接口充电");
    }
}
