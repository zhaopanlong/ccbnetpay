/*     */ package com.ccb.ccbnetpay.util;
/*     */ 
/*     */ import android.text.TextUtils;
/*     */ import android.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CcbSdkLogUtil
/*     */ {
/*     */   public static final boolean IS_DEBUG = true;
/*     */   private static final String TAG = "CCB_SDK_LOG";
/*     */   private static final int LOG_LENGTH = 3000;
/*     */   
/*     */   public static void v(String msg) {
/*  18 */     v(null, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void v(String tag, String msg) {
/*  25 */     if (msg.length() < 3000) {
/*  26 */       Log.v(getTag(tag), msg);
/*     */     } else {
/*     */       
/*  29 */       String str1 = msg.substring(0, 3000);
/*  30 */       Log.v(getTag(tag), str1);
/*  31 */       v(getTag(tag), msg.substring(3000));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void d(String msg) {
/*  36 */     d(null, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void d(String tag, String msg) {
/*  43 */     if (msg.length() < 3000) {
/*  44 */       Log.d(getTag(tag), msg);
/*     */     } else {
/*     */       
/*  47 */       String str1 = msg.substring(0, 3000);
/*  48 */       Log.d(getTag(tag), str1);
/*  49 */       d(getTag(tag), msg.substring(3000));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void i(String msg) {
/*  54 */     i(null, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void i(String tag, String msg) {
/*  61 */     if (msg.length() < 3000) {
/*  62 */       Log.i(getTag(tag), msg);
/*     */     } else {
/*     */       
/*  65 */       String str1 = msg.substring(0, 3000);
/*  66 */       Log.i(getTag(tag), str1);
/*  67 */       i(getTag(tag), msg.substring(3000));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void w(String msg) {
/*  75 */     if (msg.length() < 3000) {
/*  76 */       Log.w("CCB_SDK_LOG", msg);
/*     */     } else {
/*     */       
/*  79 */       String str1 = msg.substring(0, 3000);
/*  80 */       Log.w("CCB_SDK_LOG", str1);
/*  81 */       w(msg.substring(3000));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void e(String msg) {
/*  86 */     e(null, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void e(String tag, String msg) {
/*  93 */     if (msg.length() < 3000) {
/*     */ 
/*     */       
/*  96 */       String errorPoint = "error at " + Thread.currentThread().getStackTrace()[2].getMethodName() + " called by " + Thread.currentThread().getStackTrace()[3].getClassName() + "::" + Thread.currentThread().getStackTrace()[3].getMethodName();
/*     */       
/*  98 */       Log.e(getTag(tag), msg);
/*  99 */       Log.e(getTag(tag), errorPoint);
/*     */     } else {
/*     */       
/* 102 */       String str1 = msg.substring(0, 3000);
/* 103 */       Log.e(getTag(tag), str1);
/* 104 */       e(getTag(tag), msg.substring(3000));
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
/*     */   public static void printProcess(String msg) {
/* 118 */     String info = Thread.currentThread().getStackTrace()[3].getClassName() + "::" + Thread.currentThread().getStackTrace()[3].getMethodName();
/* 119 */     if (msg != null) {
/* 120 */       info = info + "  " + msg;
/*     */     }
/* 122 */     i(info);
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
/*     */   public static void whoInvokeMe() {
/* 134 */     String callerInfo = Thread.currentThread().getStackTrace()[3].getMethodName() + " called by " + Thread.currentThread().getStackTrace()[4].getClassName() + "::" + Thread.currentThread().getStackTrace()[4].getMethodName();
/*     */     
/* 136 */     i(callerInfo);
/*     */   }
/*     */   
/*     */   private static String getTag(String tag) {
/* 140 */     return !TextUtils.isEmpty(tag) ? ("CCB_SDK_LOG: " + tag + " ") : "CCB_SDK_LOG";
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpa\\util\CcbSdkLogUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */