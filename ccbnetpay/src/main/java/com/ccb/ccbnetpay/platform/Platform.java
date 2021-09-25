/*     */ package com.ccb.ccbnetpay.platform;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.text.TextUtils;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ import com.ccb.ccbnetpay.util.NetUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Platform
/*     */ {
/*     */   protected String params;
/*     */   protected Activity mActivity;
/*     */   protected PayStyle payStyle;
/*     */   
/*     */   public void pay() {
/*  29 */     CcbSdkLogUtil.d("=====商户串====", this.params);
/*  30 */     showLoadingDialog();
/*     */     
/*  32 */     if (null == this.mActivity) {
/*  33 */       onSendMsgDialog(1, "请传入当前Activity\n参考码:\"\"");
/*     */       
/*     */       return;
/*     */     } 
/*  37 */     if (TextUtils.isEmpty(this.params)) {
/*  38 */       onSendMsgDialog(1, "商户串不能为空\n参考码:\"\"");
/*     */       
/*     */       return;
/*     */     } 
/*  42 */     checkSdkVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pay(String sdkResult) {
/*  48 */     CcbSdkLogUtil.d("=====综合支付====" + sdkResult);
/*  49 */     showLoadingDialog();
/*     */     
/*  51 */     if (null == this.mActivity) {
/*  52 */       onSendMsgDialog(1, "请传入当前Activity\n参考码:\"\"");
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     if (TextUtils.isEmpty(this.params)) {
/*  57 */       onSendMsgDialog(1, "商户串不能为空\n参考码:\"\"");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  64 */     displayCheckVersion(sdkResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkSdkVersion() {
/*  73 */     CcbSdkLogUtil.d("---SJSF01请求URL---", "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?" + 
/*  74 */         CcbPayUtil.getInstance().getSdkCheckParam(this.params));
/*  75 */     NetUtil.httpSendPost("https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain", 
/*  76 */         CcbPayUtil.getInstance().getSdkCheckParam(this.params), new NetUtil.SendCallBack()
/*     */         {
/*     */           public void success(String result)
/*     */           {
/*  80 */             CcbSdkLogUtil.d("---SJSF01请求结果---" + result);
/*  81 */             if (TextUtils.isEmpty(result)) {
/*  82 */               Platform.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */ 
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/*  89 */               Platform.this.displayCheckVersion(result);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void failed(Exception e) {
/*  96 */             CcbSdkLogUtil.d("---SJSF01请求异常---" + e.getMessage());
/*  97 */             Platform.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ccbFzReq(final String result) {
/*     */     try {
/* 111 */       JSONObject jsonObj = new JSONObject(result);
/* 112 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/* 113 */         String urlPubData = jsonObj.getString("URLPUBDATA");
/* 114 */         CcbSdkLogUtil.i("--解析sdkCheckRs--：", jsonObj
/* 115 */             .getString("URLPATH") + "--" + urlPubData);
/* 116 */         String httpPath = "https://ibsbjstar.ccb.com.cn/" + jsonObj.getString("URLPATH");
/* 117 */         String fzparams = "", keyVal = NetUtil.getKeyWords(this.params, "TXCODE=");
/* 118 */         if (!TextUtils.isEmpty(keyVal))
/* 119 */           fzparams = this.params.replace(keyVal, "SDK4FZ"); 
/* 120 */         fzparams = urlPubData + "&" + fzparams;
/* 121 */         CcbSdkLogUtil.i("---SDK4FZ请求参数---", fzparams);
/* 122 */         NetUtil.httpSendPost(httpPath, fzparams, new NetUtil.SendCallBack()
/*     */             {
/*     */               public void success(String fzrs)
/*     */               {
/* 126 */                 CcbSdkLogUtil.d("---SDK4FZ请求结果---" + fzrs);
/*     */                 try {
/* 128 */                   JSONObject fzjson = new JSONObject(fzrs);
/* 129 */                   if (CcbPayUtil.getInstance().isSuccess(fzjson)) {
/* 130 */                     Platform.this.displayCheckVersion(result);
/*     */                   } else {
/* 132 */                     CcbPayUtil.getInstance().onSendMsgSucc(fzjson);
/*     */                   } 
/* 134 */                 } catch (Exception e) {
/* 135 */                   CcbSdkLogUtil.d("---SDK4FZ请求异常---" + e.getMessage());
/* 136 */                   Platform.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SDK4FZ.\"\"");
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public void failed(Exception e) {
/* 143 */                 CcbSdkLogUtil.d("---SDK4FZ请求异常---" + e.getMessage());
/* 144 */                 Platform.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SDK4FZ.\"\"");
/*     */               }
/*     */             });
/*     */       } else {
/*     */         
/* 149 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */       } 
/* 151 */     } catch (Exception e) {
/* 152 */       CcbSdkLogUtil.d("---校验sdk版本结果信息异常---", e.getMessage());
/* 153 */       onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void displayCheckVersion(String result) {
/* 164 */     CcbPayUtil.getInstance().putData("pubparam", this.params);
/*     */     try {
/* 166 */       JSONObject jsonObj = new JSONObject(result);
/* 167 */       if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/* 168 */         String urlPubData = jsonObj.getString("URLPUBDATA");
/* 169 */         CcbSdkLogUtil.i("---解析sdkCheckRs---", jsonObj
/* 170 */             .getString("URLPATH") + "--" + urlPubData);
/* 171 */         String httpPath = "https://ibsbjstar.ccb.com.cn/" + jsonObj.getString("URLPATH");
/* 172 */         String urlParam = "", keyVal = NetUtil.getKeyWords(this.params, "TXCODE=");
/* 173 */         if (PayStyle.APP_PAY == this.payStyle) {
/*     */           
/* 175 */           urlParam = getAppWechatAliParam(urlPubData, CcbPayUtil.getInstance().getSysVersion());
/* 176 */           if (!TextUtils.isEmpty(keyVal))
/* 177 */             urlParam = urlParam.replace(keyVal, "SDK001"); 
/* 178 */           CcbSdkLogUtil.i("---组装新的跳转龙支付App的请求参数---", httpPath + "?" + urlParam);
/* 179 */           jumpAppPay(httpPath, urlParam);
/*     */         }
/* 181 */         else if (PayStyle.H5_PAY == this.payStyle) {
/*     */           
/* 183 */           urlParam = httpPath + "?" + this.params;
/* 184 */           CcbSdkLogUtil.d("---组装新的跳转龙支付H5的url---", urlParam);
/* 185 */           jumpH5Pay(urlParam);
/*     */         }
/* 187 */         else if (PayStyle.WECHAT_PAY == this.payStyle) {
/*     */           
/* 189 */           jmupWechatPay(httpPath, urlPubData);
/*     */         
/*     */         }
/* 192 */         else if (PayStyle.ALI_PAY == this.payStyle) {
/*     */ 
/*     */           
/* 195 */           urlParam = getAppWechatAliParam(urlPubData, CcbPayUtil.getInstance().getSysVersion());
/* 196 */           if (!TextUtils.isEmpty(keyVal))
/* 197 */             urlParam = urlParam.replace(keyVal, "SDK4AL"); 
/* 198 */           CcbSdkLogUtil.i("---跳转支付宝支付页面的url---", httpPath + "?" + urlParam);
/* 199 */           getAlipayUrl(httpPath, urlParam);
/*     */         }
/* 201 */         else if (PayStyle.UNION_PAY == this.payStyle) {
/*     */           
/* 203 */           urlParam = httpPath + "?" + urlPubData + "&" + this.params;
/* 204 */           if (!TextUtils.isEmpty(keyVal))
/* 205 */             urlParam = urlParam.replace(keyVal, "SDK4YL"); 
/* 206 */           CcbSdkLogUtil.i("---组装新的跳转银联支付的url---", urlParam);
/* 207 */           jumpUnionPay(urlParam);
/*     */         } 
/*     */       } else {
/*     */         
/* 211 */         CcbSdkLogUtil.d(jsonObj.getString("ERRORCODE") + "---解析sdk版本结果信息有误---" + jsonObj
/* 212 */             .getString("ERRORMSG"));
/* 213 */         CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */       } 
/* 215 */     } catch (Exception e) {
/* 216 */       CcbSdkLogUtil.d("---校验sdk版本结果信息异常---", e.getMessage());
/* 217 */       onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jmupWechatPay(String httpUrl, String pubData) {
/* 224 */     String sdkWxParam = getAppWechatAliParam(pubData, CcbPayUtil.getInstance().getSysVersion());
/* 225 */     String txcode = NetUtil.getKeyWords(this.params, "TXCODE=");
/* 226 */     if (!TextUtils.isEmpty(txcode))
/* 227 */       sdkWxParam = sdkWxParam.replace(txcode, "SDKWX1"); 
/* 228 */     sdkWxParam = sdkWxParam + "&WXAPP=1&SUB_OPENID=&TRADE_TYPE=APP";
/* 229 */     CcbSdkLogUtil.i("---组装新的跳转微信支付的请求参数---", sdkWxParam);
/* 230 */     NetUtil.httpSendPost(httpUrl, sdkWxParam, new NetUtil.SendCallBack()
/*     */         {
/*     */           public void success(String result)
/*     */           {
/* 234 */             CcbSdkLogUtil.d("---SDKWX1请求结果---" + result);
/* 235 */             if (TextUtils.isEmpty(result)) {
/* 236 */               CcbSdkLogUtil.i("---跳转微信支付页面失败---", result);
/* 237 */               Platform.this.onSendMsgDialog(1, "支付失败\n参考码:SDKWX1.请求结果为空");
/*     */               
/*     */               return;
/*     */             } 
/* 241 */             Platform.this.displayJumpWeChat(result);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void failed(Exception e) {
/* 247 */             CcbSdkLogUtil.d("---SDKWX1请求异常---" + e.getMessage());
/* 248 */             Platform.this.onSendMsgDialog(1, "支付失败\n参考码:SDKWX1.\"\"");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void getAlipayUrl(String httpUrl, String paramsMap) {
/* 256 */     NetUtil.httpSendPost(httpUrl, paramsMap, new NetUtil.SendCallBack()
/*     */         {
/*     */           public void success(String result)
/*     */           {
/* 260 */             CcbSdkLogUtil.d("---SDK4AL请求结果---" + result);
/* 261 */             if (TextUtils.isEmpty(result)) {
/* 262 */               CcbSdkLogUtil.i("---跳转支付宝支付页面失败---", result);
/* 263 */               Platform.this.onSendMsgDialog(1, "跳转支付宝支付页面失败\n参考码:SDK4AL.请求结果为空");
/*     */             } else {
/*     */               
/* 266 */               Platform.this.jumpAliPay(result);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void failed(Exception e) {
/* 272 */             CcbSdkLogUtil.d("---SDK4AL请求异常---" + e.getMessage());
/* 273 */             Platform.this.onSendMsgDialog(1, "跳转支付宝支付页面失败\n参考码:SDK4AL");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAppWechatAliParam(String pubData, String sysVer) {
/* 281 */     return pubData + "&" + this.params + "&APP_TYPE=" + "1" + "&SDK_VERSION=" + "2.2.0" + "&SYS_VERSION=" + sysVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void freezeRight(String path, String pubData) {
/* 292 */     String cardParams = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     String sdkDjParams = pubData + "&BRANCHID=" + NetUtil.getKeyWords(this.params, "BRANCHID=") + "&Ed_Crd_Prty_Idr_CD=" + NetUtil.getKeyWords(this.params, "MERCHANTID=") + "&Cntrprt_ID=" + NetUtil.getKeyWords(this.params, "POSID=") + "&OnLn_Py_Txn_Ordr_ID=" + NetUtil.getKeyWords(this.params, "ORDERID=") + "&Ordr_Amt=" + NetUtil.getKeyWords(this.params, "price=") + "&Scn_Idr=CFT&Bsn_Scn_Cd=1b&TXCODE=SDK4DJ" + "&APP_TYPE=" + "1" + "&SDK_VERSION=" + "2.2.0" + "&SYS_VERSION=" + CcbPayUtil.getInstance().getSysVersion();
/* 301 */     String cardType = NetUtil.getKeyWords(this.params, "cardtype=");
/* 302 */     if (TextUtils.isEmpty(cardType)) {
/* 303 */       jmupWechatPay(path, pubData);
/*     */     } else {
/* 305 */       if (cardType.equals("1")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 310 */         cardParams = "&DcCp_Nm=" + NetUtil.getKeyWords(this.params, "DcCp_Nm=") + "&IdCst_ID=" + NetUtil.getKeyWords(this.params, "IdCst_ID=") + "&MblPh_No=" + NetUtil.getKeyWords(this.params, "MblPh_No=") + "&Cyc_Pref_Amt=" + NetUtil.getKeyWords(this.params, "Cyc_Pref_Amt=") + "&OnlnPcsgInd_1_Bmp_ECD=000001&Cst_AccNo=";
/*     */       }
/* 312 */       else if (cardType.equals("2")) {
/*     */ 
/*     */ 
/*     */         
/* 316 */         cardParams = "&OnlnPcsgInd_1_Bmp_ECD=000010&Cptl_AccNo=" + NetUtil.getKeyWords(this.params, "Cptl_AccNo=") + "&Aply_TxnAmt=" + NetUtil.getKeyWords(this.params, "Aply_TxnAmt=") + "&TxnAmt=" + NetUtil.getKeyWords(this.params, "TxnAmt=") + "&Frz_Tm=013000&Rvl_Rcrd_Num_1=1";
/*     */       } 
/*     */       
/* 319 */       sdkDjParams = sdkDjParams + cardParams;
/* 320 */       CcbSdkLogUtil.i("---SDK4DJ接口请求URL---", path + "?" + sdkDjParams);
/* 321 */       NetUtil.httpSendPost(path, sdkDjParams, new NetUtil.SendCallBack()
/*     */           {
/*     */             public void success(String result)
/*     */             {
/* 325 */               CcbSdkLogUtil.i("---SDK4DJ请求结果---" + result);
/* 326 */               if (TextUtils.isEmpty(result)) {
/* 327 */                 Platform.this.onSendMsgDialog(1, "SDK4DJ请求结果为空");
/*     */               } else {
/*     */                 try {
/* 330 */                   JSONObject jsonObj = new JSONObject(result);
/* 331 */                   if (!CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/*     */ 
/*     */                     
/* 334 */                     CcbSdkLogUtil.i("---解析SDK4DJ结果信息有误---ERRORCODE：" + jsonObj.getString("ERRORCODE") + "---ERRORMSG：" + jsonObj
/* 335 */                         .getString("ERRORMSG"));
/* 336 */                     CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */                   } 
/* 338 */                 } catch (Exception e) {
/* 339 */                   CcbSdkLogUtil.i("---解析SDK4DJ结果信息有误---", e.getMessage());
/* 340 */                   Platform.this.onSendMsgDialog(1, "解析SDK4DJ结果信息有误\n参考码:SDK4DJ");
/*     */                 } 
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void failed(Exception e) {
/* 348 */               CcbSdkLogUtil.i("---SDK4DJ请求异常---" + e.getMessage());
/* 349 */               Platform.this.onSendMsgDialog(1, "SDK4DJ请求失败");
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jumpAppPay(String httpUrl, String paramsMap) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jumpH5Pay(String newurls) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jumpAliPay(String newurls) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayJumpWeChat(String newurls) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jumpUnionPay(String newurls) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void authorize() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSendMsgDialog(int num, String msg) {
/* 387 */     dismissLoadingDialog();
/* 388 */     CcbPayUtil.getInstance().onSendendResultMsg(num, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showLoadingDialog() {
/* 395 */     CcbPayUtil.getInstance().showLoadingDialog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dismissLoadingDialog() {
/* 402 */     CcbPayUtil.getInstance().dismissLoadingDialog();
/*     */   }
/*     */   
/*     */   public enum PayStyle {
/* 406 */     APP_PAY, H5_PAY, APP_OR_H5_PAY, WECHAT_PAY, ALI_PAY, UNION_PAY;
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\platform\Platform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */