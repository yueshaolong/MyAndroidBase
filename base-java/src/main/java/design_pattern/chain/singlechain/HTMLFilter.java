package design_pattern.chain.singlechain;

public class HTMLFilter extends Filter {
    @Override
    String handle(String str) {
        //将字符串中出现的"<>"符号替换成"[]"
        System.out.println("初始str："+str);
        str = str.replace('<', '[').replace('>', ']')+
                //后面添加的是便于我们观察代码执行步骤的字符串
                "----HTMLFilter()";
        System.out.println("str："+str);
        return str;
    }
}
