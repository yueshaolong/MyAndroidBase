// ICatService.aidl

//第二类AIDL文件的例子
package com.ysl.myaidl;

// Declare any non-default types here with import statements
//导入所需要使用的非默认支持数据类型的包
import com.ysl.myaidl.bean.Cat;

interface ICatService {

    //所有的返回值前都不需要加任何东西，不管是什么数据类型
    Cat getCat();
    int getCatCount();

    //传参时除了Java基本类型以及String，CharSequence之外的类型
    //都需要在前面加上定向tag，具体加什么量需而定
    void setCatPrice(in Cat cat , int price);
    void setCatName(in Cat cat , String name);
    void addCatIn(in Cat cat);
    void addCatOut(out Cat cat);
    void addCatInout(inout Cat cat);
}
