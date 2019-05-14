package com.huang.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;

@Setter
@Getter
@ToString
public class GoodsDto {
    private String gid;
    private String goodsName;
    private Double price;
    private Integer stock;//库存
    private Integer isValid;//状态：0--下架；1--上架（默认）
    private String categoryId;
    private String info;
    private String businessId;
    private InputStream inputStream; //文件的输入流
    private String fileName; //文件的名称
    private String uploadPath; //文件的上传路径
}
