package com.huang.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class CartItem {
    private String gid;
    private String goodsName;
    private Double price;
    private Integer count;//购买的数量
    private Business business;
}
