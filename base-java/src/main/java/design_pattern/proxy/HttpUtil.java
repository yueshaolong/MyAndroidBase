package design_pattern.proxy;

public class HttpUtil implements NetUtil{
    @Override
    public String getData() {
        return "我是HTTP请求回来的数据";
    }
}
