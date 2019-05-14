package com.huang.pojo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Integer orderItemId;
    private String goodsId;
    private Integer count;
    private Double subtotal;
    private String orderId;
    private Integer status;//订单状态
    private Goods goods;

}
