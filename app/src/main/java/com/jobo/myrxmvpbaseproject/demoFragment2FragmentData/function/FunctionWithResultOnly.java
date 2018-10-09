package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function;

/**
 * 无参有返回值接口抽象类
 * Created by JoBo on 2018/9/25.
 */

public abstract class FunctionWithResultOnly<Result> extends Function {
    public FunctionWithResultOnly(String functionName) {
        super(functionName);
    }
    public abstract Result function();
}
