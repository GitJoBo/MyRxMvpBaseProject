package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function;

/**
 * 有参有返回值接口抽象类
 * Created by JoBo on 2018/9/25.
 */

public abstract class FunctionWithParamAndResult<Result,Param> extends Function {
    public FunctionWithParamAndResult(String functionName) {
        super(functionName);
    }
    public abstract Result function(Param param);
}
