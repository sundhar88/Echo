package com.ivara.aravi.echoshopping;

/**
 * Created by Aravi on 08-09-2017.
 */

public class EchoShopAdapter {

    private String USER_EMAIL, PHONE_NO, USER_TYPE, SELECTED_SHOP_TYPE, SELECTED_SELLER_TYPE, SELLER_NAME, SELLER_DP,SHOP_NAME;

    public EchoShopAdapter() {

    }

    public String getSHOP_NAME() {
        return SHOP_NAME;
    }

    public void setSHOP_NAME(String SHOP_NAME) {
        this.SHOP_NAME = SHOP_NAME;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public EchoShopAdapter(String USER_EMAIL, String PHONE_NO, String USER_TYPE, String SELECTED_SHOP_TYPE, String SELECTED_SELLER_TYPE, String SELLER_NAME, String SELLER_DP, String SHOP_NAME) {
        this.USER_EMAIL = USER_EMAIL;
        this.USER_TYPE = USER_TYPE;
        this.SELECTED_SHOP_TYPE = SELECTED_SHOP_TYPE;
        this.SELECTED_SELLER_TYPE = SELECTED_SELLER_TYPE;
        this.SELLER_NAME = SELLER_NAME;
        this.SELLER_DP = SELLER_DP;
        this.SHOP_NAME = SHOP_NAME;
        this.PHONE_NO = PHONE_NO;


    }

    public String getUSER_EMAIL() {
        return USER_EMAIL;
    }

    public void setUSER_EMAIL(String USER_EMAIL) {
        this.USER_EMAIL = USER_EMAIL;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getSELECTED_SHOP_TYPE() {
        return SELECTED_SHOP_TYPE;
    }

    public void setSELECTED_SHOP_TYPE(String SELECTED_SHOP_TYPE) {
        this.SELECTED_SHOP_TYPE = SELECTED_SHOP_TYPE;
    }

    public String getSELECTED_SELLER_TYPE() {
        return SELECTED_SELLER_TYPE;
    }

    public void setSELECTED_SELLER_TYPE(String SELECTED_SELLER_TYPE) {
        this.SELECTED_SELLER_TYPE = SELECTED_SELLER_TYPE;
    }

    public String getSELLER_NAME() {
        return SELLER_NAME;
    }

    public void setSELLER_NAME(String SELLER_NAME) {
        this.SELLER_NAME = SELLER_NAME;
    }

    public String getSELLER_DP() {
        return SELLER_DP;
    }

    public void setSELLER_DP(String SELLER_DP) {
        this.SELLER_DP = SELLER_DP;
    }
}
