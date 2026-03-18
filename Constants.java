package com.example.mobilebanking.utils;

public class Constants {
    public static final String PREF_NAME = "MobileBankingPref";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_MOBILE = "userMobile";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    
    // Transaction Types
    public static final String TYPE_SEND = "send";
    public static final String TYPE_CASH_OUT = "cashout";
    public static final String TYPE_RECHARGE = "recharge";
    
    // Payment Methods
    public static final String METHOD_BKASH = "bkash";
    public static final String METHOD_NAGAD = "nagad";
    public static final String METHOD_ROCKET = "rocket";
    public static final String METHOD_DUTCH_BANGLA = "dbbl";
    
    // Operators
    public static final String OPERATOR_GP = "gp";
    public static final String OPERATOR_ROBI = "robi";
    public static final String OPERATOR_BANGLALINK = "banglalink";
    public static final String OPERATOR_TELETALK = "teletalk";
    public static final String OPERATOR_AIRTEL = "airtel";
    
    // Transaction Status
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_FAILED = "failed";
    
    // Fees
    public static final double CASH_OUT_FEE_PERCENTAGE = 0.015; // 1.5%
    public static final double MIN_SEND_AMOUNT = 10;
    public static final double MIN_CASH_OUT_AMOUNT = 50;
    public static final double MIN_RECHARGE_AMOUNT = 20;
    
    // Firebase Collections
    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_TRANSACTIONS = "transactions";
}
