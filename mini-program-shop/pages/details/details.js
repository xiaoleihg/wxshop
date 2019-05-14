// pages/details/details.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goods:{},
    goodsId:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function () {
    var me = this;
    var serverUrl = app.serverUrl;
    wx.getStorage({
      key: 'goodsId',
      success: function(res) {
        me.setData({
          goodsId: res.data
        })
        wx.request({
          url: serverUrl + '/wxGoods/findById?id=' + res.data,
          method: "GET",
          header: {
            'content-type': 'application/json' // 默认值
          },
          success(res) {
            if (res.data.status == 1) {
              me.setData({
                goods: res.data.data
              })
            } else {
              wx.showToast({
                title: "服务器出现未知错误",
                icon: 'none',
                duration: 3000
              })
            }
          }
        })
      },
    })
    wx.removeStorage({
      key: 'goodsId',
      success: function(res) {},
    })
  },
  bindToHome:function(){
    wx.switchTab({
      url: '../home/home',
    })
  },
  bindToCart: function (e) {
    var me = this;
    //console.log(me.data);
    var list = app.getGoodsIdList();
    //console.log("home----"+list);
    if (!list.includes(e.currentTarget.id)) {
      app.setGoodsIdList(e.currentTarget.id);
      wx.showToast({
        title: '加入购物车成功',
        icon: 'success',
        duration: 1000
      });
    } else {
      wx.showToast({
        title: '购物车中已有该商品',
        icon: 'none',
        duration: 1000
      });
    }
  },
  intoStore:function(){
    var me = this;
    var serverUrl = app.serverUrl;
    var businessId = me.data.goods.business.id;
    console.log(businessId);
    wx.setStorage({
      key: 'businessId',
      data: businessId,
    })
    wx.navigateTo({
      url: '../store/store',
    })
  }
  // bindToPay:function(){
  //   var me = this;
  //   var paylist = [];
  //   paylist.push(me.data.goods);
  //   console.log(paylist);
  //   wx.setStorage({
  //     key: 'orderItems',
  //     data: paylist,
  //   })
  //   wx.navigateTo({
  //     url: '../order/createOrderFromCart/orderToCreate',
  //   })
  // }
  
})