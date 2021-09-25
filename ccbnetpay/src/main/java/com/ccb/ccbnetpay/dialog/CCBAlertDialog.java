/*     */ package com.ccb.ccbnetpay.dialog;
/*     */ 
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.res.ColorStateList;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.GradientDrawable;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.Window;
/*     */ import android.view.WindowManager;
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
/*     */ 
/*     */ public class CCBAlertDialog
/*     */   extends Dialog
/*     */ {
/*     */   public static final String TAG = "CCBAlertDialog";
/*  32 */   private Context context = null;
/*  33 */   private TextView btnView = null;
/*  34 */   private DisplayMetrics dm = null;
/*  35 */   private String msg = "";
/*     */   
/*     */   public CCBAlertDialog(Context context) {
/*  38 */     super(context);
/*  39 */     this.context = context;
/*     */   }
/*     */   
/*     */   public CCBAlertDialog(Context context, int theme) {
/*  43 */     super(context, theme);
/*  44 */     this.context = context;
/*     */   }
/*     */   
/*     */   public CCBAlertDialog(Context context, String msg) {
/*  48 */     super(context);
/*  49 */     this.context = context;
/*  50 */     this.msg = msg;
/*  51 */     createDialog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showDialog() {
/*  58 */     CcbSdkLogUtil.i("CCBAlertDialog", "---AlertDialog状态---" + isShowing());
/*  59 */     if (!isShowing()) {
/*  60 */       show();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disDialog() {
/*  67 */     CcbSdkLogUtil.i("CCBAlertDialog", "---AlertDialog状态---" + isShowing());
/*  68 */     if (isShowing()) {
/*  69 */       dismiss();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createDialog() {
/*  77 */     if (null == this.msg)
/*  78 */       this.msg = ""; 
/*  79 */     this.dm = this.context.getResources().getDisplayMetrics();
/*     */     
/*  81 */     Window dialogWindow = getWindow();
/*     */     
/*  83 */     dialogWindow.requestFeature(1);
/*     */     
/*  85 */     dialogWindow.getDecorView().setBackgroundColor(0);
/*     */     
/*  87 */     WindowManager.LayoutParams lp = dialogWindow.getAttributes();
/*     */     
/*  89 */     lp.dimAmount = 0.6F;
/*     */     
/*  91 */     lp.gravity = 17;
/*     */     
/*  93 */     lp.width = (int)(this.dm.widthPixels * 0.8D);
/*  94 */     lp.height = -2;
/*  95 */     dialogWindow.setAttributes(lp);
/*     */     
/*  97 */     setCancelable(true);
/*     */     
/*  99 */     initLayout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initLayout() {
/* 108 */     LinearLayout rootLayout = new LinearLayout(this.context);
/* 109 */     LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(-1, -2);
/*     */     
/* 111 */     rootLayout.setLayoutParams((ViewGroup.LayoutParams)rootParams);
/*     */     
/* 113 */     rootLayout.setGravity(17);
/* 114 */     rootLayout.setOrientation(1);
/*     */     
/* 116 */     GradientDrawable gradient = new GradientDrawable();
/*     */     
/* 118 */     gradient.setCornerRadius(12.0F);
/*     */ 
/*     */ 
/*     */     
/* 122 */     gradient.setColor(Color.parseColor("#ffffff"));
/* 123 */     rootLayout.setBackgroundDrawable((Drawable)gradient);
/*     */ 
/*     */ 
/*     */     
/* 127 */     TextView msgView = new TextView(this.context);
/* 128 */     LinearLayout.LayoutParams msgParams = new LinearLayout.LayoutParams(-2, -2);
/*     */     
/* 130 */     int txtWid = CcbPayUtil.getInstance().getDimensionToDip(40, this.dm);
/* 131 */     int txtLeft = CcbPayUtil.getInstance().getDimensionToDip(20, this.dm);
/* 132 */     msgParams.setMargins(txtLeft, txtWid, txtLeft, txtWid);
/* 133 */     msgView.setLayoutParams((ViewGroup.LayoutParams)msgParams);
/* 134 */     msgView.setGravity(17);
/* 135 */     msgView.setTextColor(Color.parseColor("#262626"));
/* 136 */     msgView.setTextSize(2, 16.0F);
/* 137 */     msgView.setText(this.msg);
/*     */ 
/*     */ 
/*     */     
/* 141 */     View lineView = new View(this.context);
/* 142 */     LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(-1, 1);
/*     */     
/* 144 */     lineView.setLayoutParams((ViewGroup.LayoutParams)lineParams);
/* 145 */     lineView.setBackgroundColor(Color.parseColor("#E4E4E4"));
/*     */ 
/*     */     
/* 148 */     this.btnView = new TextView(this.context);
/* 149 */     LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(-2, -2);
/*     */     
/* 151 */     int btnWid = CcbPayUtil.getInstance().getDimensionToDip(1, this.dm);
/* 152 */     btnParams.setMargins(0, 0, 0, btnWid);
/* 153 */     this.btnView.setLayoutParams((ViewGroup.LayoutParams)btnParams);
/* 154 */     int btnPid = CcbPayUtil.getInstance().getDimensionToDip(15, this.dm);
/* 155 */     this.btnView.setPadding(btnPid, btnPid, btnPid, btnPid);
/* 156 */     this.btnView.setGravity(17);
/*     */     
/* 158 */     this.btnView.setText("确定");
/* 159 */     this.btnView.setTextColor(createColorStateList(Color.parseColor("#262626"), 
/* 160 */           Color.parseColor("#003399"), Color.parseColor("#003399"), Color.parseColor("#262626")));
/* 161 */     this.btnView.setTextSize(2, 18.0F);
/*     */     
/* 163 */     this.btnView.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 166 */             CcbSdkLogUtil.i("CCBAlertDialog", "---onClick:点击确定---");
/* 167 */             CCBAlertDialog.this.disDialog();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 172 */     rootLayout.addView((View)msgView);
/* 173 */     rootLayout.addView(lineView);
/* 174 */     rootLayout.addView((View)this.btnView);
/* 175 */     setContentView((View)rootLayout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
/* 183 */     int[] colors = { pressed, focused, normal, focused, unable, normal };
/* 184 */     int[][] states = new int[6][];
/* 185 */     (new int[2])[0] = 16842910; (new int[2])[1] = 16842919; states[0] = new int[2];
/* 186 */     (new int[2])[0] = 16842910; (new int[2])[1] = 16842908; states[1] = new int[2];
/* 187 */     (new int[1])[0] = 16842910; states[2] = new int[1];
/* 188 */     (new int[1])[0] = 16842908; states[3] = new int[1];
/* 189 */     (new int[1])[0] = 16842909; states[4] = new int[1];
/* 190 */     states[5] = new int[0];
/* 191 */     ColorStateList colorList = new ColorStateList(states, colors);
/* 192 */     return colorList;
/*     */   }
/*     */ }


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\dialog\CCBAlertDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */