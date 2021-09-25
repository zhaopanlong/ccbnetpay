/*     */ package com.ccb.ccbnetpay.platform;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.net.Uri;
/*     */ import android.text.TextUtils;
/*     */ import com.ccb.ccbnetpay.activity.CcbH5PayActivity;
/*     */ import com.ccb.ccbnetpay.message.CcbPayResultListener;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ import com.ccb.ccbnetpay.util.NetUtil;
/*     */ import java.net.URLDecoder;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CcbPayAliPlatform
/*     */   extends Platform
/*     */ {
/*  23 */   private String alipayHttp = "";
/*  24 */   private final String LOGTAG = "CcbPayAliPlatform";
/*     */ 
/*     */   
/*     */   protected void jumpAliPay(String result) {
/*     */     try {
/*  29 */       JSONObject jsonObj = new JSONObject(result);
/*     */       
/*  31 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/*  32 */         String liPayUrl = jsonObj.getString("mweb_url");
/*  33 */         CcbSdkLogUtil.d("---唤起支付宝支付的URL---" + liPayUrl);
/*  34 */         if (TextUtils.isEmpty(liPayUrl)) {
/*  35 */           onSendMsgDialog(1, "跳转支付宝支付页面失败\n参考码:SDK4AL");
/*     */           
/*     */           return;
/*     */         } 
/*  39 */         liPayUrl = URLDecoder.decode(liPayUrl, "UTF-8");
/*  40 */         CcbSdkLogUtil.d("---URLDecode解码后支付宝的URL---" + liPayUrl);
/*  41 */         dismissLoadingDialog();
/*  42 */         this.mActivity.startActivity(CcbH5PayActivity.creatIntent((Context)this.mActivity, liPayUrl, "", this.payStyle));
/*     */       } else {
/*  44 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 103 */     catch (Exception e) {
/* 104 */       CcbSdkLogUtil.i("CcbPayAliPlatform", "---跳转支付宝支付页面失败---" + e.getMessage());
/* 105 */       onSendMsgDialog(1, "跳转支付宝支付页面失败");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onPayUrlReq(String dataRes) {
/*     */     try {
/* 111 */       JSONObject jsonObj = new JSONObject(dataRes);
/* 112 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/* 113 */         String payUrl = jsonObj.getString("PAYURL");
/* 114 */         CcbSdkLogUtil.i("CcbPayAliPlatform", "---解析得到PAYURL---" + payUrl);
/* 115 */         String[] urlArray = payUrl.split("[?]");
/* 116 */         NetUtil.httpSendPost(urlArray[0], urlArray[1], new NetUtil.SendCallBack()
/*     */             {
/*     */               public void success(String result)
/*     */               {
/* 120 */                 CcbSdkLogUtil.i("CcbPayAliPlatform", "---PAYURL请求结果---" + result);
/* 121 */                 if (TextUtils.isEmpty(result)) {
/* 122 */                   CcbPayAliPlatform.this.onSendMsgDialog(1, "跳转支付宝支付页面失败");
/*     */                 } else {
/* 124 */                   CcbPayAliPlatform.this.getQrUrl(result);
/*     */                 } 
/*     */               }
/*     */               
/*     */               public void failed(Exception e) {
/* 129 */                 CcbSdkLogUtil.i("CcbPayAliPlatform", "---PAYURL请求异常---" + e.getMessage());
/* 130 */                 CcbPayAliPlatform.this.onSendMsgDialog(1, "跳转支付宝支付页面失败");
/*     */               }
/*     */             });
/*     */       } else {
/* 134 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */       } 
/* 136 */     } catch (Exception e) {
/* 137 */       CcbSdkLogUtil.i("CcbPayAliPlatform", "---解析mweb_url请求结果异常---" + e.getMessage());
/* 138 */       onSendMsgDialog(1, "跳转支付宝支付页面失败");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getQrUrl(String data) {
/*     */     try {
/* 144 */       JSONObject jsonObj = new JSONObject(data);
/* 145 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/* 146 */         String qrUrl = jsonObj.getString("QRURL");
/* 147 */         CcbSdkLogUtil.i("CcbPayAliPlatform", "---解析得到QRURL---" + qrUrl);
/* 148 */         dismissLoadingDialog();
/*     */         
/* 150 */         String aliUrl = this.alipayHttp + qrUrl;
/* 151 */         CcbSdkLogUtil.i("CcbPayAliPlatform", "---获取跳转支付宝支付页面URL---" + aliUrl);
/* 152 */         Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(aliUrl));
/* 153 */         this.mActivity.startActivity(intent);
/*     */       } else {
/* 155 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */       } 
/* 157 */     } catch (Exception e) {
/* 158 */       CcbSdkLogUtil.i("CcbPayAliPlatform", "---解析PAYURL请求结果异常---" + e.getMessage());
/* 159 */       onSendMsgDialog(1, "跳转支付宝支付页面失败");
/*     */     } 
/*     */   }
/*     */   
/*     */   public CcbPayAliPlatform(Builder builder) {
/* 164 */     this.params = builder.params;
/* 165 */     this.mActivity = builder.activity;
/* 166 */     this.payStyle = PayStyle.ALI_PAY;
/*     */     
/* 168 */     CcbPayUtil.getInstance().setListener(builder.listener);
/* 169 */     CcbPayUtil.getInstance().setmContext(this.mActivity);
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private String params;
/*     */     private Activity activity;
/* 175 */     private CcbPayResultListener listener = null;
/*     */     
/*     */     public Builder setListener(CcbPayResultListener l) {
/* 178 */       this.listener = l;
/* 179 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setParams(String param) {
/* 183 */       this.params = param;
/* 184 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setActivity(Activity a) {
/* 188 */       this.activity = a;
/* 189 */       return this;
/*     */     }
/*     */     
/*     */     public Platform build() {
/* 193 */       return new CcbPayAliPlatform(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\platform\CcbPayAliPlatform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */