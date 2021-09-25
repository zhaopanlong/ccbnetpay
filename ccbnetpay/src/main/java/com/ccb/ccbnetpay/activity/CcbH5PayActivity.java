/*     */ package com.ccb.ccbnetpay.activity;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.graphics.Bitmap;
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.text.TextUtils;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.ViewParent;
/*     */ import android.webkit.JavascriptInterface;
/*     */ import android.webkit.WebChromeClient;
/*     */ import android.webkit.WebSettings;
/*     */ import android.webkit.WebView;
/*     */ import android.webkit.WebViewClient;
/*     */ import android.widget.RelativeLayout;
/*     */ import androidx.annotation.Nullable;

import com.ccb.ccbnetpay.CCbPayContants;
/*     */ import com.ccb.ccbnetpay.dialog.CCBAlertDialog;
/*     */ import com.ccb.ccbnetpay.platform.Platform;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ import com.ccb.ccbnetpay.util.NetUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CcbH5PayActivity
/*     */   extends Activity
/*     */ {
/*  44 */   private String httpUrl = null;
/*  45 */   private String cxUrl = "";
/*     */   
/*     */   private WebView mWebView;
/*     */   
/*     */   private RelativeLayout rootRelative;
/*     */   private Platform.PayStyle payStyle;
/*  51 */   private int flag = 0;
/*     */ 
/*     */   
/*     */   protected void onCreate(@Nullable Bundle savedInstanceState) {
/*  55 */     super.onCreate(savedInstanceState);
/*  56 */     Bundle bundle = getIntent().getExtras();
/*  57 */     this.payStyle = (Platform.PayStyle)bundle.get("payStyle");
/*  58 */     this.httpUrl = bundle.getString("httpurl");
/*  59 */     this.cxUrl = bundle.getString("cxurl");
/*  60 */     initLayout();
/*  61 */     webViewSettings();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initLayout() {
/*  66 */     ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.rootRelative = new RelativeLayout((Context)this);
/*     */     
/*  72 */     this.mWebView = new WebView((Context)this);
/*  73 */     this.rootRelative.addView((View)this.mWebView, layoutParams);
/*     */     
/*  75 */     setContentView((View)this.rootRelative, layoutParams);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void webViewSettings() {
/*  81 */     WebSettings settings = this.mWebView.getSettings();
/*     */ 
/*     */ 
/*     */     
/*  85 */     String webviewAgent = settings.getUserAgentString();
/*  86 */     CcbSdkLogUtil.d("---webview端useragent---", webviewAgent);
/*  87 */     settings.setUserAgentString(webviewAgent + " " + "CCBSDK/2.2.0");
/*     */     
/*  89 */     settings.setUseWideViewPort(false);
/*  90 */     settings.setLoadWithOverviewMode(false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     settings.setJavaScriptEnabled(true);
/*  96 */     settings.setJavaScriptCanOpenWindowsAutomatically(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.mWebView.setWebViewClient(new MyWebViewClient());
/* 104 */     this.mWebView.setWebChromeClient(new WebChromeClient()
/*     */         {
/*     */           public void onProgressChanged(WebView view, int newProgress) {
/* 107 */             CcbSdkLogUtil.i("---onProgressChanged---", newProgress + "");
/*     */ 
/*     */             
/* 110 */             super.onProgressChanged(view, newProgress);
/*     */           }
/*     */         });
/*     */     
/* 114 */     this.mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "javaObj");
/*     */     
/* 116 */     if (this.payStyle == Platform.PayStyle.WECHAT_PAY) {
/* 117 */       loadURLWithHTTPHeaders();
/*     */     } else {
/* 119 */       this.mWebView.loadUrl(this.httpUrl);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onResume() {
/* 125 */     super.onResume();
/* 126 */     CcbSdkLogUtil.d("-----onResume-----");
/* 127 */     if (0 != this.flag && 
/* 128 */       this.payStyle == Platform.PayStyle.WECHAT_PAY) {
/* 129 */       CcbPayUtil.getInstance().showLoadingDialog((Context)this);
/* 130 */       CcbSdkLogUtil.d("-----微信订单查询-----" + this.flag);
/*     */       
/* 132 */       NetUtil.httpSendGet(this.cxUrl, new NetUtil.SendCallBack()
/*     */           {
/*     */             public void success(String result)
/*     */             {
/* 136 */               CcbPayUtil.getInstance().dismissLoading();
/* 137 */               CcbSdkLogUtil.d("---微信订单查询---", result);
/* 138 */               Map<String, String> map = new HashMap<>();
/*     */               try {
/* 140 */                 if (!TextUtils.isEmpty(result)) {
/*     */                   
/* 142 */                   int index = result.length() - 2;
/* 143 */                   char str = result.charAt(index);
/* 144 */                   if (',' == str)
/* 145 */                     result = result.substring(0, index) + result.substring(index + 1); 
/* 146 */                   JSONObject jsonObj = new JSONObject(result);
/* 147 */                   Iterator<String> iterator = jsonObj.keys();
/* 148 */                   String rskey = "", rsval = "";
/* 149 */                   while (iterator.hasNext()) {
/* 150 */                     rskey = iterator.next();
/* 151 */                     rsval = jsonObj.getString(rskey);
/* 152 */                     map.put(rskey, rsval);
/*     */                   } 
/*     */                 } 
/* 155 */                 CcbPayUtil.getInstance().sendSuccess(map);
/* 156 */                 CcbH5PayActivity.this.finish();
/* 157 */               } catch (Exception e) {
/* 158 */                 CcbSdkLogUtil.d("---解析微信订单查询结果异常---", e.getMessage());
/* 159 */                 CcbPayUtil.getInstance().onSendendResultMsg(1, "支付失败\n参考码:CXURL");
/*     */                 
/* 161 */                 CcbH5PayActivity.this.finish();
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public void failed(Exception e) {
/* 167 */               CcbPayUtil.getInstance().dismissLoading();
/* 168 */               CcbSdkLogUtil.d("---微信订单查询异常---", e.getMessage());
/* 169 */               CcbPayUtil.getInstance().onSendendResultMsg(1, "支付失败\n参考码:CXURL");
/*     */               
/* 171 */               CcbH5PayActivity.this.finish();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadURLWithHTTPHeaders() {
/* 180 */     String sysVer = CcbPayUtil.getInstance().getSysVersion();
/* 181 */     CcbSdkLogUtil.d("---sysversion----" + sysVer + "---referer----" + CCbPayContants.WECHATREFERER);
/*     */     
/* 183 */     if ("4.4.3".equals(sysVer) || "4.4.4".equals(sysVer)) {
/* 184 */       CcbSdkLogUtil.d("---Android系统4.4及以下版本----");
/*     */       
/* 186 */       this.mWebView.loadDataWithBaseURL(CCbPayContants.WECHATREFERER, "<script>window.location.href=\"" + this.httpUrl + "\";</script>", "text/html", "utf-8", null);
/*     */     }
/*     */     else {
/*     */       
/* 190 */       CcbSdkLogUtil.d("---Android系统其他版本----", CCbPayContants.WECHATREFERER);
/* 191 */       Map<String, String> extraHeaders = new HashMap<>();
/* 192 */       extraHeaders.put("Referer", CCbPayContants.WECHATREFERER);
/* 193 */       this.mWebView.loadUrl(this.httpUrl, extraHeaders);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class MyWebViewClient
/*     */     extends WebViewClient {
/*     */     private MyWebViewClient() {}
/*     */     
/*     */     public void onPageStarted(WebView view, String url, Bitmap favicon) {
/* 202 */       CcbSdkLogUtil.d("---pageStart---", url);
/* 203 */       CcbPayUtil.getInstance().showLoadingDialog((Context)CcbH5PayActivity.this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onPageFinished(WebView view, String url) {
/* 209 */       CcbSdkLogUtil.d("---pageFinished---", url);
/* 210 */       CcbPayUtil.getInstance().dismissLoading();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
/* 217 */       CcbSdkLogUtil.d("---页面加载有误---", failingUrl);
/* 218 */       CcbPayUtil.getInstance().dismissLoading();
/* 219 */       CCBAlertDialog dialog = new CCBAlertDialog((Context)CcbH5PayActivity.this, description);
/*     */       
/* 221 */       dialog.showDialog();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean shouldOverrideUrlLoading(WebView view, String url) {
/* 232 */       CcbSdkLogUtil.d("---shouldOverrideUrlLoading---", url);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       if (url.startsWith("http://") || url.startsWith("https://")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 258 */         CcbSdkLogUtil.d("---处理http开头url路径---", url);
/* 259 */         return false;
/*     */       } 
/* 261 */       CcbSdkLogUtil.d("---处理非http等开头url路径---", url);
/*     */       try {
/* 263 */         Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
/* 264 */         CcbH5PayActivity.this.startActivity(intent);
/* 265 */       } catch (Exception e) {
/* 266 */         CCBAlertDialog dialog = new CCBAlertDialog((Context)CcbH5PayActivity.this, "未检测到相关客户端，请安装后重试。");
/*     */         
/* 268 */         dialog.showDialog();
/*     */       } 
/* 270 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class MyJavaScriptInterface
/*     */   {
/*     */     @JavascriptInterface
/*     */     public void sdkCallBack(String val) {
/* 294 */       CcbSdkLogUtil.d("H5 sdkCallBack", "---H5 sdkCallBack---" + val);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void showFinish() {
/* 301 */       CcbSdkLogUtil.d("H5支付", "显示完成按钮");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void captureScreen() {
/* 308 */       CcbSdkLogUtil.d("---H5截屏---");
/*     */       
/* 310 */       NetUtil.screenshot(CcbH5PayActivity.this);
/* 311 */       CCBAlertDialog dialog = new CCBAlertDialog((Context)CcbH5PayActivity.this, "建行支付二维码已保存到本机相册，\n请使用支付宝扫一扫扫码支付!");
/*     */       
/* 313 */       dialog.showDialog();
/*     */     }
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void payBack() {
/* 319 */       CcbSdkLogUtil.d("---H5支付返回---");
/* 320 */       CcbPayUtil.getInstance().onSendendResultMsg(2, "取消支付");
/* 321 */       CcbH5PayActivity.this.finish();
/*     */     }
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void payDone(String str) {
/* 327 */       CcbSdkLogUtil.d("---H5完成---" + str);
/* 328 */       Map<String, String> map = CcbPayUtil.getInstance().splitResult(str);
/* 329 */       CcbPayUtil.getInstance().sendSuccess(map);
/* 330 */       CcbH5PayActivity.this.finish();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 345 */     if (keyCode == 4)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 352 */       return true;
/*     */     }
/* 354 */     return super.onKeyDown(keyCode, event);
/*     */   }
/*     */   
/*     */   public static Intent creatIntent(Context c, String url, String cxurl, Platform.PayStyle style) {
/* 358 */     Intent intent = new Intent();
/* 359 */     intent.setClass(c, CcbH5PayActivity.class);
/* 360 */     Bundle bundle = new Bundle();
/* 361 */     bundle.putSerializable("payStyle", (Serializable)style);
/* 362 */     bundle.putString("httpurl", url);
/* 363 */     bundle.putString("cxurl", cxurl);
/* 364 */     intent.putExtras(bundle);
/* 365 */     return intent;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDestroy() {
/* 370 */     super.onDestroy();
/* 371 */     if (this.mWebView != null) {
/* 372 */       ViewParent parent = this.mWebView.getParent();
/* 373 */       if (null != parent)
/* 374 */         ((ViewGroup)parent).removeView((View)this.mWebView); 
/* 375 */       this.mWebView.stopLoading();
/* 376 */       this.mWebView.getSettings().setJavaScriptEnabled(false);
/* 377 */       this.mWebView.clearHistory();
/* 378 */       this.mWebView.removeAllViews();
/*     */       try {
/* 380 */         this.mWebView.destroy();
/* 381 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\activity\CcbH5PayActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */