const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderItems:[],
    userInfo:{},
    total:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var me =this;
    var items = [];
    var totalPrice = 0;
    wx.getStorage({
      key: 'orderItems',
      success: function(res) {
        items = res.data;
        //console.log(items);
        for (let i = 0; i < items.length; i++) {         // 循环列表得到每个数据
          totalPrice += items[i].count * items[i].price;   // 所有价格加起来  
        }
       // console.log(totalPrice);
        me.setData({
          orderItems:items,
          userInfo: app.getGlobalUserInfo(),
          total:totalPrice
        })
      },
    })
  },
  /*创建订单 */
  createOrder:function(e){
    var me = this;
    var itemList = me.data.orderItems;
    var formObject = e.detail.value;
    var address = formObject.address;
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/wxOrder/createOrder',
      method: "POST",
      data: {
        total: me.data.total,
        orderItems: me.data.orderItems,
        user: app.getGlobalUserInfo(),
        address: address
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        
        if(res.data.status==1){//订单创建成功
        //删除购物车中对应的商品
        var prodoucts=app.getGoodsIdList();
        var order = res.data.data;
        //console.log("order-----"+order);
          console.log("me.data.orderItems-----" + me.data.orderItems);        
          for (let i = 0; i < me.data.orderItems.length;i++){
          for (let j = 0; j < prodoucts.length; j++){
            if (me.data.orderItems[i].gid == prodoucts[j]){
              let indexs = prodoucts.indexOf(prodoucts[j]);
              prodoucts.splice(indexs, 1);
            }
          }
        }
        //console.log("prodoucts----" + prodoucts);
        wx.setStorageSync("goodsIdList", prodoucts);
        //console.log("删除后"+app.getGoodsIdList());
        //提示是否立即付款(模拟付款，点击确认即为付款成功)
        wx.showModal({
          title: '小提示',
          content: '订单创建成功，是否立即支付',
          showCancel: true,
          cancelText: '稍后付款',
          cancelColor: 'red',
          confirmText: '马上支付',
          confirmColor: 'green',
          success: function(res) {
            if(res.confirm){
              wx.request({
                url: serverUrl + '/wxOrder/changeStatus',
                method: "POST",
                data: {
                  oid:order.oid,
                  status:1
                },
                header: {
                  'content-type': 'application/json' // 默认值
                },
                success: function (result) {
                  if(result.data.status==1){
                    wx.showToast({
                      title: '付款成功，等待发货吧',
                      icon: 'none',
                      duration: 3000
                    })
                    wx.switchTab({
                      url: '../../cart/cart',
                    })
                  }
                }
              })
            }else if(res.cancel){
              wx.switchTab({
                url: '../../home/home',
              })
            }
          },
        })
        }else{//创建订单失败，返回失败信息
          wx.showToast({
            title: res.data.message,
            icon: 'none',
            duration: 3000
          })
      }
      }
    })
  },
  //前往详情页
  toDetails: function (e) {
    //console.log(e)
    wx.setStorage({
      key: 'goodsId',
      data: e.currentTarget.id,
    })
    wx.navigateTo({
      url: '../../details/details',
    })
  },
})