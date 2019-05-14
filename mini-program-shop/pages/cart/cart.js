const app = getApp();
Page({
  data: {
    carts: [],               // 购物车列表
    hasList: false,          // 列表是否有数据
    totalPrice: 0,           // 总价，初始为0
    selectAllStatus: false    // 全选状态，默认全不选
  },
  onShow:function() {
    var me = this;
    var list = app.getGoodsIdList();
    var serverUrl = app.serverUrl;
    //console.log(list);
    if(list!=null && list.length>0){
      wx.request({
        url: serverUrl + '/wxGoods/findFromCart',
        method: "POST",
        data: app.getGoodsIdList(),
        header: {
          'content-type': 'application/json' // 默认值
        },
        success(res) {
          //console.log(res.data.data);
          //为返回的数组里的所有对象添加selected和num属性，并赋初值为false和1
          res.data.data.map(item => (item.selected ? item : Object.assign(item, { selected:false}),
            item.count ? item : Object.assign(item, { count: 1 })
          ));
          //console.log(res.data.data);
          me.setData({
            hasList:true,
            carts: res.data.data
          })
        }
      })      
    }else{
      me.setData({
        hasList: false,
        carts: []
      })
    }
    this.getTotalPrice();
  },
  /**
   * 当前商品选中事件
   */
  selectList(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    const selected = carts[index].selected;
    carts[index].selected = !selected;
    this.setData({
      carts: carts
    });
    this.getTotalPrice();
  },

  /**
   * 删除购物车当前商品
   */
  deleteList(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    carts.splice(index, 1);
    app.outGoodsIdList(index);
    this.setData({
      carts: carts
    });
    if (carts.length<1) {
      this.setData({
        hasList: false
      });
    } else {
      this.getTotalPrice();
    }
  },

  /**
   * 购物车全选事件
   */
  selectAll(e) {
    let selectAllStatus = this.data.selectAllStatus;
    selectAllStatus = !selectAllStatus;
    let carts = this.data.carts;
    for (let i = 0; i < carts.length; i++) {
      carts[i].selected = selectAllStatus;
    }
    this.setData({
      selectAllStatus: selectAllStatus,
      carts: carts
    });
    this.getTotalPrice();
  },

  /**
   * 绑定加数量事件
   */
  addCount(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let num = carts[index].count;
    num = num + 1;
    carts[index].count = num;
    this.setData({
      carts: carts
    });
    this.getTotalPrice();
  },

  /**
   * 绑定减数量事件
   */
  minusCount(e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let num = carts[index].count;
    if (num <= 1) {
      wx.showToast({
        title: '再减就没有了',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    num = num - 1;
    carts[index].count = num;
    this.setData({
      carts: carts
    });
    this.getTotalPrice();
  },

  /**
   * 计算总价
   */
  getTotalPrice() {
    let carts = this.data.carts;                  // 获取购物车列表
    let total = 0;
    for (let i = 0; i < carts.length; i++) {         // 循环列表得到每个数据
      if (carts[i].selected) {                     // 判断选中才会计算价格
        total += carts[i].count * carts[i].price;   // 所有价格加起来
      }
    }
    this.setData({                                // 最后赋值到data中渲染到页面     
      totalPrice: total.toFixed(2)
    });
  },
  /* 前往结算*/
  toPay:function(e){
    var me = this;
    var paylist = [];
    //console.log(me.data.carts);
    me.data.carts.map(item => (item.selected == false ? item : paylist.push(item)));
    if (paylist==null||paylist.length<1){
      wx.showToast({
        title: '请先选择要购买的商品',
        icon: 'none',
        duration: 2000
      })
    }else{
      paylist.map(item => (delete item.selected,
        delete item.category,
        //delete item.goodsImg ,
        delete item.info,
        delete item.isValid,
        delete item.stock
        ));
      wx.setStorage({
        key: 'orderItems',
        data: paylist,
      })
      wx.navigateTo({
        url: '../order/createOrder/createOrder',
      })
    }
   
  }
})