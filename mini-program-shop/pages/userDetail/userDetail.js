// pages/userDetial/userDetail.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
      userInfo:{},
      headImg:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function (options) {
    var me = this;
    var user = app.getGlobalUserInfo();
    me.setData({
      userInfo:user,
      headImg:user.headImage
    })
  },
  /**
   * 用户信息修改
   */
  doUpdate:function(e){
    //console.log(e);
    var me = this;
    var formObject = e.detail.value;
    var name = formObject.username;
    var nickName = formObject.nickName;
    var realName = formObject.realName;
    var sex = formObject.sex;
    var phone = formObject.phone;
    var address = formObject.address;
    var serverUrl = app.serverUrl;
    // console.log(me.data.userInfo.id);
    // console.log(this.data.headImg);
    wx.uploadFile({
      url: serverUrl + '/uploadFace?id='+me.data.userInfo.id,
      filePath: this.data.headImg,
      name: 'file',
      success:function(res){
        console.log(res);
        if (res.data.status == 1) {
          // 图片上传成功
          wx.showToast({
            title: '图片上传成功',
            icon: 'success',
            duration: 2000
          });
        } else if (res.data.status == 2) {
          // 失败弹出框
          wx.showToast({
            title: res.data.message,
            icon: 'none',
            duration: 3000
          })
        }
      }
    })
    wx.request({
      url: serverUrl + '/change',
      method: "POST",
      data: {
        id: me.data.userInfo.id,
        name: name,
        nickName: nickName,
        realName: realName,
        sex: sex,
        phone: phone,
        address: address
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res);
        if (res.data.status == 1) {
          var user = res.data.data;
          // 修改成功
          wx.showToast({
            title: '修改成功',
            icon: 'success',
            duration: 2000
          });
          app.setGlobalUserInfo(user);
          me.setData({
            userInfo: user,
            headImg:user.headImage
          })
          // wx.switchTab({
          //   url: '../users/user',
          // })
        } else if (res.data.status == 2) {
          // 失败弹出框
          wx.showToast({
            title: res.data.message,
            icon: 'none',
            duration: 3000
          })
        }
      }
    })
    
  },
  toChooseImg:function(e){
    var me = this;
    wx.chooseImage({
      count:1,
      success: function(res) {
        console.log(res);
        const tempFilePaths = res.tempFilePaths;
        me.setData({
          headImg:tempFilePaths[0]
        })
      },
    })
  },
  goBack:function(e){
    wx.switchTab({
      url: '../users/user',
    })
  }
})