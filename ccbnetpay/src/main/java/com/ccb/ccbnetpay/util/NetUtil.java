/*     */ package com.ccb.ccbnetpay.util;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Intent;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Rect;
/*     */ import android.net.Uri;
/*     */ import android.os.Environment;
/*     */ import android.text.TextUtils;
/*     */ import android.view.View;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetUtil
/*     */ {
/*     */   public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2)";
/*     */   public static final String ACCEPT = "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*";
/*     */   public static final String CONNECTION = "Keep_Alive";
/*     */   public static final String ACCEPT_LANGUAGE = "zh-cn,zh;q=0.5";
/*     */   public static final String ACCEPT_ENCODING = "gzip, deflate";
/*  45 */   public static final String SDCARD_PATH = Environment.getExternalStorageDirectory() + File.separator + "CcbPay" + File.separator + "Ali" + File.separator;
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
/*     */   public static void httpSendGet(final String strUrl, final SendCallBack callBack) {
/*  63 */     (new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/*  67 */               String result = NetUtil.sendGet(strUrl);
/*  68 */               callBack.success(result);
/*  69 */             } catch (Exception e) {
/*  70 */               callBack.failed(e);
/*     */             } 
/*     */           }
/*  73 */         })).start();
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
/*     */   public static void httpSendPost(final String strUrl, final String params, final SendCallBack callBack) {
/*  86 */     (new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/*  90 */               String result = NetUtil.sendPost(strUrl, params);
/*  91 */               callBack.success(result);
/*  92 */             } catch (Exception e) {
/*  93 */               callBack.failed(e);
/*     */             } 
/*     */           }
/*  96 */         })).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sendGet(String strUrl) throws Exception {
/* 101 */     String result = "";
/* 102 */     InputStreamReader inputStreamReader = null;
/*     */     
/* 104 */     HttpURLConnection conn = getConnection(strUrl, "GET");
/* 105 */     conn.setDoOutput(false);
/* 106 */     conn.connect();
/*     */     
/* 108 */     int response = conn.getResponseCode();
/* 109 */     if (200 == response) {
/* 110 */       InputStream inputStream = conn.getInputStream();
/* 111 */       if (conn.getContentEncoding() != null && conn.getContentEncoding().contains("gzip")) {
/* 112 */         GZIPInputStream gzipIn = new GZIPInputStream(inputStream);
/* 113 */         inputStreamReader = new InputStreamReader(gzipIn, "UTF-8");
/*     */       } else {
/* 115 */         inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
/*     */       } 
/*     */       
/* 118 */       BufferedReader buffReader = new BufferedReader(inputStreamReader);
/* 119 */       String line = "";
/* 120 */       while ((line = buffReader.readLine()) != null) {
/* 121 */         result = result + line;
/*     */       }
/*     */       
/* 124 */       if (buffReader != null)
/* 125 */         buffReader.close(); 
/* 126 */       if (inputStreamReader != null)
/* 127 */         inputStreamReader.close(); 
/*     */     } 
/* 129 */     conn.disconnect();
/*     */     
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sendPost(String strUrl, String params) throws Exception {
/* 136 */     String result = "";
/*     */     
/* 138 */     byte[] data = params.getBytes();
/* 139 */     HttpURLConnection conn = getConnection(strUrl, "POST");
/*     */ 
/*     */     
/* 142 */     OutputStream outputStream = conn.getOutputStream();
/* 143 */     outputStream.write(data, 0, data.length);
/* 144 */     int response = conn.getResponseCode();
/* 145 */     InputStream inptStream = null;
/* 146 */     if (response == 200) {
/* 147 */       inptStream = conn.getInputStream();
/* 148 */       if (null != inptStream) {
/* 149 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 150 */         byte[] bytedata = new byte[1024];
/* 151 */         int len = 0;
/* 152 */         while ((len = inptStream.read(bytedata)) != -1) {
/* 153 */           byteArrayOutputStream.write(bytedata, 0, len);
/*     */         }
/* 155 */         result = new String(byteArrayOutputStream.toByteArray());
/* 156 */         byteArrayOutputStream.flush();
/*     */         
/* 158 */         inptStream.close();
/*     */       } 
/* 160 */       outputStream.close();
/*     */     } 
/* 162 */     conn.disconnect();
/*     */     
/* 164 */     return result;
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
/*     */   public static HttpURLConnection getConnection(String strUrl, String httpType) throws Exception {
/* 176 */     HttpURLConnection con = null;
/* 177 */     URL realUrl = new URL(strUrl);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     con = (HttpURLConnection)realUrl.openConnection();
/*     */     
/* 187 */     con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2)");
/* 188 */     con.setRequestProperty("accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
/* 189 */     con.setRequestProperty("Proxy-Connection", "Keep_Alive");
/* 190 */     con.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
/* 191 */     con.setRequestProperty("Accept-Encoding", "gzip, deflate");
/* 192 */     con.setRequestProperty("Charset", "UTF-8");
/* 193 */     con.setRequestMethod(httpType);
/* 194 */     con.setConnectTimeout(30000);
/* 195 */     con.setReadTimeout(30000);
/* 196 */     con.setDoInput(true);
/* 197 */     con.setDoOutput(true);
/* 198 */     con.setUseCaches(false);
/* 199 */     return con;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void trustAllHosts() {
/* 204 */     TrustManager[] trustAllCerts = { new X509TrustManager()
/*     */         {
/*     */           public X509Certificate[] getAcceptedIssuers() {
/* 207 */             return new X509Certificate[0];
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
/*     */         } };
/*     */     try {
/* 227 */       SSLContext sc = SSLContext.getInstance("SSL");
/* 228 */       sc.init(null, trustAllCerts, new SecureRandom());
/* 229 */       HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/* 230 */     } catch (Exception e) {
/* 231 */       e.printStackTrace();
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
/*     */   public static String getKeyWords(String params, String key) {
/* 249 */     String rsVal = "";
/* 250 */     if (TextUtils.isEmpty(params))
/* 251 */       return ""; 
/* 252 */     int indexSta = params.indexOf(key);
/* 253 */     if (-1 == indexSta)
/* 254 */       return ""; 
/* 255 */     int indexEnd = params.indexOf("&", indexSta);
/* 256 */     if (-1 == indexEnd) {
/* 257 */       rsVal = params.substring(indexSta + key.length());
/*     */     } else {
/* 259 */       rsVal = params.substring(indexSta + key.length(), indexEnd);
/*     */     } 
/* 261 */     if (TextUtils.isEmpty(rsVal))
/* 262 */       rsVal = ""; 
/* 263 */     return rsVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readStream(InputStream inStream) throws Exception {
/* 271 */     ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/* 272 */     byte[] buffer = new byte[1024];
/* 273 */     int len = 0;
/* 274 */     while ((len = inStream.read(buffer)) != -1) {
/* 275 */       outStream.write(buffer, 0, len);
/*     */     }
/* 277 */     outStream.close();
/*     */     
/* 279 */     return outStream.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void screenshot(Activity act) {
/* 284 */     View decorView = act.getWindow().getDecorView().getRootView();
/*     */     
/* 286 */     decorView.setDrawingCacheEnabled(true);
/* 287 */     decorView.buildDrawingCache();
/*     */     
/* 289 */     Bitmap temBitmap = decorView.getDrawingCache();
/* 290 */     CcbSdkLogUtil.d("----截屏文件宽---" + temBitmap.getWidth() + "----截屏文件高---" + temBitmap
/* 291 */         .getHeight());
/*     */     
/* 293 */     if (temBitmap != null) {
/*     */       
/*     */       try {
/* 296 */         CcbSdkLogUtil.d("----手机截屏文件夹路径----" + SDCARD_PATH);
/* 297 */         File files = new File(SDCARD_PATH);
/* 298 */         if (!files.exists()) {
/* 299 */           files.mkdirs();
/*     */         } else {
/* 301 */           deleteDirWihtFile(files);
/*     */         } 
/*     */ 
/*     */         
/* 305 */         String screenFileName = System.currentTimeMillis() + ".png";
/* 306 */         File file = new File(files, screenFileName);
/* 307 */         FileOutputStream os = new FileOutputStream(file);
/* 308 */         temBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
/* 309 */         os.flush();
/* 310 */         os.close();
/*     */ 
/*     */         
/* 313 */         CcbSdkLogUtil.d("----手机截屏文件路径----" + file.getAbsolutePath());
/*     */         
/* 315 */         Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
/* 316 */         Uri uri = Uri.fromFile(file);
/* 317 */         intent.setData(uri);
/* 318 */         act.sendBroadcast(intent);
/* 319 */         temBitmap.recycle();
/* 320 */       } catch (Exception e) {
/* 321 */         CcbSdkLogUtil.d("----手机截屏异常----" + e.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void deleteDirWihtFile(File dir) {
/* 327 */     if (dir == null || !dir.exists() || !dir.isDirectory()) {
/*     */       return;
/*     */     }
/* 330 */     for (File file : dir.listFiles()) {
/* 331 */       if (file.isFile()) {
/* 332 */         file.delete();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void screenshot(Activity act, int titleHeight) {
/* 343 */     View decorView = act.getWindow().getDecorView().getRootView();
/*     */     
/* 345 */     decorView.setDrawingCacheEnabled(true);
/* 346 */     decorView.buildDrawingCache();
/*     */     
/* 348 */     Bitmap temBitmap = decorView.getDrawingCache();
/* 349 */     CcbSdkLogUtil.d("----截屏文件宽---" + temBitmap.getWidth() + "----截屏文件高---" + temBitmap
/* 350 */         .getHeight());
/*     */     
/* 352 */     int width = decorView.getWidth();
/* 353 */     int height = decorView.getHeight();
/*     */     
/* 355 */     Rect rect = new Rect();
/* 356 */     decorView.getWindowVisibleDisplayFrame(rect);
/* 357 */     int statusTitleHeight = rect.top + titleHeight;
/* 358 */     CcbSdkLogUtil.d("----状态栏高度---" + rect.top + "----标题栏高度---" + titleHeight);
/*     */     
/* 360 */     Bitmap sdkfile = Bitmap.createBitmap(temBitmap, 0, statusTitleHeight, width, height - statusTitleHeight);
/*     */     
/* 362 */     decorView.destroyDrawingCache();
/* 363 */     if (sdkfile != null) {
/*     */       
/*     */       try {
/* 366 */         CcbSdkLogUtil.d("----手机截屏文件夹路径----" + SDCARD_PATH);
/* 367 */         File files = new File(SDCARD_PATH);
/* 368 */         if (!files.exists()) {
/* 369 */           files.mkdirs();
/*     */         }
/* 371 */         String screenFileName = System.currentTimeMillis() + ".png";
/* 372 */         File file = new File(files, screenFileName);
/* 373 */         FileOutputStream os = new FileOutputStream(file);
/* 374 */         sdkfile.compress(Bitmap.CompressFormat.PNG, 100, os);
/* 375 */         os.flush();
/* 376 */         os.close();
/*     */ 
/*     */         
/* 379 */         CcbSdkLogUtil.d("----手机截屏文件路径----" + file.getAbsolutePath());
/*     */         
/* 381 */         Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
/* 382 */         Uri uri = Uri.fromFile(file);
/* 383 */         intent.setData(uri);
/* 384 */         act.sendBroadcast(intent);
/* 385 */       } catch (Exception e) {
/* 386 */         CcbSdkLogUtil.d("----手机截屏异常----" + e.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRequestData(Map<String, String> params, String encode) throws IOException {
/* 397 */     StringBuilder stringBuilder = new StringBuilder();
/* 398 */     for (Map.Entry<String, String> entry : params.entrySet()) {
/* 399 */       stringBuilder.append(entry.getKey())
/* 400 */         .append("=")
/* 401 */         .append(URLEncoder.encode(entry.getValue(), encode))
/* 402 */         .append("&");
/*     */     }
/* 404 */     if (0 < stringBuilder.length())
/* 405 */       stringBuilder.deleteCharAt(stringBuilder.length() - 1); 
/* 406 */     CcbSdkLogUtil.d("---请求参数串---" + stringBuilder.toString());
/* 407 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static interface SendCallBack {
/*     */     void success(String param1String);
/*     */     
/*     */     void failed(Exception param1Exception);
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpa\\util\NetUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */