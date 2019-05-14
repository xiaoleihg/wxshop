const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    order:{},
    orderItem:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var me =this;
    var serverUrl = app.serverUrl;
    wx.getStorage({
      key: 'itemId',
      success: function(res) {
        wx.request({
          url: serverUrl + '/wxOrder/toDetails',
          method: "POST",
          data: {
            orderItemId:res.data
          },
          header: {
            'content-type': 'application/json' // 默认值
          },
          success: function (res) {
            console.log(res);
            me.setData({
              order:res.data.data.order,
              orderItem: res.data.data.orderItem,
            })
          }
        })
      },
    })
  },
  changeStatus: function (e) {
    var me = this;
    var serverUrl = app.serverUrl;
    //console.log(e.currentTarget.dataset.status);
    //console.log(e.currentTarget.dataset.id);
    var list = me.data.orderItem;
    //console.log(list)
    wx.request({
      url: serverUrl + '/wxOrder/changeItemStatus',
      method: "POST",
      data: {
        orderItemId: e.currentTarget.dataset.id,
        status: e.currentTarget.dataset.status
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        //console.log(res);
        if (res.data.status == 1) {
          list.status = e.currentTarget.dataset.status+1
          me.setData({
            orderItem: list
          })
        }
      }
    })
  },
    
})