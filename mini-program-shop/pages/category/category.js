const app = getApp()
Page({
  data: {
    category: [],
    goodsArray: [],
    curIndex: 0,
    isScroll: false,
    toView: ''
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var me = this;
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/wxCategory/list',
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        var clist = res.data.data;
        if(res.data.status==1){
          me.setData({
            category: clist
          })
          wx.request({
            url: serverUrl + '/wxGoods/findByCategory?categoryId=' + clist[0].cid ,
            method: "GET",          
            header: {
              'content-type': 'application/json' // 默认值
            },
            success: function (res) {
              me.setData({
                goodsArray:res.data.data
              })
            }
          })
        }
      }
    })
  },
  findByCate(e) {
    var me = this;
    var serverUrl = app.serverUrl;
    //console.log(e);
    me.setData({
      toView: e.target.dataset.id,
      curIndex: e.target.dataset.index
    });
    //console.log(me.data.toView);
    wx.request({
      url: serverUrl + '/wxGoods/findByCategory?categoryId=' + e.target.dataset.id,
      method: "GET",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        me.setData({
          goodsArray: res.data.data
        })
      }
    })
  },
  //前往详情页
  toDetails: function (e) {
    wx.setStorage({
      key: 'goodsId',
      data: e.currentTarget.id,
    })
    wx.navigateTo({
      url: '../details/details',
    })
  },

})