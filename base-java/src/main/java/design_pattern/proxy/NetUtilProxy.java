package design_pattern.proxy;

public class NetUtilProxy implements NetUtil{
    private NetUtil netUtil;

    public NetUtilProxy(NetUtil netUtil) {
        this.netUtil = netUtil;
    }


    @Override
    public String getData() {
        System.out.println("请求之前数据封装！");
        return netUtil.getData();
    }
}
