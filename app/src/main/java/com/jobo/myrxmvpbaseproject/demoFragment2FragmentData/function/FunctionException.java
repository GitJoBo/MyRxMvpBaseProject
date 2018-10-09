package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function;

/**
 * fragment传值万能接口自定义异常
 * Created by JoBo on 2018/9/25.
 */

public class FunctionException extends Exception {
    public FunctionException(String exceptionStr){
        super(exceptionStr);
    }
}
