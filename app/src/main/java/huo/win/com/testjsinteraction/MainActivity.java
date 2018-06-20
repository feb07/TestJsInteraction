package huo.win.com.testjsinteraction;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends Activity implements View.OnClickListener {

    private WebView webview;
    private Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webview);
        btn_click = (Button) findViewById(R.id.btn_click);
        btn_click.setOnClickListener(this);
        WebSettings webSettings = webview.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
//        webview.addJavascriptInterface(new AndroidJsMapping(this), "test");//AndroidtoJS类对象映射到js的test对象
        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
//        webview.loadUrl("file:///android_asset/androidJsMapping.html");
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        webview.loadUrl("file:///android_asset/androidJsMapping2.html");
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if (uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        // 可以在协议上带有参数并传递到Android上
                        String param = uri.getQueryParameter("arg1");
                        Toast.makeText(MainActivity.this, param, Toast.LENGTH_LONG).show();
                        result.cancel();
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
//        webview.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 步骤2：根据协议的参数，判断是否是所需要的url
//                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
//                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
//                Uri uri = Uri.parse(url);
//                // 如果url的协议 = 预先约定的 js 协议
//                // 就解析往下解析参数
//                if (uri.getScheme().equals("js")) {
//                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
//                    // 所以拦截url,下面JS开始调用Android需要的方法
//                    if (uri.getAuthority().equals("webview")) {
//                        //  步骤3：
//                        // 执行JS所需要调用的逻辑
//                        // 可以在协议上带有参数并传递到Android上
//                        String param = uri.getQueryParameter("arg1");
//                        Toast.makeText(MainActivity.this, param, Toast.LENGTH_LONG).show();
//                    }
//                    return true;
//                }
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//        });
//        webview.setWebViewClient(new WebViewClient() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                // 步骤2：根据协议的参数，判断是否是所需要的url
//                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
//                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
//                Uri uri = request.getUrl();
//                // 如果url的协议 = 预先约定的 js 协议
//                // 就解析往下解析参数
//                if (uri.getScheme().equals("js")) {
//                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
//                    // 所以拦截url,下面JS开始调用Android需要的方法
//                    if (uri.getAuthority().equals("webview")) {
//                        //  步骤3：
//                        // 执行JS所需要调用的逻辑
//                        // 可以在协议上带有参数并传递到Android上
//                        String param = uri.getQueryParameter("arg1");
//                        Toast.makeText(MainActivity.this, param, Toast.LENGTH_LONG).show();
//                    }
//                    return true;
//                }
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//        });
//        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
//        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
//        // 通过设置WebChromeClient对象处理JavaScript的对话框
//        //设置响应js 的Alert()函数
//        webview.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//                alert.setTitle("Alert");
//                alert.setMessage(message);
//                alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                alert.setCancelable(false);
//                alert.create().show();
//                return true;
//            }
//
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (v == btn_click) {
//            webview.post(new Runnable() {
//                @Override
//                public void run() {
//                    // 注意调用的JS方法名要对应上
//                    // 调用javascript的callJS()方法
//                    webview.loadUrl("javascript:callJS()");
//                }
//            });
            // 只需要将第一种方法的loadUrl()换成下面该方法即可
            webview.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        }
    }
}
