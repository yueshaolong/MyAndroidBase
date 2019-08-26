package design_pattern.chain.singlechain;

public class FaceFilter extends Filter {
    @Override
    String handle(String str) {
        //将字符串中出现的":):"转换成"^V^";
        str = str.replace(":):", "^V^")
                //后面添加的是便于我们观察代码执行步骤的字符串
                + "----FaceFilter()";
        System.out.println("str："+str);
        return str;
    }
}
