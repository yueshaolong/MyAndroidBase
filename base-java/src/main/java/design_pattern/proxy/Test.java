package design_pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        NetUtil httpUtil = new HttpUtil();
        NetUtil okHttp = new OkHttpUtil();
        NetUtil netUtilProxy = new NetUtilProxy(httpUtil);
        NetUtil netUtilProxy1 = new NetUtilProxy(okHttp);
        System.out.println(netUtilProxy.getData());

        System.out.println("-----------------------------");

        NetUtil okHttpUtil = new OkHttpUtil();
        NetUtil proxyInstance = (NetUtil) Proxy.newProxyInstance(okHttpUtil.getClass().getClassLoader(), okHttpUtil.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("请求之前数据封装！！！");
                return method.invoke(okHttpUtil, args);
            }
        });
        System.out.println(proxyInstance.getData());
    }
}
