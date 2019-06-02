package com.huang.web.front;

import com.alibaba.fastjson.JSONObject;
import com.huang.common.constant.CommonConstant;
import com.huang.common.utils.MD5Utils;
import com.huang.common.utils.ResponseResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import com.huang.pojo.User;
import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

@RestController("userController")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    public ResponseResult regist(@RequestBody User user) throws Exception {
        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(user.getName()) ||
                StringUtils.isBlank(user.getPassword())) {
            return ResponseResult.fail("用户名和密码不能为空");
        }
        // 2. 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getName());
        // 3. 保存用户，注册信息
        if (!usernameIsExist) {
            user.setNickName(user.getName());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setId( UUID.randomUUID().toString());
            user.setSex(CommonConstant.SEX_SECRET);
            user.setHeadImage("../../image/common/dsp.jpg");
            userService.saveUser(user);
            user.setPassword("");
        } else {
            return ResponseResult.fail("用户名已经存在，请换一个再试");
        }
        return ResponseResult.success(user);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) throws Exception {
        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPassword())) {
            return ResponseResult.fail("用户名和密码不能为空");
        }
        // 2. 判断符合条件的用户是否存在
        User userResult = userService.queryUserForLogin(user.getName(), MD5Utils.getMD5Str(user.getPassword()));
        //3.返回结果
        if (userResult!=null){
            userResult.setPassword("");
            return ResponseResult.success(userResult);
        }
        return ResponseResult.fail("账号或密码不正确，请重试！！！");
    }


    @PostMapping("/change")
    public ResponseResult change(@RequestBody User user) throws Exception {
        //System.out.println("传入"+user);
        if (StringUtils.isBlank(user.getNickName()) || StringUtils.isBlank(user.getPhone())) {
            return ResponseResult.fail("昵称和电话不能为空");
        }
        userService.updateUserInfo(user);
        User userResult = userService.queryUserInfo(user.getId());
        userResult.setPassword("");
        //System.out.println("传出"+userResult);
        return ResponseResult.success(userResult);
    }
    @PostMapping("/changePwd")
    public ResponseResult changePwd(@RequestBody String data) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        String newPassword = jsonObject.getString("newPassword");
        System.out.println(name+password+newPassword);
        if (StringUtils.isBlank(password) || StringUtils.isBlank(newPassword)) {
            return ResponseResult.fail("新旧密码不能为空");
        }
        if (password == newPassword){
            return ResponseResult.fail("新密码不能与原密码相同");
        }
        User userResult = userService.queryUserForLogin(name, MD5Utils.getMD5Str(password));
        System.out.println(userResult);
        if(userResult != null){
            userService.updatePassword(name,MD5Utils.getMD5Str(newPassword));
            return ResponseResult.success();
        }
        return ResponseResult.fail("原密码错误，请重试！！！");
    }


    @PostMapping("/uploadFace")
    public ResponseResult uploadFace(String id ,@RequestParam("file") MultipartFile[] files) throws Exception {
        if (StringUtils.isBlank(id)) {
            return ResponseResult.fail("用户id不能为空...");
        }
        // 文件保存的命名空间
        String fileSpace = "E:/upload/wxshopImg/HeadImage";
        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + id + "/face";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件上传的最终保存路径
                    String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();// 创建父文件夹
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return ResponseResult.fail("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        User user = new User();
        user.setId(id);
        user.setHeadImage("http://192.168.11.1/wxshopImg/HeadImage"+uploadPathDB);
        userService.updateUserInfo(user);

        return ResponseResult.success(user);
    }
}
