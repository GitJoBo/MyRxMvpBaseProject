package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function;

import android.text.TextUtils;

import javax.xml.transform.Result;
import java.util.HashMap;

/**
 * Created by JoBo on 2018/9/25.
 */

public class FunctionManager {
    private static FunctionManager instance;
    private HashMap<String, FunctionNoParamNoResault> mStringFunctionNoParamNoResaultHashMap;
    private HashMap<String, FunctionWithParamAndResult> mStringFunctionWithParamAndResultHashMap;
    private HashMap<String, FunctionWithParamOnly> mStringFunctionWithParamOnlyHashMap;
    private HashMap<String, FunctionWithResultOnly> mStringFunctionWithResultOnlyHashMap;

    private FunctionManager() {
        mStringFunctionNoParamNoResaultHashMap = new HashMap<>();
        mStringFunctionWithParamAndResultHashMap = new HashMap<>();
        mStringFunctionWithParamOnlyHashMap = new HashMap<>();
        mStringFunctionWithResultOnlyHashMap = new HashMap<>();
    }

    public static FunctionManager getInstance() {
        if (instance == null) {
            instance = new FunctionManager();
        }
        return instance;
    }

    /**
     * 添加无参无返回值的接口
     *
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionNoParamNoResault function) {
        mStringFunctionNoParamNoResaultHashMap.put(function.mFunctionName, function);
        return this;
    }

    /**
     * 添加有参有返回值的接口
     *
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionWithParamAndResult function) {
        mStringFunctionWithParamAndResultHashMap.put(function.mFunctionName, function);
        return this;
    }

    /**
     * 添加有参无返回值的接口
     *
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionWithParamOnly function) {
        mStringFunctionWithParamOnlyHashMap.put(function.mFunctionName, function);
        return this;
    }

    /**
     * 添加无参有返回值的接口
     *
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionWithResultOnly function) {
        mStringFunctionWithResultOnlyHashMap.put(function.mFunctionName, function);
        return this;
    }

    /**
     * 调用无参无返回值的接口方法
     *
     * @param functionName
     */
    public void invokeFunction(String functionName) {
        if (TextUtils.isEmpty(functionName)) {
            return;
        }
        if (mStringFunctionNoParamNoResaultHashMap != null) {
            FunctionNoParamNoResault function = mStringFunctionNoParamNoResaultHashMap.get(functionName);
            if (function != null) {
                function.function();
            }
            if (function == null) {
                try {
                    throw new FunctionException("Has no this function:" + functionName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 调用有参有返回值的接口方法
     *
     * @param functionName 方法名
     * @param clz          返回值class
     * @param data         参数
     * @param <Result>
     * @param <Param>
     * @return
     */
    public <Result, Param> Result invokeFunction(String functionName, Class<Result> clz, Param data) {
        if (TextUtils.isEmpty(functionName) == true) {
            return null;
        }
        if (mStringFunctionWithParamAndResultHashMap != null) {
            FunctionWithParamAndResult f = mStringFunctionWithParamAndResultHashMap.get(functionName);
            if (f != null) {
                if (clz != null) {
                    return clz.cast(f.function(data));
                } else {
                    return (Result) f.function(data);
                }
            } else {
                try {
                    throw new FunctionException("Has no this function" + functionName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 调用有参无返回值的接口方法
     *
     * @param functionName 方法名
     * @param param        参数
     * @param <Param>
     */
    public <Param> void invokeFunction(String functionName, Param param) {
        if (TextUtils.isEmpty(functionName)) {
            return;
        }
        if (mStringFunctionWithParamOnlyHashMap != null) {
            FunctionWithParamOnly function = mStringFunctionWithParamOnlyHashMap.get(functionName);
            if (function != null) {
                function.function(functionName);
            } else {
                try {
                    throw new FunctionException("Has no this function" + functionName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 调用无参有返回值的接口方法
     * @param functionName
     * @param clz
     * @param <Result>
     * @return
     */
    public <Result> Result invokeFunction(String functionName, Class<Result> clz) {
        if (TextUtils.isEmpty(functionName)) {
            return null;
        }
        if (mStringFunctionWithResultOnlyHashMap != null){
            FunctionWithResultOnly function = mStringFunctionWithResultOnlyHashMap.get(functionName);
            if (function!=null){
               if (clz!=null){
                   return clz.cast(function.function());
               }else {
                   return (Result)function.function();
               }
            }else {
                try{
                    throw new FunctionException("Has no this function "+functionName);
                }catch (FunctionException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
