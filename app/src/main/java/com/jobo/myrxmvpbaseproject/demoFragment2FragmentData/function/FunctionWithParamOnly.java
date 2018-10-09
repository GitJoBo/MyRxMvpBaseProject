package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function;

/**
 * 有参无返回值接口抽象类
 * Created by JoBo on 2018/9/25.
 */

public abstract class FunctionWithParamOnly<Param> extends Function {
    public FunctionWithParamOnly(String functionName) {
        super(functionName);
    }
    public abstract void function(Param param);
}
