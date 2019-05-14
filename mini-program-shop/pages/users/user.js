// pages/users/user.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    orderCateList:[
      {
        "orderIconUrl":'../../image/order-icon/order-all.png',
        "orderText":'全部',
        "status":-1
      },{
        "orderIconUrl": '../../image/order-icon/order-pay.png',
        "orderText": '待支付',
        "status": 0
      }, {
        "orderIconUrl": '../../image/order-icon/order-unsend.png',
        "orderText": '待发货',
        "status": 1
      },{
        "orderIconUrl": '../../image/order-icon/order-receving.png',
        "orderText": '待收货',
        "status": 2
      },{
        "orderIconUrl": '../../image/order-icon/order-evaluate.png',
        "orderText": '已完成',
        "status": 3
      }
    ],
    right:"../../image/common/right.png"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function (options) {
    var self = this;
    var user = app.getGlobalUserInfo();
    //console.log(user);   
    self.setData({
      userInfo:user
    })


   
  },
  toUserDetail:function(){
   wx.redirectTo({
     url: '../userDetail/userDetail',
   })
  },
  findByStatus:function(e){
    console.log(e.currentTarget.id);
    wx.setStorage({
      key: 'orderStatus',
      data: e.currentTarget.id,
    })
    wx.navigateTo({
      url: '../order/orderToFind/orderToFind',
    })
  },
  logout:function(e){
    wx.removeStorageSync("userInfo");
    wx.redirectTo({
      url: '../userLogin/userLogin',
    })
  }
})