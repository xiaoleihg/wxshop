package com.huang.pojo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private String gid;
    private String goodsName;
    private Double price;
    private Integer stock;//库存
    private Integer isValid;//状态：0--下架；1--上架（默认）
    private String goodsImg;
    private String info;
    private Category category;
    private Business business;
}
