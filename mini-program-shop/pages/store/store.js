const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    business:{},
    goodsList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var me = this;
    var serverUrl = app.serverUrl;
    wx.getStorage({
      key: 'businessId',
      success: function(res) {
        wx.request({
          url: serverUrl + '/wxGoods/findByShop?businessId=' + res.data,
          method: "GET",
          header: {
            'content-type': 'application/json' // 默认值
          },
          success(res) {
            console.log(res);
            me.setData({
              goodsList: res.data.data,
              business:res.data.data[0].business
            })
          }
        })
      },
    })
  },
  /*前往详情页*/
  toDetails: function (e) {
    wx.setStorage({
      key: 'goodsId',
      data: e.currentTarget.id,
    })
    wx.navigateTo({
      url: '../details/details',
    })
  },
  /*添加到购物车*/
  addToCart: function (e) {
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
  }
})