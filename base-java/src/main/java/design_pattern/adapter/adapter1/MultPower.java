package design_pattern.adapter.adapter1;

public class MultPower extends OTG implements ITypeC {

    @Override
    public void powerTypeC() {
        System.out.println("使用TypeC接口充电");
    }

    public void power(){
        powerOTG();
        powerTypeC();
    }
}
