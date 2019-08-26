package design_pattern.proxy.proxy.normal;


import design_pattern.proxy.proxy.ManToolsFactory;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class Mark implements ManToolsFactory {

    /*包含真实的对象*/
    public ManToolsFactory factory;

    public Mark(ManToolsFactory factory) {
        this.factory = factory;
    }

    /*前置处理器*/
    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }

    /*后置处理器*/
    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }


    @Override
    public void saleManTools(String size) {
        doSthAfter();
        factory.saleManTools(size);
        doSthBefore();
    }

}
