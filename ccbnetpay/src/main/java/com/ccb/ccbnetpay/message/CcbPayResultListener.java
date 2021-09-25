package com.ccb.ccbnetpay.message;

import java.util.Map;

public interface CcbPayResultListener {
  public static final int PAY_SUCCESS = 0;
  
  public static final int PAY_FAILED = 1;
  
  public static final int PAY_CANCEL = 2;
  
  public static final String ANALYSIS_SDK_MSG = "校验SDK版本有误";
  
  public static final String CCB_PAY_PAGE_MSG = "建行支付页面加载失败";
  
  public static final String WECHAT_PAY_MSG_ERROR = "支付失败";
  
  void onSuccess(Map<String, String> paramMap);
  
  void onFailed(String paramString);
}


/* Location:              F:\ccbnetpay_v2.2.0(1).jar!\com\ccb\ccbnetpay\message\CcbPayResultListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */