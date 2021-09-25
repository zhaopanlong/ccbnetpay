/*     */ package com.ccb.ccbnetpay.dialog;
/*     */ 
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.drawable.AnimationDrawable;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.GradientDrawable;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.Window;
/*     */ import android.view.WindowManager;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.TextView;
/*     */ import com.ccb.ccbnetpay.util.CcbPayUtil;
/*     */ import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCBProgressDialog
/*     */   extends Dialog
/*     */ {
/*  31 */   private TextView textView = null;
/*  32 */   private ImageView imageView = null;
/*  33 */   private AnimationDrawable spinner = null;
/*  34 */   private Context mContext = null;
/*     */   
/*  36 */   private DisplayMetrics dm = new DisplayMetrics();
/*     */   
/*     */   public CCBProgressDialog(Context mContext) {
/*  39 */     super(mContext);
/*  40 */     this.mContext = mContext;
/*  41 */     initDialog();
/*     */   }
/*     */   
/*     */   public CCBProgressDialog(Context mContext, int theme) {
/*  45 */     super(mContext, theme);
/*  46 */     this.mContext = mContext;
/*  47 */     initDialog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWindowFocusChanged(boolean hasFocus) {
/*  55 */     if (null != this.spinner) {
/*  56 */       this.spinner.start();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/*  63 */     if (message == null || message.length() == 0 || "".equals(message)) {
/*  64 */       this.textView.setVisibility(8);
/*  65 */       this.textView.setText("");
/*     */     } else {
/*  67 */       this.textView.setVisibility(0);
/*  68 */       this.textView.setText(message);
/*  69 */       this.textView.invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showDialog() {
/*  77 */     CcbSdkLogUtil.d("---弹出ProgressDialog---", "ProgressDialog状态：" + isShowing());
/*  78 */     if (!isShowing()) {
/*  79 */       show();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disDialog() {
/*  87 */     CcbSdkLogUtil.d("---关闭ProgressDialog---", "ProgressDialog状态：" + isShowing());
/*  88 */     if (isShowing()) {
/*  89 */       dismiss();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  97 */     WindowManager wm = (WindowManager)this.mContext.getSystemService("window");
/*     */     
/*  99 */     wm.getDefaultDisplay().getMetrics(this.dm);
/*     */     
/* 101 */     Window dialogWin = getWindow();
/*     */     
/* 103 */     dialogWin.requestFeature(1);
/*     */     
/* 105 */     dialogWin.getDecorView().setBackgroundColor(0);
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
/* 116 */     setCancelable(true);
/*     */ 
/*     */ 
/*     */     
/* 120 */     setCanceledOnTouchOutside(false);
/*     */     
/* 122 */     initLayout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initLayout() {
/* 130 */     LinearLayout rootLayout = new LinearLayout(this.mContext);
/*     */     
/* 132 */     rootLayout.setGravity(17);
/* 133 */     rootLayout.setOrientation(1);
/* 134 */     int dialogWid = CcbPayUtil.getInstance().getDimensionToDip(15, this.dm);
/* 135 */     rootLayout.setPadding(dialogWid, dialogWid, dialogWid, dialogWid);
/* 136 */     LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(-2, -2);
/*     */ 
/*     */     
/* 139 */     rootParams.gravity = 17;
/* 140 */     rootLayout.setLayoutParams((ViewGroup.LayoutParams)rootParams);
/* 141 */     GradientDrawable gradient = new GradientDrawable();
/*     */     
/* 143 */     gradient.setCornerRadius(20.0F);
/*     */ 
/*     */ 
/*     */     
/* 147 */     gradient.setColor(Color.parseColor("#88000000"));
/* 148 */     rootLayout.setBackgroundDrawable((Drawable)gradient);
/*     */     
/* 150 */     this.imageView = new ImageView(this.mContext);
/*     */ 
/*     */     
/* 153 */     LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(CcbPayUtil.getInstance().getDimensionToDip(32, this.dm), CcbPayUtil.getInstance().getDimensionToDip(32, this.dm));
/* 154 */     this.imageView.setLayoutParams((ViewGroup.LayoutParams)imageParams);
/* 155 */     Drawable drawable = null;
/* 156 */     this.spinner = new AnimationDrawable();
/* 157 */     for (int i = 0; i <= 11; i++) {
/*     */       try {
/* 159 */         drawable = CcbPayUtil.getInstance().loadImageFromAsserts(this.mContext, "images/progess_loading_" + i + ".png");
/* 160 */         if (drawable != null)
/* 161 */           this.spinner.addFrame(drawable, 100); 
/* 162 */       } catch (Exception e) {
/* 163 */         e.printStackTrace();
/* 164 */         CcbSdkLogUtil.d("---获取图片异常---", e.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     this.spinner.setOneShot(false);
/* 169 */     this.imageView.setImageDrawable((Drawable)this.spinner);
/*     */     
/* 171 */     this.textView = new TextView(this.mContext);
/* 172 */     LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-2, -2);
/*     */     
/* 174 */     textParams.setMargins(0, CcbPayUtil.getInstance().getDimensionToDip(5, this.dm), 0, 0);
/* 175 */     this.textView.setLayoutParams((ViewGroup.LayoutParams)textParams);
/* 176 */     this.textView.setGravity(17);
/* 177 */     this.textView.setTextColor(Color.parseColor("#ffffff"));
/* 178 */     this.textView.setTextSize(2, 12.0F);
/* 179 */     this.textView.setVisibility(8);
/* 180 */     rootLayout.addView((View)this.imageView);
/* 181 */     rootLayout.addView((View)this.textView);
/* 182 */     setContentView((View)rootLayout);
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\dialog\CCBProgressDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */