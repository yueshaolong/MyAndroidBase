package design_pattern.chain.singlechain;

public class SensitiveFilter extends Filter {
    @Override
    String handle(String str) {
        //处理字符串中的敏感信息，将被就业和谐成就业
        str=str.replace("被就业", "就业").replace("敏感", "哈哈")+
                //后面添加的是便于我们观察代码执行步骤的字符串
                " ---sensitiveFilter()";
        System.out.println("str："+str);
        return str;
    }
}
