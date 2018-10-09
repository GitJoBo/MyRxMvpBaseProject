package com.jobo.httplib.http.exception;

import android.util.Log;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.NetWorkUtil;
import org.json.JSONException;
import retrofit2.HttpException;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;


/**
 * 错误/异常处理工具
 */
public class ExceptionEngine {

    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时

    public final static int ERROR=0;//错误
    public final static int NET_ERROR=1;//无网络
    public final static int EMPTY=2;//为空

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if(NetWorkUtil.isNet()){
            if (e instanceof HttpException) {             //HTTP错误
                HttpException httpExc = (HttpException) e;
                ex = new ApiException(e, httpExc.code());
                ex.setMsg("网络错误");  //均视为网络错误
                return ex;
            } else if (e instanceof ServerException) {    //服务器返回的错误
                ServerException serverExc = (ServerException) e;
                ex = new ApiException(serverExc, serverExc.getCode());
                ex.setMsg(serverExc.getMsg());
                return ex;
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
                ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
                ex.setMsg("解析错误");
                return ex;
            } else if (e instanceof ConnectException || e instanceof SSLHandshakeException || e instanceof UnknownHostException) {//连接网络错误
                ex = new ApiException(e, CONNECT_ERROR);
                ex.setMsg("服务器未响应");
                return ex;
            } else if (e instanceof SocketTimeoutException) {//网络超时
                ex = new ApiException(e, TIME_OUT_ERROR);
                ex.setMsg("网络超时");
                return ex;
            } else {  //未知错误
                ex = new ApiException(e, UN_KNOWN_ERROR);
                ex.setMsg("未知错误");
                return ex;
            }
        }else {
            ex = new ApiException(NET_ERROR,"无网络");
            return ex;
        }
    }

}
