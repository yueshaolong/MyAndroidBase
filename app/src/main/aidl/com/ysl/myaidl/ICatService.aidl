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
    /*AIDL中的定向 tag 表示了在跨进程通信中数据的流向，
    其中 in 表示数据只能由客户端流向服务端， out 表示数据只能由服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。
    其中，数据流向是针对在客户端中的那个传入方法的对象而言的。比如下面的Cat
    in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，但是客户端的那个对象不会因为服务端对传参的修改而发生变动；
    out 的话表现为服务端将会接收到那个对象的参数为空的对象，但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；
    inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，并且客户端将会同步服务端对该对象的任何变动。*/
    void setCatPrice(in Cat cat , int price);
    void setCatName(in Cat cat , String name);
    Cat addCatIn(in Cat cat);
    Cat addCatOut(out Cat cat);
    Cat addCatInout(inout Cat cat);
}
