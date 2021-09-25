/*     */ package com.ccb.ccbnetpay.platform;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.text.TextUtils;
/*     */ import com.ccb.ccbnetpay.message.CcbPayResultListener;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ import com.ccb.ccbnetpay.util.NetUtil;
/*     */ import com.tencent.mm.opensdk.modelbase.BaseReq;
/*     */ import com.tencent.mm.opensdk.modelpay.PayReq;
/*     */ import com.tencent.mm.opensdk.openapi.IWXAPI;
/*     */ import com.tencent.mm.opensdk.openapi.WXAPIFactory;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CcbPayWechatPlatform
/*     */   extends Platform
/*     */ {
/*  23 */   public static final String TAG = CcbPayWechatPlatform.class.getSimpleName();
/*     */   
/*     */   private IWXAPI api;
/*     */ 
/*     */   
/*     */   protected void displayJumpWeChat(String result) {
/*     */     try {
/*  30 */       String appid = NetUtil.getKeyWords(this.params, "SUB_APPID=");
/*  31 */       this.api = WXAPIFactory.createWXAPI((Context)this.mActivity, appid);
/*  32 */       this.api.registerApp(appid);
/*  33 */       JSONObject jsonObj = new JSONObject(result);
/*  34 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/*  35 */         String payUrl = jsonObj.getString("PAYURL");
/*  36 */         CcbSdkLogUtil.i(TAG, "---获得微信支付参数的URL---" + payUrl);
/*  37 */         if (TextUtils.isEmpty(payUrl)) {
/*  38 */           onSendMsgDialog(1, "微信支付失败\n参考码:SDKWX1.PAYURL为空");
/*     */           
/*     */           return;
/*     */         } 
/*  42 */         int index = payUrl.indexOf("?");
/*  43 */         if (payUrl.startsWith("http") && -1 != index) {
/*  44 */           String[] array = payUrl.split("[?]");
/*  45 */           if (null != array && 0 < array.length) {
/*  46 */             NetUtil.httpSendPost(array[0], array[1], new NetUtil.SendCallBack()
/*     */                 {
/*     */                   public void success(String rs)
/*     */                   {
/*  50 */                     CcbSdkLogUtil.i(CcbPayWechatPlatform.TAG, "---获取微信支付请求参数结果---" + rs);
/*     */                     try {
/*  52 */                       JSONObject payResult = new JSONObject(rs);
/*  53 */                       if (CcbPayUtil.getInstance().isSuccess(payResult)) {
/*     */                         
/*  55 */                         PayReq payReq = new PayReq();
/*  56 */                         payReq.appId = payResult.getString("appId");
/*  57 */                         payReq.partnerId = payResult.getString("partnerid");
/*  58 */                         payReq.prepayId = payResult.getString("prepayid");
/*  59 */                         payReq.packageValue = payResult.getString("package");
/*  60 */                         payReq.nonceStr = payResult.getString("nonceStr");
/*  61 */                         payReq.timeStamp = payResult.getString("timeStamp");
/*     */                         
/*  63 */                         payReq.sign = payResult.getString("paySign");
/*     */                         
/*  65 */                         CcbPayWechatPlatform.this.api.sendReq((BaseReq)payReq);
/*  66 */                         CcbPayWechatPlatform.this.dismissLoadingDialog();
/*     */                       
/*     */                       }
/*     */                       else {
/*     */ 
/*     */                         
/*  72 */                         CcbSdkLogUtil.d(payResult.getString("ERRCODE") + "---获取微信支付请求参数失败---" + payResult
/*  73 */                             .getString("ERRMSG"));
/*  74 */                         CcbPayUtil.getInstance().onSendMsgSucc(payResult);
/*     */                         return;
/*     */                       } 
/*  77 */                     } catch (Exception e) {
/*  78 */                       CcbSdkLogUtil.i(CcbPayWechatPlatform.TAG, "---获取微信支付请求参数失败---" + e.getMessage());
/*  79 */                       CcbPayWechatPlatform.this.onSendMsgDialog(1, "微信支付失败\n参考码:SDKWX1");
/*     */                       return;
/*     */                     } 
/*     */                   }
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void failed(Exception e) {
/*  87 */                     CcbSdkLogUtil.d("---获取微信支付请求参数失败---" + e.getMessage());
/*  88 */                     CcbPayWechatPlatform.this.onSendMsgDialog(1, "微信支付失败\n参考码:SDKWX1");
/*     */                   }
/*     */                 });
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/*  95 */           onSendMsgDialog(1, "微信支付失败\n参考码:SDKWX1");
/*     */           
/*     */           return;
/*     */         } 
/*     */       } else {
/* 100 */         CcbSdkLogUtil.i("---跳转微信支付页面失败---", jsonObj
/* 101 */             .getString("ERRMSG") + "\n参考码:SDKWX1." + jsonObj.getString("ERRCODE"));
/* 102 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */         return;
/*     */       } 
/* 105 */     } catch (Exception e) {
/* 106 */       CcbSdkLogUtil.d("---跳转微信支付页面失败---" + e.getMessage());
/* 107 */       onSendMsgDialog(1, "跳转微信支付页面失败\n参考码:SDKWX1.\"\"");
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CcbPayWechatPlatform(Builder builder) {
/* 114 */     this.params = builder.params;
/* 115 */     this.mActivity = builder.activity;
/* 116 */     this.payStyle = PayStyle.WECHAT_PAY;
/*     */     
/* 118 */     CcbPayUtil.getInstance().setListener(builder.listener);
/* 119 */     CcbPayUtil.getInstance().setmContext(this.mActivity);
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private String params;
/*     */     private Activity activity;
/* 125 */     private CcbPayResultListener listener = null;
/*     */     
/*     */     public Builder setListener(CcbPayResultListener l) {
/* 128 */       this.listener = l;
/* 129 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setParams(String param) {
/* 133 */       this.params = param;
/* 134 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setActivity(Activity a) {
/* 138 */       this.activity = a;
/* 139 */       return this;
/*     */     }
/*     */     
/*     */     public Platform build() {
/* 143 */       return new CcbPayWechatPlatform(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\platform\CcbPayWechatPlatform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */