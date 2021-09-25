/*    */ package com.ccb.ccbnetpay.util;
/*    */ 
/*    */ import android.text.TextUtils;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IPUtil
/*    */ {
/*    */   public static String getIPAddress() {
/* 24 */     String ipUrl = "http://pv.sohu.com/cityjson?ie=utf-8";
/* 25 */     String iprs = "";
/*    */     try {
/* 27 */       iprs = NetUtil.sendGet(ipUrl);
/* 28 */     } catch (Exception e1) {
/* 29 */       CcbSdkLogUtil.i("---获得本机ip异常-- ", iprs);
/*    */     } 
/* 31 */     String phoneIp = "";
/* 32 */     CcbSdkLogUtil.i("---获取本机ip结果-- ", iprs);
/* 33 */     if (TextUtils.isEmpty(iprs)) {
/* 34 */       CcbSdkLogUtil.i("---本机ip为空-- ", iprs);
/*    */     } else {
/*    */       
/* 37 */       int start = iprs.indexOf("{");
/* 38 */       int end = iprs.indexOf("}");
/* 39 */       String json = iprs.substring(start, end + 1);
/* 40 */       if (json != null) {
/*    */         try {
/* 42 */           JSONObject jsonObject = new JSONObject(json);
/* 43 */           phoneIp = jsonObject.optString("cip");
/* 44 */           CcbSdkLogUtil.i("---本机ip为---", phoneIp);
/* 45 */         } catch (Exception e) {
/* 46 */           phoneIp = "";
/*    */         } 
/*    */       }
/*    */     } 
/* 50 */     return phoneIp;
/*    */   }
/*    */   
/* 53 */   static String phoneIp = "";
/*    */ 
/*    */   
/*    */   public static String getIPAddress(final GetPhoneIPListener ipListener) {
/* 57 */     String ipUrl = "http://pv.sohu.com/cityjson?ie=utf-8";
/* 58 */     NetUtil.httpSendGet(ipUrl, new NetUtil.SendCallBack()
/*    */         {
/*    */           public void success(String result) {
/* 61 */             CcbSdkLogUtil.d("---获取本机ip结果-- " + result);
/* 62 */             if (TextUtils.isEmpty(result)) {
/* 63 */               CcbSdkLogUtil.d("---本机ip为空-- " + result);
/*    */             } else {
/*    */               
/* 66 */               int start = result.indexOf("{");
/* 67 */               int end = result.indexOf("}");
/* 68 */               String json = result.substring(start, end + 1);
/* 69 */               if (json != null) {
/*    */                 try {
/* 71 */                   JSONObject jsonObject = new JSONObject(json);
/* 72 */                   IPUtil.phoneIp = jsonObject.optString("cip");
/* 73 */                   if (null != ipListener) {
/* 74 */                     ipListener.success(IPUtil.phoneIp);
/*    */                   }
/* 76 */                   CcbSdkLogUtil.d("---本机ip为---" + IPUtil.phoneIp);
/* 77 */                 } catch (Exception e) {
/* 78 */                   e.printStackTrace();
/*    */                 } 
/*    */               }
/*    */             } 
/*    */           }
/*    */ 
/*    */           
/*    */           public void failed(Exception e) {
/* 86 */             CcbSdkLogUtil.d("---获取手机ip异常--- " + e.getMessage());
/* 87 */             if (null != ipListener)
/* 88 */               ipListener.failed("获取IP地址异常"); 
/*    */           }
/*    */         });
/* 91 */     return phoneIp;
/*    */   }
/*    */   
/*    */   public static interface GetPhoneIPListener {
/*    */     void success(String param1String);
/*    */     
/*    */     void failed(String param1String);
/*    */   }
/*    */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpa\\util\IPUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */