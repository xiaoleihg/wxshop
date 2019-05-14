// pages/home/home.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    swiperImg:[
      '../../image/home/swiper-0.jpg',
      '../../image/home/swiper-1.jpg'
    ],    
    goodsArray:[]   //商品数组
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var me = this;
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl +'/wxGoods/findAll',
      method:"POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(res){
        //console.log(res);
        if(res.data.status==1){
          me.setData({
            goodsArray:res.data.data
          })
        }else{
          wx.showToast({
            title: "服务器出现未知错误",
            icon: 'none',
            duration: 3000
          })
        }
      }
    })
  },
  /*前往详情页*/
  toDetails:function(e){
    wx.setStorage({
      key: 'goodsId',
      data: e.currentTarget.id,
    })
    wx.navigateTo({
      url: '../details/details',
    })
  },
  /*添加到购物车*/
  addToCart:function(e){
    var list = app.getGoodsIdList();
    //console.log("home----"+list);
    if (!list.includes(e.currentTarget.id)){
      app.setGoodsIdList(e.currentTarget.id);
      wx.showToast({
        title: '加入购物车成功',
        icon: 'success',
        duration: 1000
      });
    }else{
      wx.showToast({
        title: '购物车中已有该商品',
        icon: 'none',
        duration: 1000
      });
    }
  }

})