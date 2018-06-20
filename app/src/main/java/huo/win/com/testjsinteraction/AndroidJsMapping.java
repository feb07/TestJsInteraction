package huo.win.com.testjsinteraction;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by lilichun on 2018-05-22.
 */

public class AndroidJsMapping extends Object {
    private Context mContext;

    public AndroidJsMapping(Context context) {
        this.mContext = context;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
