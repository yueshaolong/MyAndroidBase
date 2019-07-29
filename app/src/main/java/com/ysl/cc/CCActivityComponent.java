package com.ysl.cc;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;

public class CCActivityComponent implements IComponent {
    @Override
    public String getName() {
        return "CCActivity";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "back"://响应actionName为"back"的组件调用
                //跳转到页面：CCActivity
                CCUtil.navigateTo(cc, CCActivity.class);
                //返回处理结果给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                //同步方式实现（在return之前听过CC.sendCCResult()返回组件调用结果），return false
                break;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
        return true;
    }
}
