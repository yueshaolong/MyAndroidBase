package design_pattern.proxy.proxy;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class BbFactory implements WomanToolsFactory {
    @Override
    public void saleWomanTools(float length) {
        System.out.println("按需求定制了一个高度为"+length+"的男model");
    }
}
