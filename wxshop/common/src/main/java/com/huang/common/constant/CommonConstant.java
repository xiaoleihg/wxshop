package com.huang.common.constant;


public class CommonConstant {
    /**
     * 商品常量:1---上架，0--下架
     */
    public  static final Integer GOODS_VALID_UP=1;
    public  static final Integer GOODS_VALID_DOWN=0;

    /**
     * 用户性别常量
     */
    public  static final String SEX_MALE ="男";
    public  static final String SEX_FEMALE ="女";
    public  static final String SEX_SECRET ="保密";
    /**
     * 地址常量，是否为默认收货地址
    */
    private static Boolean IS_DEFAULT = true;
    private static Boolean NOT_DEFAULT = false;
    /**
     * 订单的状态：
     * 0：待付款；1：代发货；2：待收货；3：已完成
     */
    public  static Integer ORDER_STATUS_UNPAID = 0;
    public  static Integer ORDER_STATUS_UNSHIPPED = 1;
    public  static Integer ORDER_STATUS_UNRECIVED = 2;
    public  static Integer ORDER_STATUS_FINISHED= 3;
}
