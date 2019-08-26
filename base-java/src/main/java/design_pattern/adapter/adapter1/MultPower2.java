package design_pattern.adapter.adapter1;

public class MultPower2 implements ITypeC {
    private IOTG otg;

    public MultPower2(IOTG otg) {
        this.otg = otg;
    }

    @Override
    public void powerTypeC() {
        System.out.println("使用TypeC接口充电");
    }

    public void power(){
        otg.powerOTG();
        powerTypeC();
    }
}
