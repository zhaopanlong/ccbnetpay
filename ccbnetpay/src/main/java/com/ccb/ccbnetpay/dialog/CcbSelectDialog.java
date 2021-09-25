/*     */ package com.ccb.ccbnetpay.dialog;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.GradientDrawable;
/*     */ import android.os.Bundle;
/*     */ import android.text.Html;
/*     */ import android.text.TextUtils;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.Window;
/*     */ import android.view.WindowManager;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.TextView;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CcbSelectDialog
/*     */   extends Dialog
/*     */ {
/*  31 */   private String AMOUNT = "金额:<font color='red' >%S</font>";
/*     */ 
/*     */   
/*  34 */   private String rateflag = ""; private String amount = ""; private String unionflag = "";
/*  35 */   private int currentSelect = 0;
/*     */   
/*  37 */   private Activity mContext = null;
/*     */   private Bitmap bitMapSelect;
/*  39 */   private DisplayMetrics dm = new DisplayMetrics();
/*     */   private TextView txtAmount;
/*     */   private LinearLayout ccbPayLayout; private ImageView ccbSelectImg; private LinearLayout aliPayLayout; private ImageView aliSelectImg; private LinearLayout wechatPayLayout; private ImageView wechatSelectImg; private LinearLayout unionPayLayout; private ImageView unionSelectImg; private LinearLayout btnLayout; private TextView txtCancle; private TextView txtOk; protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); this.bitMapSelect = CcbPayUtil.getInstance().getImageFromAssetsFile((Context)this.mContext, "images/ccbpay_select.png"); initDialog(); } public void initDialog() { requestWindowFeature(1); Window dialogWin = getWindow(); dialogWin.getDecorView().setBackgroundColor(0); WindowManager.LayoutParams lp = dialogWin.getAttributes(); WindowManager wm = this.mContext.getWindowManager(); wm.getDefaultDisplay().getMetrics(this.dm); lp.width = -1; lp.height = -2; dialogWin.setAttributes(lp); setCancelable(true); setCanceledOnTouchOutside(false);
/*  42 */     initLayout(); } public CcbSelectDialog(Builder builder) { super((Context)builder.mContext);
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
/* 237 */     this.ccbPayLayout = null;
/* 238 */     this.ccbSelectImg = null; this.mContext = builder.mContext; this.amount = builder.amount; this.rateflag = builder.rateflag; this.unionflag = builder.unionflag; }
/*     */   public void initLayout() { LinearLayout rootLayout = new LinearLayout((Context)this.mContext); rootLayout.setGravity(17); rootLayout.setOrientation(1); LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(-1, -2); rootParams.gravity = 17; rootLayout.setLayoutParams((ViewGroup.LayoutParams)rootParams); GradientDrawable gradient = new GradientDrawable(); gradient.setCornerRadius(20.0F); gradient.setColor(Color.parseColor("#ffffffff")); rootLayout.setBackgroundDrawable((Drawable)gradient); if (null == this.txtAmount) initAmount();  rootLayout.addView((View)this.txtAmount); rootLayout.addView(getLine()); if (null == this.ccbPayLayout) initCcbPayLayout();  rootLayout.addView((View)this.ccbPayLayout); rootLayout.addView(getLine()); if (!TextUtils.isEmpty(this.rateflag)) { char typeOne = this.rateflag.charAt(0); if ('1' == typeOne) { if (null == this.wechatPayLayout) initWechatPayLayout();  rootLayout.addView((View)this.wechatPayLayout); rootLayout.addView(getLine()); }  if (1 < this.rateflag.length()) { char typeTwo = this.rateflag.charAt(1); if ('1' == typeTwo) { if (null == this.aliPayLayout) initAliPayLayout();  rootLayout.addView((View)this.aliPayLayout); rootLayout.addView(getLine()); }  }  }  if ("1".equals(this.unionflag)) { if (null == this.unionPayLayout)
/*     */         initUnionPayLayout();  rootLayout.addView((View)this.unionPayLayout); rootLayout.addView(getLine()); }  if (null == this.btnLayout)
/* 241 */       initBtnLayout();  rootLayout.addView((View)this.btnLayout); setContentView((View)rootLayout); } private void initCcbPayLayout() { this.ccbPayLayout = new LinearLayout((Context)this.mContext);
/* 242 */     this.ccbSelectImg = new ImageView((Context)this.mContext);
/*     */     
/* 244 */     layoutViewOperate(this.ccbPayLayout, "images/ccbpay_longpay.png", "建行龙支付", "建行安全支付", this.ccbSelectImg, 0); } private void setSelect(int select) { this.currentSelect = select; switch (this.currentSelect) { case 0: this.ccbSelectImg.setVisibility(0); if (null != this.aliSelectImg) this.aliSelectImg.setVisibility(4);  if (null != this.wechatSelectImg) this.wechatSelectImg.setVisibility(4);  if (null != this.unionSelectImg)
/*     */           this.unionSelectImg.setVisibility(4);  break;case 1: this.aliSelectImg.setVisibility(0); this.ccbSelectImg.setVisibility(4); if (null != this.wechatSelectImg)
/*     */           this.wechatSelectImg.setVisibility(4);  if (null != this.unionSelectImg)
/*     */           this.unionSelectImg.setVisibility(4);  break;case 2: this.wechatSelectImg.setVisibility(0); if (null != this.aliSelectImg)
/*     */           this.aliSelectImg.setVisibility(4);  this.ccbSelectImg.setVisibility(4); if (null != this.unionSelectImg)
/*     */           this.unionSelectImg.setVisibility(4);  break;case 3: this.unionSelectImg.setVisibility(0); if (null != this.aliSelectImg)
/*     */           this.aliSelectImg.setVisibility(4);  this.ccbSelectImg.setVisibility(4); if (null != this.wechatSelectImg)
/*     */           this.wechatSelectImg.setVisibility(4);  break; }  }
/*     */   private void initAmount() { this.txtAmount = new TextView((Context)this.mContext); LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-1, -2); textParams.setMargins(CcbPayUtil.getInstance().getDimensionToDip(10, this.dm), CcbPayUtil.getInstance().getDimensionToDip(15, this.dm), 0, CcbPayUtil.getInstance().getDimensionToDip(15, this.dm)); this.txtAmount.setLayoutParams((ViewGroup.LayoutParams)textParams); this.txtAmount.setGravity(3); this.txtAmount.setTextColor(Color.parseColor("#000000")); this.txtAmount.setTextSize(2, 18.0F); this.txtAmount.setVisibility(0); this.txtAmount.setText((CharSequence)Html.fromHtml(this.AMOUNT.replace("%S", " " + this.amount + "元"))); }
/*     */   private View getLine() { View lineView = new View((Context)this.mContext); LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(-1, 1); lineView.setLayoutParams((ViewGroup.LayoutParams)lineParams); lineView.setBackgroundColor(Color.parseColor("#E4E4E4")); return lineView; }
/* 254 */   private void initAliPayLayout() { this.aliPayLayout = new LinearLayout((Context)this.mContext);
/* 255 */     this.aliSelectImg = new ImageView((Context)this.mContext);
/*     */     
/* 257 */     layoutViewOperate(this.aliPayLayout, "images/ccbpay_ali.png", "支付宝支付", "支付宝安全支付", this.aliSelectImg, 1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initWechatPayLayout() {
/* 267 */     this.wechatPayLayout = new LinearLayout((Context)this.mContext);
/* 268 */     this.wechatSelectImg = new ImageView((Context)this.mContext);
/*     */     
/* 270 */     layoutViewOperate(this.wechatPayLayout, "images/ccbpay_wechat.png", "微信支付", "微信安全支付", this.wechatSelectImg, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initUnionPayLayout() {
/* 280 */     this.unionPayLayout = new LinearLayout((Context)this.mContext);
/* 281 */     this.unionSelectImg = new ImageView((Context)this.mContext);
/*     */     
/* 283 */     layoutViewOperate(this.unionPayLayout, "images/ccbpay_unionpay.png", "银联支付", "银联安全支付", this.unionSelectImg, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initBtnLayout() {
/* 294 */     this.btnLayout = new LinearLayout((Context)this.mContext);
/*     */ 
/*     */ 
/*     */     
/* 298 */     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, CcbPayUtil.getInstance().getDimensionToDip(60, this.dm));
/* 299 */     this.btnLayout.setOrientation(0);
/* 300 */     this.btnLayout.setLayoutParams((ViewGroup.LayoutParams)params);
/*     */     
/* 302 */     this.txtCancle = new TextView((Context)this.mContext);
/* 303 */     LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(-1, -1);
/*     */     
/* 305 */     text2Params.weight = 1.0F;
/* 306 */     this.txtCancle.setLayoutParams((ViewGroup.LayoutParams)text2Params);
/* 307 */     this.txtCancle.setGravity(17);
/* 308 */     this.txtCancle.setTextColor(Color.parseColor("#0099ff"));
/* 309 */     this.txtCancle.setTextSize(2, 16.0F);
/*     */     
/* 311 */     this.txtCancle.setVisibility(0);
/* 312 */     this.txtCancle.setText("取消");
/* 313 */     this.btnLayout.addView((View)this.txtCancle);
/*     */     
/* 315 */     View lineView = new View((Context)this.mContext);
/* 316 */     LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(1, -1);
/*     */     
/* 318 */     lineView.setLayoutParams((ViewGroup.LayoutParams)lineParams);
/* 319 */     lineView.setBackgroundColor(Color.parseColor("#E4E4E4"));
/* 320 */     this.btnLayout.addView(lineView);
/*     */     
/* 322 */     this.txtOk = new TextView((Context)this.mContext);
/* 323 */     LinearLayout.LayoutParams textOkParams = new LinearLayout.LayoutParams(-1, -1);
/*     */     
/* 325 */     textOkParams.weight = 1.0F;
/* 326 */     this.txtOk.setLayoutParams((ViewGroup.LayoutParams)textOkParams);
/* 327 */     this.txtOk.setGravity(17);
/* 328 */     this.txtOk.setTextColor(Color.parseColor("#0099ff"));
/* 329 */     this.txtOk.setTextSize(2, 16.0F);
/*     */     
/* 331 */     this.txtOk.setVisibility(0);
/* 332 */     this.txtOk.setText("确定");
/* 333 */     this.btnLayout.addView((View)this.txtOk);
/*     */     
/* 335 */     this.txtCancle.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 338 */             CcbSelectDialog.this.dismiss();
/*     */           }
/*     */         });
/* 341 */     this.txtOk.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 344 */             CcbSelectDialog.this.dismiss();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void layoutViewOperate(LinearLayout layout, String imgPath, String titleTxt, String contentTxt, ImageView img, final int index) {
/* 354 */     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
/*     */ 
/*     */     
/* 357 */     layout.setOrientation(0);
/* 358 */     layout.setGravity(16);
/* 359 */     layout.setLayoutParams((ViewGroup.LayoutParams)params);
/*     */     
/* 361 */     ImageView imageView = new ImageView((Context)this.mContext);
/*     */ 
/*     */     
/* 364 */     LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(CcbPayUtil.getInstance().getDimensionToDip(32, this.dm), CcbPayUtil.getInstance().getDimensionToDip(32, this.dm));
/* 365 */     imageParams.setMargins(CcbPayUtil.getInstance().getDimensionToDip(13, this.dm), 
/* 366 */         CcbPayUtil.getInstance().getDimensionToDip(5, this.dm), 
/* 367 */         CcbPayUtil.getInstance().getDimensionToDip(13, this.dm), 
/* 368 */         CcbPayUtil.getInstance().getDimensionToDip(5, this.dm));
/* 369 */     imageView.setLayoutParams((ViewGroup.LayoutParams)imageParams);
/* 370 */     Bitmap bitMap = CcbPayUtil.getInstance().getImageFromAssetsFile((Context)this.mContext, imgPath);
/* 371 */     if (null != bitMap)
/* 372 */       imageView.setImageBitmap(bitMap); 
/* 373 */     layout.addView((View)imageView);
/*     */ 
/*     */     
/* 376 */     LinearLayout tipLayout = new LinearLayout((Context)this.mContext);
/* 377 */     LinearLayout.LayoutParams li1params = new LinearLayout.LayoutParams(-1, -2);
/*     */     
/* 379 */     li1params.weight = 1.0F;
/* 380 */     li1params.setMargins(0, CcbPayUtil.getInstance().getDimensionToDip(3, this.dm), 0, 
/* 381 */         CcbPayUtil.getInstance().getDimensionToDip(3, this.dm));
/* 382 */     tipLayout.setOrientation(1);
/* 383 */     tipLayout.setLayoutParams((ViewGroup.LayoutParams)li1params);
/* 384 */     TextView title = new TextView((Context)this.mContext);
/* 385 */     LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-2, -2);
/*     */     
/* 387 */     title.setLayoutParams((ViewGroup.LayoutParams)textParams);
/* 388 */     title.setGravity(3);
/* 389 */     title.setTextColor(Color.parseColor("#000000"));
/* 390 */     title.setTextSize(2, 16.0F);
/* 391 */     title.setVisibility(0);
/* 392 */     title.setText(titleTxt);
/*     */     
/* 394 */     TextView content = new TextView((Context)this.mContext);
/* 395 */     content.setLayoutParams((ViewGroup.LayoutParams)textParams);
/* 396 */     content.setGravity(3);
/* 397 */     content.setTextColor(Color.parseColor("#cccccc"));
/* 398 */     content.setTextSize(2, 12.0F);
/* 399 */     content.setVisibility(0);
/* 400 */     content.setText(contentTxt);
/*     */     
/* 402 */     tipLayout.addView((View)title);
/* 403 */     tipLayout.addView((View)content);
/* 404 */     layout.addView((View)tipLayout);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     LinearLayout.LayoutParams imageSelectParams = new LinearLayout.LayoutParams(CcbPayUtil.getInstance().getDimensionToDip(18, this.dm), CcbPayUtil.getInstance().getDimensionToDip(18, this.dm));
/* 410 */     imageSelectParams.setMargins(0, 0, 
/* 411 */         CcbPayUtil.getInstance().getDimensionToDip(20, this.dm), 0);
/* 412 */     imageSelectParams.gravity = 21;
/* 413 */     img.setLayoutParams((ViewGroup.LayoutParams)imageSelectParams);
/* 414 */     if (0 == index) {
/* 415 */       img.setVisibility(0);
/*     */     } else {
/* 417 */       img.setVisibility(4);
/*     */     } 
/* 419 */     if (null != this.bitMapSelect)
/* 420 */       img.setImageBitmap(this.bitMapSelect); 
/* 421 */     layout.addView((View)img);
/*     */     
/* 423 */     layout.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 426 */             if (index == CcbSelectDialog.this.currentSelect)
/*     */               return; 
/* 428 */             CcbSelectDialog.this.setSelect(index);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public int getCurrentSelect() {
/* 434 */     return this.currentSelect;
/*     */   }
/*     */   
/*     */   public void setOkClick(View.OnClickListener listener) {
/* 438 */     this.txtOk.setOnClickListener(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private Activity mContext;
/*     */     private String amount;
/*     */     private String rateflag;
/*     */     private String unionflag;
/*     */     
/*     */     public Builder(Activity mContext) {
/* 450 */       this.mContext = mContext;
/*     */     }
/*     */     
/*     */     public Builder setAmount(String amount) {
/* 454 */       this.amount = amount;
/* 455 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setRateflag(String rateflag) {
/* 459 */       this.rateflag = rateflag;
/* 460 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setUnionflag(String unionflag) {
/* 464 */       this.unionflag = unionflag;
/* 465 */       return this;
/*     */     }
/*     */     
/*     */     public CcbSelectDialog build() {
/* 469 */       return new CcbSelectDialog(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\dialog\CcbSelectDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */