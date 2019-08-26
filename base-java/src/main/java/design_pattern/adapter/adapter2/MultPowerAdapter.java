package design_pattern.adapter.adapter2;

public class MultPowerAdapter extends Power {
    @Override
    public void powerOTG() {
        System.out.println("使用OTG接口充电");
    }

    @Override
    public void powerTypeC() {
        System.out.println("使用TypeC接口充电");
    }

    public void power(){
        powerOTG();
        powerTypeC();
    }
}
