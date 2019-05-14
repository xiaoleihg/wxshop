package com.huang.pojo;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
    private String realName;
    private String nickName;
    private String headImage;
    private String sex;
    private String phone;
    private String address;

}