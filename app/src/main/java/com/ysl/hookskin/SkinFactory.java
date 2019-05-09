package com.ysl.hookskin;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysl.myandroidbase.R;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinFactory implements Factory2 {
    private List<SkinView> cacheSkinView = new ArrayList<>();

    private AppCompatDelegate mDelegate;
    public void setDelegate(AppCompatDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }
    static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    final Object[] mConstructorArgs = new Object[2];

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();
    static final String[] prefixs = new String[]{
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = mDelegate.createView(parent, name, context, attrs);
        System.out.println("view："+view);
        if (view == null) {
            mConstructorArgs[0] = context;
            try {//替代系统来创建view
                if (-1 == name.indexOf('.')) {//TextView
                    // 如果View的name中不包含 '.' 则说明是系统控件，会在接下来的调用链在name前面加上 'android.view.'
                    view = createView(context, name, prefixs, attrs);
                } else {
                    // 如果name中包含 '.' 则直接调用createView方法，onCreateView 后续也是调用了createView
                    view = createView(context, name, null, attrs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //收集要换肤的控件
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinView);
        boolean aBoolean = typedArray.getBoolean(R.styleable.SkinView_isSupport, false);
        if (aBoolean){//此控件要换肤
            int attributeCount = attrs.getAttributeCount();
            Map<String, String> attrsMap = new HashMap<>();
            for (int i = 0; i < attributeCount; i++){
                String attributeName = attrs.getAttributeName(i);
                String attributeValue = attrs.getAttributeValue(i);
                System.out.println("attributeName: " + attributeName + " attributeValue: " + attributeValue);
                attrsMap.put(attributeName, attributeValue);
            }
            SkinView skinView = new SkinView();
            skinView.view = view;
            skinView.context = context;
            skinView.attrsMap = attrsMap;
            cacheSkinView.add(skinView);
        }
        return view;
    }
    public final View createView(Context context, String name, String[] prefixs, AttributeSet attrs) {

        Constructor<? extends View> constructor = sConstructorMap.get(name);
        Class<? extends View> clazz = null;

        if (constructor == null) {
            try {
                if (prefixs != null && prefixs.length > 0) {
                    for (String prefix : prefixs) {
                        clazz = context.getClassLoader().loadClass(
                                prefix != null ? (prefix + name) : name).asSubclass(View.class);
                        if (clazz != null) break;
                    }
                } else {
                    if (clazz == null) {
                        clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                    }
                }
                if (clazz == null) {
                    return null;
                }
                constructor = clazz.getConstructor(mConstructorSignature);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            constructor.setAccessible(true);
            sConstructorMap.put(name, constructor);
        }
        Object[] args = mConstructorArgs;
        args[1] = attrs;
        try {
            //通过反射创建View对象
            final View view = constructor.newInstance(args);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class SkinView{
        View view;
        Context context;
        Map<String, String> attrsMap;

        public void changeSkin() {
            System.out.println("a:: "+attrsMap);
            if (view instanceof LinearLayout) {
                if (!TextUtils.isEmpty(attrsMap.get("background"))){
                    int bgId = Integer.parseInt(attrsMap.get("background").substring(1));
                    System.out.println("设置布局id"+bgId);
                    String attrType = context.getResources().getResourceTypeName(bgId);
                    if (TextUtils.equals(attrType, "drawable")) {
                        System.out.println("设置布局背景图片"+view);
                        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.girl));
                    } else if (TextUtils.equals(attrType, "color")) {
                        System.out.println("设置布局背景颜色"+view);
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    }
                }
            }

            if (view instanceof TextView){
                if (!TextUtils.isEmpty(attrsMap.get("textColor"))){
                    System.out.println("设置字体颜色");
                    ((TextView)view).setTextColor(context.getResources().getColor(R.color.GREEN));
                }
                if (!TextUtils.isEmpty(attrsMap.get("textSize"))){
                    System.out.println("设置字体大小");
                    ((TextView)view).setTextSize(60);
                }
            }

            if (view instanceof Button){
                if (!TextUtils.isEmpty(attrsMap.get("background"))){
                    System.out.println("设置button背景");
                    ((Button)view).setBackgroundColor(context.getResources().getColor(R.color.GREEN));
                }
            }
        }
    }
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    public void changeSkin() {
        for (SkinView skinView : cacheSkinView) {
            skinView.changeSkin();
        }
    }
}
