package com.ysl.hookskin;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookClickHelper {

    public static void hook(final Context context, View v){
        /*try {
            // 反射执行View类的getListenerInfo()方法，拿到v的mListenerInfo对象，这个对象就是点击事件的持有者
            Method method = View.class.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);//由于getListenerInfo()方法并不是public的，所以要加这个代码来保证访问权限
            Object mListenerInfo = method.invoke(v);//这里拿到的就是mListenerInfo对象，也就是点击事件的持有者

            //要从这里面拿到当前的点击事件对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");// 这是内部类的表示方法
            Field field = listenerInfoClz.getDeclaredField("mOnClickListener");
            final OnClickListener onClickListenerInstance = (OnClickListener) field.get(mListenerInfo);//取得真实的mOnClickListener对象

            //2. 创建我们自己的点击事件代理类
            //   方式1：自己创建代理类
//               ProxyOnClickListener proxyOnClickListener = new ProxyOnClickListener(onClickListenerInstance);
            //   方式2：由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
            // Proxy.newProxyInstance的3个参数依次分别是：
            // 本地的类加载器;
            // 代理类的对象所继承的接口（用Class数组表示，支持多个接口）
            // 代理类的实际逻辑，封装在new出来的InvocationHandler内
            Object proxyOnClickListener = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("HookSetOnClickListener", "点击事件被hook到了");//加入自己的逻辑
                    Toast.makeText(context,"哈哈哈，事件被我吃了！",Toast.LENGTH_SHORT).show();
                    return "";//执行被代理的对象的逻辑
                }
            });
            //3. 用我们自己的点击事件代理类，设置到"持有者"中
            field.set(mListenerInfo, proxyOnClickListener);
            //完成
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }*/

        try {
            Class<?> view = View.class;
            Method getListenerInfo = view.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            //通过方法名调用invoke方法，并传入调用者对象，以及方法所需参数class；就可得到此方法获取的对象
            Object mListenerInfo = getListenerInfo.invoke(v);
            //内部类的调用方式
            Class<?> listenerInfo = Class.forName("android.view.View$ListenerInfo");
            //获取属性
            Field onClickListener = listenerInfo.getDeclaredField("mOnClickListener");
            //传入属性所属的对象，就可得到属性真实的值；
            OnClickListener realOnClickListener = (OnClickListener) onClickListener.get(mListenerInfo);
            //传入属性所属的对象，以及要给此属性设置的值，就可改变此属性原有的值
//            onClickListener.set(mListenerInfo, null);
            onClickListener.set(mListenerInfo, new MyOnClickListener(realOnClickListener, context));

            Log.d("SetOnClickListener", "点击事件被hook到了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 还真是这样,自定义代理类
    public static class MyOnClickListener implements View.OnClickListener {
        private final Context context;
        View.OnClickListener onClickListener;

        public MyOnClickListener(View.OnClickListener onClickListener, Context context) {
            this.onClickListener = onClickListener;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Log.d("SetOnClickListener", "点击事件被hook到了");
            Toast.makeText(context,"哈哈哈，事件被我吃了！",Toast.LENGTH_SHORT).show();
//            if (onClickListener != null) {
//                onClickListener.onClick(v);
//            }
        }
    }

}
