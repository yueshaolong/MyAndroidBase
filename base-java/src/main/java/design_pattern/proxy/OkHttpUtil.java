package design_pattern.proxy;

public class OkHttpUtil implements NetUtil {
    @Override
    public String getData() {
        return "我是OKHttp请求回来的数据";
    }
}
