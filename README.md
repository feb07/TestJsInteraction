# android 原生webview与js交互
具体也可见个人简书（图文说明更加详细）：https://www.jianshu.com/p/f7a1cbafae57


### 前言

Android与JS通过WebView互相调用方法，实际上是：

1、Android去调用JS的代码

2、JS去调用Android的代码

二者沟通的桥梁是WebView



### 对于Android调用JS代码的方法有2种： 

1. 通过WebView的loadUrl（） 

2. 通过WebView的evaluateJavascript（）

### 对于JS调用Android代码的方法有3种： 

1. 通过WebView的addJavascriptInterface（）进行对象映射 

2. 通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url 

3. 通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息



### 具体分析

### Android调用JS代码

#### 1、Android通过WebView调用 JS 代码
方式1：通过WebView的loadUrl（）
将需要调用的JS代码以.html格式放到src/main/assets文件夹里

在Android里通过WebView设置调用JS代码 ，特别注意：JS代码调用一定要在 onPageFinished（） 回调之后才能调用，否则不会调用。


#### 2、通过WebView的evaluateJavascript（），因为该方法的执行不会使页面刷新，而第一种方法（loadUrl ）的执行则会。
Android 4.4 后才可使用



### JS通过WebView调用 Android 代码
#### 1、通过 WebView的addJavascriptInterface（）进行对象映射
定义一个与JS对象映射关系的Android类：AndroidJsMapping

将需要调用的JS代码以.html格式放到src/main/assets文件夹里，注意图中test变量，需要在android代码中定义

在Android里通过WebView设置Android类与JS代码的映射

#### 2、通过 WebViewClient 的方法shouldOverrideUrlLoading ()回调拦截 url，Android通过 WebViewClient 的回调方法shouldOverrideUrlLoading ()拦截 url，解析该 url 的协议，如果检测到是预先约定好的协议，就调用相应方法 
在JS约定所需要的Url协议 

在Android通过WebViewClient复写shouldOverrideUrlLoading （）


#### 3、通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息，Android通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调分别拦截JS对话框，得到他们的消息内容，然后解析即可。常用的拦截是：拦截 JS的输入框（即prompt（）方法）
加载JS代码

在Android通过WebChromeClient复写onJsPrompt（）






