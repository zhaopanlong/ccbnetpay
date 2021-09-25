/*     */ package com.ccb.ccbnetpay.activity;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Color;
/*     */ import android.net.Uri;
/*     */ import android.os.Build;
/*     */ import android.os.Bundle;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.ViewParent;
/*     */ import android.webkit.JavascriptInterface;
/*     */ import android.webkit.WebChromeClient;
/*     */ import android.webkit.WebSettings;
/*     */ import android.webkit.WebView;
/*     */ import android.webkit.WebViewClient;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.RelativeLayout;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.Nullable;

import com.ccb.ccbnetpay.dialog.CCBAlertDialog;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ import java.util.Map;
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
/*     */ public class CcbUnionPayActivity
/*     */   extends Activity
/*     */ {
/*  49 */   private String httpUrl = null;
/*  50 */   private TextView backApp = null;
/*     */   
/*     */   private Context mContext;
/*     */   
/*     */   private WebView mWebView;
/*     */   
/*  56 */   private DisplayMetrics dm = new DisplayMetrics();
/*     */ 
/*     */   
/*     */   protected void onCreate(@Nullable Bundle savedInstanceState) {
/*  60 */     super.onCreate(savedInstanceState);
/*  61 */     initLayout();
/*  62 */     webViewSettings();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initLayout() {
/*  68 */     requestWindowFeature(1);
/*  69 */     getWindow().setSoftInputMode(16);
/*  70 */     this.mContext = (Context)this;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (Build.VERSION.SDK_INT >= 19)
/*     */     {
/*  82 */       if (Build.VERSION.SDK_INT >= 21) {
/*  83 */         getWindow().clearFlags(67108864);
/*     */         
/*  85 */         getWindow().getDecorView().setSystemUiVisibility(1280);
/*     */ 
/*     */         
/*  88 */         getWindow().addFlags(-2147483648);
/*     */         
/*  90 */         getWindow().setStatusBarColor(Color.parseColor("#0066cc"));
/*     */       } 
/*     */     }
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
/* 110 */     Bundle bundle = getIntent().getExtras();
/* 111 */     this.httpUrl = bundle.getString("httpurl");
/* 112 */     getWindowManager().getDefaultDisplay().getMetrics(this.dm);
/*     */ 
/*     */     
/* 115 */     LinearLayout rootLayout = new LinearLayout(this.mContext);
/* 116 */     LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(-1, -1);
/*     */ 
/*     */     
/* 119 */     rootLayout.setOrientation(1);
/*     */     
/* 121 */     rootLayout.setFitsSystemWindows(true);
/*     */ 
/*     */     
/* 124 */     RelativeLayout reLayout = new RelativeLayout(this.mContext);
/*     */ 
/*     */     
/* 127 */     RelativeLayout.LayoutParams reLayParams = new RelativeLayout.LayoutParams(-1, CcbPayUtil.getInstance().getDimensionToDip(44, this.dm));
/* 128 */     reLayout.setLayoutParams((ViewGroup.LayoutParams)reLayParams);
/* 129 */     reLayout.setBackgroundColor(Color.parseColor("#0066cc"));
/*     */ 
/*     */     
/* 132 */     TextView titleText = new TextView(this.mContext);
/* 133 */     RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(-2, -2);
/*     */ 
/*     */     
/* 136 */     titleParam.addRule(14);
/* 137 */     titleParam.addRule(15);
/* 138 */     titleText.setVisibility(0);
/* 139 */     titleText.setLayoutParams((ViewGroup.LayoutParams)titleParam);
/* 140 */     titleText.setGravity(16);
/*     */ 
/*     */ 
/*     */     
/* 144 */     titleText.setTextColor(Color.parseColor("#ffffff"));
/* 145 */     titleText.setTextSize(2, 18.0F);
/* 146 */     titleText.setText("银联支付");
/*     */ 
/*     */     
/* 149 */     this.backApp = new TextView(this.mContext);
/* 150 */     RelativeLayout.LayoutParams backAppParam = new RelativeLayout.LayoutParams(-2, -2);
/*     */ 
/*     */     
/* 153 */     backAppParam.addRule(15);
/* 154 */     backAppParam.addRule(11);
/* 155 */     backAppParam.setMargins(0, 0, 
/* 156 */         CcbPayUtil.getInstance().getDimensionToDip(5, this.dm), 0);
/* 157 */     this.backApp.setLayoutParams((ViewGroup.LayoutParams)backAppParam);
/*     */     
/* 159 */     this.backApp.setVisibility(0);
/* 160 */     this.backApp.setPadding(CcbPayUtil.getInstance().getDimensionToDip(5, this.dm), 
/* 161 */         CcbPayUtil.getInstance().getDimensionToDip(5, this.dm), 
/* 162 */         CcbPayUtil.getInstance().getDimensionToDip(5, this.dm), 
/* 163 */         CcbPayUtil.getInstance().getDimensionToDip(5, this.dm));
/* 164 */     this.backApp.setGravity(16);
/* 165 */     this.backApp.setTextColor(Color.parseColor("#ffffff"));
/* 166 */     this.backApp.setTextSize(2, 16.0F);
/*     */     
/* 168 */     this.backApp.setText("重选支付方式");
/* 169 */     this.backApp.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v)
/*     */           {
/* 173 */             CcbUnionPayActivity.this.finish();
/*     */           }
/*     */         });
/* 176 */     reLayout.addView((View)titleText);
/* 177 */     reLayout.addView((View)this.backApp);
/* 178 */     rootLayout.addView((View)reLayout);
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.mWebView = new WebView(this.mContext);
/* 183 */     this.mWebView.setVisibility(0);
/* 184 */     ViewGroup.LayoutParams webParams = new ViewGroup.LayoutParams(-1, -1);
/*     */ 
/*     */     
/* 187 */     rootLayout.addView((View)this.mWebView, webParams);
/*     */     
/* 189 */     setContentView((View)rootLayout, (ViewGroup.LayoutParams)rootParams);
/*     */   }
/*     */ 
/*     */   
/*     */   private void webViewSettings() {
/* 194 */     WebSettings settings = this.mWebView.getSettings();
/*     */     
/* 196 */     String webviewAgent = settings.getUserAgentString();
/* 197 */     CcbSdkLogUtil.d("---webview端useragent---", webviewAgent);
/* 198 */     settings.setUserAgentString(webviewAgent + " " + "CCBSDK/2.2.0");
/*     */     
/* 200 */     settings.setUseWideViewPort(false);
/* 201 */     settings.setLoadWithOverviewMode(false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     settings.setJavaScriptEnabled(true);
/* 207 */     settings.setJavaScriptCanOpenWindowsAutomatically(true);
/*     */ 
/*     */ 
/*     */     
/* 211 */     this.mWebView.setWebViewClient(new MyWebViewClient());
/* 212 */     this.mWebView.setWebChromeClient(new WebChromeClient()
/*     */         {
/*     */           public void onProgressChanged(WebView view, int newProgress) {
/* 215 */             CcbSdkLogUtil.i("---onProgressChanged---", newProgress + "");
/* 216 */             super.onProgressChanged(view, newProgress);
/*     */           }
/*     */         });
/*     */     
/* 220 */     this.mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "javaObj");
/* 221 */     this.mWebView.loadUrl(this.httpUrl);
/*     */   }
/*     */   
/*     */   private class MyWebViewClient
/*     */     extends WebViewClient {
/*     */     private MyWebViewClient() {}
/*     */     
/*     */     public void onPageStarted(WebView view, String url, Bitmap favicon) {
/* 229 */       CcbSdkLogUtil.d("---pageStart---", url);
/* 230 */       CcbPayUtil.getInstance().showLoadingDialog((Context)CcbUnionPayActivity.this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onPageFinished(WebView view, String url) {
/* 236 */       CcbSdkLogUtil.d("---pageFinished---", url);
/* 237 */       CcbPayUtil.getInstance().dismissLoading();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
/* 244 */       CcbSdkLogUtil.d("---页面加载有误---", failingUrl);
/* 245 */       CcbPayUtil.getInstance().dismissLoading();
/* 246 */       CCBAlertDialog dialog = new CCBAlertDialog((Context)CcbUnionPayActivity.this, description);
/*     */       
/* 248 */       dialog.showDialog();
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
/* 259 */       CcbSdkLogUtil.d("---shouldOverrideUrlLoading---", url);
/* 260 */       if (url.startsWith("http://") || url.startsWith("https://")) {
/* 261 */         CcbSdkLogUtil.d("---处理http开头url路径---", url);
/* 262 */         return false;
/*     */       } 
/* 264 */       CcbSdkLogUtil.d("---处理非http等开头url路径---", url);
/*     */       try {
/* 266 */         Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
/* 267 */         CcbUnionPayActivity.this.startActivity(intent);
/* 268 */       } catch (Exception e) {
/* 269 */         CCBAlertDialog dialog = new CCBAlertDialog((Context)CcbUnionPayActivity.this, "未检测到相关客户端，请安装后重试。");
/*     */         
/* 271 */         dialog.showDialog();
/*     */       } 
/* 273 */       return true;
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
/*     */   
/*     */   public class MyJavaScriptInterface
/*     */   {
/*     */     @JavascriptInterface
/*     */     public void sdkCallBack(String val) {
/* 298 */       CcbSdkLogUtil.d("---H5 sdkCallBack---", val);
/* 299 */       Map<String, String> map = CcbPayUtil.getInstance().splitResult(val);
/* 300 */       CcbPayUtil.getInstance().sendSuccess(map);
/* 301 */       CcbUnionPayActivity.this.finish();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void showFinish() {
/* 308 */       CcbSdkLogUtil.d("H5支付", "显示完成按钮");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void payBack() {
/* 315 */       CcbSdkLogUtil.d("---H5支付返回---");
/* 316 */       CcbPayUtil.getInstance().onSendendResultMsg(2, "取消支付");
/* 317 */       CcbUnionPayActivity.this.finish();
/*     */     }
/*     */ 
/*     */     
/*     */     @JavascriptInterface
/*     */     public void payDone(String str) {
/* 323 */       CcbSdkLogUtil.d("---H5支付完成---", str);
/* 324 */       Map<String, String> map = CcbPayUtil.getInstance().splitResult(str);
/* 325 */       CcbPayUtil.getInstance().sendSuccess(map);
/* 326 */       CcbUnionPayActivity.this.finish();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 337 */     if (keyCode == 4)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 344 */       return true;
/*     */     }
/* 346 */     return super.onKeyDown(keyCode, event);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDestroy() {
/* 351 */     super.onDestroy();
/* 352 */     if (this.mWebView != null) {
/* 353 */       ViewParent parent = this.mWebView.getParent();
/* 354 */       if (null != parent)
/* 355 */         ((ViewGroup)parent).removeView((View)this.mWebView); 
/* 356 */       this.mWebView.stopLoading();
/* 357 */       this.mWebView.getSettings().setJavaScriptEnabled(false);
/* 358 */       this.mWebView.clearHistory();
/* 359 */       this.mWebView.removeAllViews();
/*     */       try {
/* 361 */         this.mWebView.destroy();
/* 362 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Intent creatIntent(Context c, String url) {
/* 369 */     Intent intent = new Intent();
/* 370 */     intent.setClass(c, CcbUnionPayActivity.class);
/* 371 */     Bundle bundle = new Bundle();
/* 372 */     bundle.putString("httpurl", url);
/* 373 */     intent.putExtras(bundle);
/* 374 */     return intent;
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\activity\CcbUnionPayActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */