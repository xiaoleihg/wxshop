package com.huang.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Setter
@Getter
@ToString
public class GoodsVo {
    private String gid;
    private String goodsName;
    private Double price;
    private Integer stock;//库存
    private Integer isValid;//状态：0--下架；1--上架（默认）
    private CommonsMultipartFile file;
    private String categoryId;
    private String info;
    private String businessId;
}
