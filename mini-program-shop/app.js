//app.js
App({
  serverUrl: "http://192.168.11.1:8080",
  userInfo: null,
  goodsIdList: [],          //购物车种已有的商品的id列表

  setGlobalUserInfo: function (user) {
    wx.setStorageSync("userInfo", user);
  },

  getGlobalUserInfo: function () {
    return wx.getStorageSync("userInfo");
  },
  /*向购物车添加商品（goodsIdList数组中加入商品id）*/
  setGoodsIdList:function(goodsId){
    //console.log("me"+me);
    this.goodsIdList.push(goodsId);
    //console.log("app----"+this.goodsIdList);
    wx.setStorageSync("goodsIdList", this.goodsIdList);
  },
  /*从购物车删除商品（goodsIdList数组中删除对应的商品id）
  *index表示商品id在数组中的位置，从0 开始计数
  */
  outGoodsIdList: function (index) {
    //console.log("me"+me);
    this.goodsIdList.splice(index,1);
    //console.log("app----"+this.goodsIdList);
    wx.setStorageSync("goodsIdList", this.goodsIdList);
  },
  getGoodsIdList: function (){
    return wx.getStorageSync("goodsIdList");
  }
})