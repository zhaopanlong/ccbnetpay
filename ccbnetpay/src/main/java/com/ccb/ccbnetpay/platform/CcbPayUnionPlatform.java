/*    */ package com.ccb.ccbnetpay.platform;
/*    */ 
/*    */ import android.app.Activity;
/*    */ import android.content.Context;
/*    */ import com.ccb.ccbnetpay.activity.CcbUnionPayActivity;
/*    */ import com.ccb.ccbnetpay.message.CcbPayResultListener;
/*    */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*    */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CcbPayUnionPlatform
/*    */   extends Platform
/*    */ {
/*    */   protected void jumpUnionPay(String url) {
/* 19 */     CcbSdkLogUtil.i("---CcbPayUnionPlatform---", "---获取跳转银联支付页面路径结果---" + url);
/* 20 */     dismissLoadingDialog();
/* 21 */     this.mActivity.startActivity(CcbUnionPayActivity.creatIntent((Context)this.mActivity, url));
/*    */   }
/*    */ 
/*    */   
/*    */   public CcbPayUnionPlatform(Builder builder) {
/* 26 */     this.params = builder.params;
/* 27 */     this.mActivity = builder.activity;
/* 28 */     this.payStyle = PayStyle.UNION_PAY;
/* 29 */     CcbPayUtil.getInstance().setListener(builder.listener);
/* 30 */     CcbPayUtil.getInstance().setmContext(this.mActivity);
/*    */   }
/*    */   
/*    */   public static class Builder {
/*    */     private String params;
/*    */     private Activity activity;
/* 36 */     private CcbPayResultListener listener = null;
/*    */     
/*    */     public Builder setListener(CcbPayResultListener l) {
/* 39 */       this.listener = l;
/* 40 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setParams(String param) {
/* 44 */       this.params = param;
/* 45 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setActivity(Activity a) {
/* 49 */       this.activity = a;
/* 50 */       return this;
/*    */     }
/*    */     
/*    */     public Platform build() {
/* 54 */       return new CcbPayUnionPlatform(this);
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\platform\CcbPayUnionPlatform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */