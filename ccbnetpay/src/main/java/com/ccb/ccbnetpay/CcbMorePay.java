/*     */ package com.ccb.ccbnetpay;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.text.TextUtils;
/*     */ import android.view.View;
/*     */ import com.ccb.ccbnetpay.dialog.CcbSelectDialog;
/*     */ import com.ccb.ccbnetpay.message.CcbPayResultListener;
/*     */ import com.ccb.ccbnetpay.platform.CcbPayAliPlatform;
/*     */ import com.ccb.ccbnetpay.platform.CcbPayPlatform;
/*     */ import com.ccb.ccbnetpay.platform.CcbPayUnionPlatform;
/*     */ import com.ccb.ccbnetpay.platform.CcbPayWechatPlatform;
/*     */ import com.ccb.ccbnetpay.platform.Platform;
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
/*     */ public class CcbMorePay
/*     */ {
/*     */   private Activity mContext;
/*     */   private String mParam;
/*     */   private CcbPayResultListener listener;
/*  32 */   private String sdkResult = "";
/*     */   
/*     */   public void pay(Activity context, String param, CcbPayResultListener l) {
/*  35 */     this.mContext = context;
/*  36 */     this.mParam = param;
/*  37 */     this.listener = l;
/*     */     
/*  39 */     CcbPayUtil.getInstance().setListener(this.listener);
/*  40 */     CcbPayUtil.getInstance().setmContext(this.mContext);
/*     */     
/*  42 */     checkSdkVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSdkVersion() {
/*  51 */     CcbPayUtil.getInstance().setmContext(this.mContext);
/*  52 */     CcbPayUtil.getInstance().showLoadingDialog();
/*     */     
/*  54 */     NetUtil.httpSendPost("https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain", 
/*  55 */         CcbPayUtil.getInstance().getSdkCheckParam(this.mParam), new NetUtil.SendCallBack()
/*     */         {
/*     */           public void success(String result)
/*     */           {
/*  59 */             CcbSdkLogUtil.d("---SJSF01请求结果---" + result);
/*  60 */             if (TextUtils.isEmpty(result)) {
/*  61 */               CcbMorePay.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */               
/*     */               return;
/*     */             } 
/*  65 */             CcbPayUtil.getInstance().dismissLoadingDialog();
/*  66 */             CcbMorePay.this.sdkResult = result;
/*  67 */             CcbMorePay.this.showSelectPayDiaog();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void failed(Exception e) {
/*  73 */             CcbSdkLogUtil.d("---SJSF01请求异常---" + e.getMessage());
/*  74 */             CcbMorePay.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showSelectPayDiaog() {
/*  85 */     this.mContext.runOnUiThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*  88 */             String rateflag = "", unionflag = "";
/*     */             try {
/*  90 */               JSONObject jsonObj = new JSONObject(CcbMorePay.this.sdkResult);
/*  91 */               if (CcbPayUtil.getInstance().isSuccess(jsonObj)) {
/*  92 */                 if (jsonObj.has("RATEFLAG"))
/*  93 */                   rateflag = jsonObj.getString("RATEFLAG"); 
/*  94 */                 if (jsonObj.has("UnionFlag"))
/*  95 */                   unionflag = jsonObj.getString("UnionFlag"); 
/*  96 */                 CcbSdkLogUtil.d("---rateflag---" + rateflag + "---UnionFlag---" + unionflag);
/*  97 */                 String amount = NetUtil.getKeyWords(CcbMorePay.this.mParam, "PAYMENT=");
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 102 */                 final CcbSelectDialog dialog = (new CcbSelectDialog.Builder(CcbMorePay.this.mContext)).setAmount(amount).setRateflag(rateflag).setUnionflag(unionflag).build();
/* 103 */                 dialog.show();
/* 104 */                 dialog.setOkClick(new View.OnClickListener()
/*     */                     {
/*     */                       public void onClick(View v) {
/* 107 */                         dialog.dismiss();
/* 108 */                         switch (dialog.getCurrentSelect()) {
/*     */                           case 0:
/* 110 */                             CcbMorePay.this.payAppOrH5();
/*     */                             break;
/*     */                           case 1:
/* 113 */                             CcbMorePay.this.payAli();
/*     */                             break;
/*     */                           case 2:
/* 116 */                             CcbMorePay.this.payWeChat();
/*     */                             break;
/*     */                           case 3:
/* 119 */                             CcbMorePay.this.payUnion();
/*     */                             break;
/*     */                         } 
/*     */ 
/*     */                       
/*     */                       }
/*     */                     });
/*     */               } else {
/* 127 */                 CcbSdkLogUtil.d("---SJSF01请求有误---", jsonObj
/* 128 */                     .getString("ERRORMSG") + "\n参考码:SJSF01." + jsonObj.getString("ERRORCODE"));
/* 129 */                 CcbPayUtil.getInstance().onSendMsgSucc(jsonObj);
/*     */               } 
/* 131 */             } catch (Exception e) {
/* 132 */               CcbSdkLogUtil.d("---检查SDK版本有误---" + e.getMessage());
/* 133 */               CcbMorePay.this.onSendMsgDialog(1, "校验SDK版本有误\n参考码:SJSF01.\"\"");
/*     */               return;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void payAppOrH5() {
/* 146 */     Platform ccbPayPlatform = (new CcbPayPlatform.Builder()).setActivity(this.mContext).setListener(this.listener).setParams(this.mParam).setPayStyle(Platform.PayStyle.APP_OR_H5_PAY).build();
/* 147 */     ccbPayPlatform.pay(this.sdkResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void payWeChat() {
/* 156 */     Platform ccbPayWechatPlatform = (new CcbPayWechatPlatform.Builder()).setActivity(this.mContext).setListener(this.listener).setParams(this.mParam).build();
/* 157 */     ccbPayWechatPlatform.pay(this.sdkResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void payAli() {
/* 166 */     Platform ccbPayWechatPlatform = (new CcbPayAliPlatform.Builder()).setActivity(this.mContext).setListener(this.listener).setParams(this.mParam).build();
/* 167 */     ccbPayWechatPlatform.pay(this.sdkResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void payUnion() {
/* 176 */     Platform ccbPayPlatform = (new CcbPayUnionPlatform.Builder()).setActivity(this.mContext).setListener(this.listener).setParams(this.mParam).build();
/* 177 */     ccbPayPlatform.pay(this.sdkResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSendMsgDialog(int num, String msg) {
/* 187 */     CcbPayUtil.getInstance().dismissLoadingDialog();
/* 188 */     CcbPayUtil.getInstance().onSendendResultMsg(num, msg);
/*     */   }
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 193 */     private static final CcbMorePay INSTANCE = new CcbMorePay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final CcbMorePay getInstance() {
/* 200 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */   
/*     */   private CcbMorePay() {}
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\CcbMorePay.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */