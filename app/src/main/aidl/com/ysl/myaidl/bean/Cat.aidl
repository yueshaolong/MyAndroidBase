// Cat.aidl

//第一类AIDL文件的例子
//这个文件的作用是引入了一个序列化对象 Cat 供其他的AIDL文件使用
//注意：Cat.aidl与Cat.java的包名应当是一样的
package com.ysl.myaidl.bean;

// Declare any non-default types here with import statements
//注意parcelable是小写
parcelable Cat;
