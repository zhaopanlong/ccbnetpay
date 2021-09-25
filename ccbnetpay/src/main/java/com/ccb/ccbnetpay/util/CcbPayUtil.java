/*     */ package com.ccb.ccbnetpay.util;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.res.AssetManager;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.Build;
/*     */ import android.text.TextUtils;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.util.TypedValue;
/*     */ import com.ccb.ccbnetpay.dialog.CCBProgressDialog;
/*     */ import com.ccb.ccbnetpay.message.CcbPayResultListener;
/*     */ import com.tencent.mm.opensdk.modelbase.BaseResp;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CcbPayUtil
/*     */ {
/*  32 */   private CcbPayResultListener listener = null;
/*     */   private Activity mContext;
/*     */   public CCBProgressDialog progressDialog;
/*     */   public CCBProgressDialog ccbProgressDialog;
/*  36 */   private Map<String, String> map = new HashMap<>(0);
/*     */   
/*     */   public void putData(String key, String val) {
/*  39 */     this.map.put(key, val);
/*     */   }
/*     */   
/*     */   public String getData(String key) {
/*  43 */     return this.map.get(key);
/*     */   }
/*     */   
/*     */   public void setmContext(Activity mContext) {
/*  47 */     this.mContext = mContext;
/*     */   }
/*     */   
/*     */   public void setListener(CcbPayResultListener listener) {
/*  51 */     this.listener = listener;
/*     */   }
/*     */   
/*     */   public void onSendendResultMsg(int num, String result) {
/*  55 */     if (num == 0) {
/*     */       
/*  57 */       Map<String, String> map = splitResult(result);
/*  58 */       sendSuccess(map);
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
/*  69 */     else if (num == 1) {
/*  70 */       sendFaild(result);
/*  71 */     } else if (num == 2) {
/*  72 */       sendFaild("取消支付");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onSendMsgSucc(JSONObject jsonObj) {
/*  77 */     dismissLoadingDialog();
/*  78 */     Map<String, String> map = new HashMap<>();
/*     */     try {
/*  80 */       Iterator<String> iterator = jsonObj.keys();
/*  81 */       String rskey = "", rsval = "";
/*  82 */       while (iterator.hasNext()) {
/*  83 */         rskey = iterator.next();
/*  84 */         rsval = jsonObj.getString(rskey);
/*  85 */         map.put(rskey, rsval);
/*     */       } 
/*  87 */     } catch (Exception e) {
/*  88 */       CcbSdkLogUtil.d("---解析json报错---" + e.getMessage());
/*     */     } 
/*  90 */     sendSuccess(map);
/*     */   }
/*     */   
/*     */   public void sendSuccess(final Map<String, String> re) {
/*  94 */     if (null == this.mContext)
/*     */       return; 
/*  96 */     if (null == this.listener)
/*     */       return; 
/*  98 */     this.mContext.runOnUiThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 101 */             CcbSdkLogUtil.i("---发送支付结果---", "---Map---");
/* 102 */             CcbPayUtil.this.listener.onSuccess(re);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void sendFaild(final String result) {
/* 108 */     if (null == this.mContext)
/*     */       return; 
/* 110 */     if (null == this.listener)
/*     */       return; 
/* 112 */     this.mContext.runOnUiThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 115 */             CcbPayUtil.this.listener.onFailed(result);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Map<String, String> splitResult(String strUrlParam) {
/* 121 */     Map<String, String> mapRequest = new HashMap<>();
/* 122 */     if (TextUtils.isEmpty(strUrlParam))
/* 123 */       return mapRequest; 
/* 124 */     String[] arrSplit = strUrlParam.split("[&]");
/* 125 */     for (String strSplit : arrSplit) {
/* 126 */       String[] arrSplitEqual = strSplit.split("[=]");
/*     */       
/* 128 */       if (1 < arrSplitEqual.length) {
/* 129 */         mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
/*     */       
/*     */       }
/* 132 */       else if (!TextUtils.isEmpty(arrSplitEqual[0])) {
/* 133 */         mapRequest.put(arrSplitEqual[0], "");
/*     */       } 
/*     */     } 
/* 136 */     return mapRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showLoadingDialog() {
/* 143 */     if (null == this.mContext)
/*     */       return; 
/* 145 */     this.mContext.runOnUiThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 148 */             if (null == CcbPayUtil.this.progressDialog) {
/* 149 */               CcbPayUtil.this.progressDialog = new CCBProgressDialog((Context)CcbPayUtil.this.mContext);
/*     */             } else {
/* 151 */               CcbPayUtil.this.dismissLoadingDialog();
/* 152 */               CcbPayUtil.this.progressDialog = new CCBProgressDialog((Context)CcbPayUtil.this.mContext);
/*     */             } 
/* 154 */             CcbPayUtil.this.progressDialog.showDialog();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismissLoadingDialog() {
/* 163 */     if (null == this.mContext)
/*     */       return; 
/* 165 */     this.mContext.runOnUiThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 168 */             if (null != CcbPayUtil.this.progressDialog)
/* 169 */               CcbPayUtil.this.progressDialog.disDialog(); 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void showLoadingDialog(Context contxt) {
/* 175 */     if (null == this.ccbProgressDialog) {
/* 176 */       this.ccbProgressDialog = new CCBProgressDialog(contxt);
/*     */     } else {
/* 178 */       dismissLoading();
/* 179 */       this.ccbProgressDialog = new CCBProgressDialog(contxt);
/*     */     } 
/* 181 */     this.ccbProgressDialog.showDialog();
/*     */   }
/*     */   
/*     */   public void dismissLoading() {
/* 185 */     if (null != this.ccbProgressDialog) {
/* 186 */       this.ccbProgressDialog.disDialog();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
/* 193 */     Bitmap image = null;
/* 194 */     AssetManager am = ctx.getAssets();
/*     */     try {
/* 196 */       InputStream input = am.open(fileName);
/* 197 */       image = BitmapFactory.decodeStream(input);
/* 198 */       input.close();
/* 199 */     } catch (IOException e) {
/* 200 */       CcbSdkLogUtil.d("---读取assets文件夹的图片异常---" + e.getMessage());
/* 201 */       image = null;
/*     */     } 
/* 203 */     return image;
/*     */   }
/*     */   
/*     */   public Drawable loadImageFromAsserts(Context ctx, String fileName) {
/* 207 */     Drawable drawable = null;
/*     */     try {
/* 209 */       InputStream input = ctx.getAssets().open(fileName);
/* 210 */       drawable = Drawable.createFromStream(input, null);
/* 211 */       input.close();
/* 212 */     } catch (Exception e) {
/* 213 */       CcbSdkLogUtil.d("---读取assets文件夹的图片异常---" + e.getMessage());
/* 214 */       e.printStackTrace();
/* 215 */       drawable = null;
/*     */     } 
/* 217 */     return drawable;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDimensionToDip(int num, DisplayMetrics dm) {
/* 222 */     return (int)TypedValue.applyDimension(1, num, dm);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuccess(JSONObject jsonobj) {
/* 227 */     boolean flag = false;
/*     */     try {
/* 229 */       String success = jsonobj.getString("SUCCESS");
/* 230 */       if ("true".equalsIgnoreCase(success)) {
/* 231 */         if ((jsonobj.has("ERRCODE") && !"000000".equals(jsonobj.getString("ERRCODE"))) || (jsonobj
/* 232 */           .has("ERRORCODE") && !"000000".equals(jsonobj.getString("ERRORCODE")))) {
/* 233 */           flag = false;
/*     */         } else {
/* 235 */           flag = true;
/*     */         } 
/*     */       } else {
/* 238 */         flag = false;
/*     */       } 
/* 240 */     } catch (Exception e) {
/* 241 */       CcbSdkLogUtil.d("---解析JSON数据有误---" + e.getMessage());
/* 242 */       flag = false;
/*     */     } 
/* 244 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSdkCheckParam(String mParam) {
/* 251 */     String merchantId = NetUtil.getKeyWords(mParam, "MERCHANTID=");
/* 252 */     String branchId = NetUtil.getKeyWords(mParam, "BRANCHID=");
/* 253 */     String posId = NetUtil.getKeyWords(mParam, "POSID=");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     String paramReq = "CCB_IBSVersion=V6&PT_STYLE=3&TXCODE=SJSF01&APP_TYPE=1&SDK_VERSION=2.2.0&SYS_VERSION=" + getSysVersion() + "&REMARK1=" + merchantId + "&REMARK2=" + branchId + "&POSID=" + posId + "&ORDERID=" + NetUtil.getKeyWords(mParam, "ORDERID=");
/* 261 */     CcbSdkLogUtil.d("----SJSF01请求参数----", paramReq);
/* 262 */     return paramReq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wxPayResultReq(final Activity act, BaseResp resp) {
/* 272 */     CcbSdkLogUtil.i("---CcbPayUtil---", "---微信支付结果---" + resp.errCode);
/* 273 */     if (resp.getType() == 5) {
/* 274 */       if (0 == resp.errCode) {
/* 275 */         showLoadingDialog((Context)act);
/* 276 */         String pubparams = this.map.get("pubparam");
/*     */ 
/*     */ 
/*     */         
/* 280 */         String cxUrlParams = "CCB_IBSVersion=V6&PT_STYLE=3&TXCODE=SDK4CX&TYPE=2&MERCHANTID=" + NetUtil.getKeyWords(pubparams, "MERCHANTID=") + "&BRANCHID=" + NetUtil.getKeyWords(pubparams, "BRANCHID=") + "&ORDERID=" + NetUtil.getKeyWords(pubparams, "ORDERID=");
/* 281 */         CcbSdkLogUtil.i("CcbPayUtil", "---SDK4CX请求参数--- https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?" + cxUrlParams);
/* 282 */         NetUtil.httpSendPost("https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain", cxUrlParams, new NetUtil.SendCallBack()
/*     */             {
/*     */               public void success(String result)
/*     */               {
/* 286 */                 CcbPayUtil.this.dismissLoading();
/* 287 */                 CcbSdkLogUtil.i("CcbPayUtil", "---SDK4CX查询结果---" + result);
/* 288 */                 Map<String, String> resultMap = new HashMap<>();
/*     */                 try {
/* 290 */                   if (!TextUtils.isEmpty(result)) {
/*     */                     
/* 292 */                     int index = result.length() - 2;
/* 293 */                     char str = result.charAt(index);
/* 294 */                     if (',' == str)
/* 295 */                       result = result.substring(0, index) + result.substring(index + 1); 
/* 296 */                     JSONObject jsonObj = new JSONObject(result);
/* 297 */                     Iterator<String> iterator = jsonObj.keys();
/* 298 */                     String rskey = "", rsval = "";
/* 299 */                     while (iterator.hasNext()) {
/* 300 */                       rskey = iterator.next();
/* 301 */                       rsval = jsonObj.getString(rskey);
/* 302 */                       resultMap.put(rskey, rsval);
/*     */                     } 
/*     */                   } 
/* 305 */                   CcbPayUtil.this.sendSuccess(resultMap);
/* 306 */                   act.finish();
/* 307 */                 } catch (Exception e) {
/* 308 */                   CcbSdkLogUtil.i("---解析微信订单查询结果失败---", e.getMessage());
/* 309 */                   CcbPayUtil.this.onSendendResultMsg(1, "SDK4CX查询失败\n参考码:SDK4CX");
/* 310 */                   act.finish();
/*     */                 } 
/*     */               }
/*     */               
/*     */               public void failed(Exception e) {
/* 315 */                 CcbPayUtil.this.dismissLoading();
/* 316 */                 CcbSdkLogUtil.i("---微信订单查询失败---", e.getMessage());
/* 317 */                 CcbPayUtil.this.onSendendResultMsg(1, "SDK4CX查询失败\n参考码:SDK4CX");
/* 318 */                 act.finish();
/*     */               }
/*     */             });
/* 321 */       } else if (-2 == resp.errCode) {
/* 322 */         onSendendResultMsg(2, "取消支付");
/* 323 */         act.finish();
/*     */       } else {
/* 325 */         onSendendResultMsg(1, "支付失败\n 参考码errCode:" + resp.errCode);
/* 326 */         act.finish();
/*     */       } 
/*     */     } else {
/* 329 */       CcbSdkLogUtil.i("---CcbPayUtil---", "---微信：resp.getType()---" + resp.getType());
/* 330 */       act.finish();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSysVersion() {
/* 336 */     return Build.VERSION.RELEASE;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 342 */     private static final CcbPayUtil INSTANCE = new CcbPayUtil();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final CcbPayUtil getInstance() {
/* 349 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */   
/*     */   private CcbPayUtil() {}
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpa\\util\CcbPayUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */