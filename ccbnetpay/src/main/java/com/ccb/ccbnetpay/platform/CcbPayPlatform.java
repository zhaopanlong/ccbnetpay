/*     */ package com.ccb.ccbnetpay.platform;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.content.pm.PackageManager;
/*     */ import android.content.pm.ResolveInfo;
/*     */ import android.net.Uri;
/*     */ import android.text.TextUtils;
/*     */ import android.util.Log;
/*     */ import com.ccb.ccbnetpay.activity.CcbH5PayActivity;
/*     */ import com.ccb.ccbnetpay.message.CcbPayResultListener;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ import com.ccb.ccbnetpay.util.NetUtil;
/*     */ import java.util.List;
/*     */ import org.json.JSONException;
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
/*     */ 
/*     */ public class CcbPayPlatform
/*     */   extends Platform
/*     */ {
/*     */   public void pay() {
/*  33 */     if (this.payStyle == PayStyle.APP_OR_H5_PAY) {
/*  34 */       startAppOrH5();
/*  35 */     } else if (this.payStyle == PayStyle.APP_PAY) {
/*  36 */       startApp();
/*     */     } 
/*  38 */     super.pay();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pay(String sdkResult) {
/*  44 */     if (this.payStyle == PayStyle.APP_OR_H5_PAY) {
/*  45 */       startAppOrH5();
/*  46 */     } else if (this.payStyle == PayStyle.APP_PAY) {
/*  47 */       startApp();
/*     */     } 
/*  49 */     super.pay(sdkResult);
/*     */   }
/*     */   
/*     */   private void startApp() {
/*  53 */     if (hasInstallNum(this.params))
/*  54 */       this.payStyle = PayStyle.H5_PAY;
/*     */   }
/*     */   
/*     */   private void startAppOrH5() {
/*  58 */     if (hasInstallNum(this.params)) {
/*  59 */       this.payStyle = PayStyle.H5_PAY;
/*     */     
/*     */     }
/*  62 */     else if (checkAppInstalled("com.chinamworld.main")) {
/*  63 */       this.payStyle = PayStyle.APP_PAY;
/*     */     } else {
/*  65 */       this.payStyle = PayStyle.H5_PAY;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkAppInstalled(String packageName) {
/*  76 */     PackageManager pm = this.mActivity.getPackageManager();
/*  77 */     Intent checkIntent = pm.getLaunchIntentForPackage(packageName);
/*  78 */     if (null == checkIntent) {
/*  79 */       return false;
/*     */     }
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasInstallNum(String params) {
/*  92 */     boolean flag = false;
/*  93 */     if (-1 == params.indexOf("INSTALLNUM")) {
/*  94 */       flag = false;
/*     */     } else {
/*  96 */       String keyRs = NetUtil.getKeyWords(params, "INSTALLNUM=");
/*  97 */       Log.i("---INSTALLNUM??????---", keyRs);
/*  98 */       if (0 == keyRs.length() || "".equals(keyRs)) {
/*  99 */         flag = false;
/*     */       }
/* 101 */       else if (Integer.parseInt(keyRs) > 1) {
/* 102 */         flag = true;
/*     */       } else {
/* 104 */         flag = false;
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return flag;
/*     */   }
/*     */   
/*     */   public CcbPayPlatform(Builder builder) {
/* 112 */     this.params = builder.params;
/* 113 */     this.mActivity = builder.activity;
/* 114 */     this.payStyle = builder.payStyle;
/* 115 */     CcbPayUtil.getInstance().setListener(builder.listener);
/* 116 */     CcbPayUtil.getInstance().setmContext(this.mActivity);
/*     */   }
/*     */   
/*     */   protected void jumpAppPay(String httpUrl, String httpParams) {
/* 120 */     NetUtil.httpSendPost(httpUrl, httpParams, new NetUtil.SendCallBack()
/*     */         {
/*     */           public void success(String result)
/*     */           {
/* 124 */             CcbSdkLogUtil.d("---SDK001????????????---" + result);
/* 125 */             if (TextUtils.isEmpty(result)) {
/* 126 */               CcbPayPlatform.this.onSendMsgDialog(1, "??????????????????????????????\n?????????:SDK001.??????????????????");
/*     */             } else {
/*     */               
/* 129 */               CcbPayPlatform.this.displayJumpApp(result);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void failed(Exception e) {
/* 135 */             CcbSdkLogUtil.d("---SDK001????????????---", e.getLocalizedMessage());
/* 136 */             CcbPayPlatform.this.onSendMsgDialog(1, "??????????????????????????????\n?????????:SDK001.\"\"");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void displayJumpApp(String result) {
/*     */     try {
/* 145 */       JSONObject jsonObj = new JSONObject(result);
/* 146 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/* 147 */         String appUrl = jsonObj.getString("OPENAPPURL");
/*     */ 
/*     */         
/* 150 */         CcbSdkLogUtil.i("---??????url??????appURL---", appUrl);
/* 151 */         dismissLoadingDialog();
/*     */         
/* 153 */         Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(appUrl));
/* 154 */         this.mActivity.startActivity(intent);
/*     */       } else {
/* 156 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */       } 
/* 158 */     } catch (JSONException e) {
/* 159 */       onSendMsgDialog(1, "??????????????????????????????\n?????????:SDK001.\"\"");
/*     */       
/* 161 */       CcbSdkLogUtil.d("---????????????APP??????????????????---", e.getMessage());
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void jumpH5Pay(String newurls) {
/*     */     try {
/* 169 */       dismissLoadingDialog();
/* 170 */       this.mActivity.startActivity(CcbH5PayActivity.creatIntent((Context)this.mActivity, newurls, "", this.payStyle));
/* 171 */     } catch (Exception e) {
/* 172 */       onSendMsgDialog(1, "????????????APP?????????????????????CcbH5PayActivity\n?????????:\"\"");
/*     */       
/* 174 */       CcbSdkLogUtil.d("---????????????APP??????????????????---", e.getMessage());
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void authorize() {
/* 181 */     showLoadingDialog();
/* 182 */     String keyVal = NetUtil.getKeyWords(this.params, "TXCODE=");
/*     */ 
/*     */ 
/*     */     
/* 186 */     String sdk004params = this.params + "&CCB_IBSVersion=V6&PT_STYLE=3" + "&APP_TYPE=" + "1" + "&SDK_VERSION=" + "2.2.0" + "&SYS_VERSION=" + CcbPayUtil.getInstance().getSysVersion();
/* 187 */     if (!TextUtils.isEmpty(keyVal))
/* 188 */       sdk004params = sdk004params.replace(keyVal, "SDK004"); 
/* 189 */     NetUtil.httpSendPost("https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00", sdk004params, new NetUtil.SendCallBack()
/*     */         {
/*     */           public void success(String result)
/*     */           {
/* 193 */             CcbPayPlatform.this.dismissLoadingDialog();
/* 194 */             CcbSdkLogUtil.i("---SDK004????????????---", result);
/*     */             try {
/* 196 */               JSONObject jsonObj = new JSONObject(result);
/* 197 */               if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/* 198 */                 String urlParams = jsonObj.getString("OPENAPPURL");
/* 199 */                 CcbSdkLogUtil.i("----???????????????????????????APP?????????----", urlParams);
/*     */                 
/* 201 */                 Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("secretfree://free?" + urlParams));
/* 202 */                 PackageManager packageManager = CcbPayPlatform.this.mActivity.getPackageManager();
/* 203 */                 List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
/* 204 */                 boolean isValid = activities.isEmpty();
/* 205 */                 CcbSdkLogUtil.i("---scheme????????????---", (!isValid ? 1 : 0) + "");
/* 206 */                 if (!isValid) {
/* 207 */                   CcbPayPlatform.this.mActivity.startActivity(intent);
/*     */                 } else {
/* 209 */                   CcbPayPlatform.this.onSendMsgDialog(1, "????????????APP???scheme????????????");
/*     */                 } 
/*     */               } else {
/* 212 */                 CcbPayPlatform.this.onSendMsgDialog(1, jsonObj
/* 213 */                     .getString("ERRMSG") + "\n?????????:SDK004." + jsonObj.getString("ERRCODE"));
/*     */               } 
/* 215 */             } catch (Exception e) {
/* 216 */               CcbSdkLogUtil.i("---SDK004????????????---", e.getLocalizedMessage());
/* 217 */               CcbPayPlatform.this.onSendMsgDialog(1, "???????????????????????????\n?????????:SDK004.");
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void failed(Exception e) {
/* 224 */             CcbPayPlatform.this.dismissLoadingDialog();
/* 225 */             CcbSdkLogUtil.i("---SDK004????????????---", e.getMessage());
/* 226 */             CcbPayPlatform.this.onSendMsgDialog(1, "????????????\n?????????:SDK004.\"\"");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private String params;
/*     */     private Activity activity;
/*     */     private PayStyle payStyle;
/* 237 */     private CcbPayResultListener listener = null;
/*     */     
/*     */     public Builder setListener(CcbPayResultListener l) {
/* 240 */       this.listener = l;
/* 241 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setPayStyle(PayStyle style) {
/* 245 */       this.payStyle = style;
/* 246 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setParams(String param) {
/* 250 */       this.params = param;
/* 251 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setActivity(Activity a) {
/* 255 */       this.activity = a;
/* 256 */       return this;
/*     */     }
/*     */     
/*     */     public Platform build() {
/* 260 */       return new CcbPayPlatform(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\platform\CcbPayPlatform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */