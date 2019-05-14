package com.huang.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String oid;//订单编号
    private Double total;//总价
    private String address;//收货地址
    @DateTimeFormat(pattern = "yyyy-MM-DD hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-DD hh:mm:ss")
    private Date createTime;//创建时间
    @DateTimeFormat(pattern = "yyyy-MM-DD hh:mm:ss")
    private Date endTime;//完成时间;
    private User user;//所属用户

    private List<OrderItem> orderItemList;//当前订单下所有条目

}
