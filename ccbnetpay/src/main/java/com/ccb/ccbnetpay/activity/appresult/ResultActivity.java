/*    */ package com.ccb.ccbnetpay.activity.appresult;
/*    */ 
/*    */ import android.app.Activity;
/*    */ import android.content.Intent;
/*    */ import android.net.Uri;
/*    */ import android.os.Bundle;
/*    */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*    */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResultActivity
/*    */   extends Activity
/*    */ {
/*    */   protected void onCreate(Bundle savedInstanceState) {
/* 20 */     super.onCreate(savedInstanceState);
/*    */     
/* 22 */     String rs = "";
/* 23 */     Intent intent = getIntent();
/* 24 */     Uri uri = intent.getData();
/* 25 */     if (null == uri) {
/* 26 */       rs = intent.getStringExtra("CCBPARAM");
/*    */     } else {
/* 28 */       rs = uri.getQuery();
/*    */     } 
/* 30 */     CcbSdkLogUtil.i("---ResultActivity---", "----支付结果----" + rs);
/*    */     
/* 32 */     Map<String, String> map = CcbPayUtil.getInstance().splitResult(rs);
/* 33 */     CcbPayUtil.getInstance().sendSuccess(map);
/* 34 */     finish();
/*    */   }
/*    */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\activity\appresult\ResultActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */