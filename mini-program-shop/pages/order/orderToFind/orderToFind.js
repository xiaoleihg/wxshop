const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentStatus:'',
    orderItemList:[],
    orderStatus: [
      {
        "orderText": '全部',
        "status": -1
      }, {
        "orderText": '待支付',
        "status": 0
      }, {
        "orderText": '待发货',
        "status": 1
      }, {
        "orderText": '待收货',
        "status": 2
      }, {
        "orderText": '已完成',
        "status": 3
      }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var me = this;
    var status;
    var serverUrl = app.serverUrl;
    wx.getStorage({
      key: 'orderStatus',
      success: function (res) {
        status = res.data;
        console.log("------" + status);
        me.setData({
          currentStatus: res.data
        })
        wx.request({
          url: serverUrl + '/wxOrder/findByStatus',
          method: "POST",
          data: {
            userId: app.getGlobalUserInfo().id,
            status: status
          },
          header: {
            'content-type': 'application/json' // 默认值
          },
          success: function (res) {
            //console.log(res);
            if (res.data.status == 1) {
              me.setData({
                orderItemList: res.data.data
              })
            }
          }
        })
      },
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },
  findByStatus: function (e) {
    var me = this;
    var serverUrl = app.serverUrl;
    //console.log(e.currentTarget.dataset.status);
    me.setData({
      currentStatus: e.currentTarget.dataset.status
    })
    wx.request({
      url: serverUrl + '/wxOrder/findByStatus',
      method: "POST",
      data: {
        userId: app.getGlobalUserInfo().id,
        status: e.currentTarget.dataset.status
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        //console.log(res);
        if (res.data.status == 1) {
          me.setData({
            orderItemList: res.data.data
          })
        }
      }
    })
  },

  changeStatus:function(e){
    var me = this;
    var serverUrl = app.serverUrl;
    //console.log(e.currentTarget.dataset.status);
    //console.log(e.currentTarget.dataset.id);
    var list = me.data.orderItemList;
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
          list.map(item => (item.orderItemId == e.currentTarget.dataset.id ? item.status = e.currentTarget.dataset.status+1 : item
            ));
          me.setData({
            orderItemList: list
          })
        }
      }
    })
  },
  toOrderDetail:function(e){
    console.log(e.currentTarget.id);
    wx.setStorage({
      key: 'itemId',
      data: e.currentTarget.id,
    })
    wx.navigateTo({
      url: '../orderDetail/orderDetail',
    })
  }
})