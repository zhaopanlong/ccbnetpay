/*    */ package com.ccb.ccbnetpay.util;
/*    */ 
/*    */ import java.security.cert.CertificateException;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.HostnameVerifier;
/*    */ import javax.net.ssl.HttpsURLConnection;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.SSLSession;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import javax.net.ssl.X509TrustManager;
/*    */ 
/*    */ 
/*    */ public class SslUtils
/*    */ {
/*    */   private static void trustAllHttpsCertificates() throws Exception {
/* 16 */     TrustManager[] trustAllCerts = new TrustManager[1];
/* 17 */     TrustManager tm = new miTM();
/* 18 */     trustAllCerts[0] = tm;
/* 19 */     SSLContext sc = SSLContext.getInstance("SSL");
/* 20 */     sc.init(null, trustAllCerts, null);
/* 21 */     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/*    */   }
/*    */   
/*    */   static class miTM
/*    */     implements TrustManager, X509TrustManager
/*    */   {
/*    */     public X509Certificate[] getAcceptedIssuers() {
/* 28 */       return null;
/*    */     }
/*    */     
/*    */     public boolean isServerTrusted(X509Certificate[] certs) {
/* 32 */       return true;
/*    */     }
/*    */     
/*    */     public boolean isClientTrusted(X509Certificate[] certs) {
/* 36 */       return true;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void ignoreSsl() throws Exception {
/* 56 */     HostnameVerifier hv = new HostnameVerifier() {
/*    */         public boolean verify(String urlHostName, SSLSession session) {
/* 58 */           return true;
/*    */         }
/*    */       };
/* 61 */     trustAllHttpsCertificates();
/* 62 */     HttpsURLConnection.setDefaultHostnameVerifier(hv);
/*    */   }
/*    */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpa\\util\SslUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */