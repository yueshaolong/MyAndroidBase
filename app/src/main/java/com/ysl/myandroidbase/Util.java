package com.ysl.myandroidbase;

public class Util {
    public static String printTrack(){
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        if(st==null){
            return "无堆栈...";
        }
        StringBuffer sbf =new StringBuffer();
        for(StackTraceElement e:st){
            if(sbf.length()>0){
                sbf.append(" <- ");
                sbf.append(System.getProperty("line.separator"));
            }
            sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}"
                    ,e.getClassName()
                    ,e.getMethodName()
                    ,e.getLineNumber()));
        }
        return sbf.toString();
    }
}
