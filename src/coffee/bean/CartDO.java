package coffee.bean;

/**
 * @author yu
 * @Description: 购物车表
 * @date 2021/7/1 22:39
 */
public class CartDO {
    private String id;
    private String itemId;
    private String itemName;
    private String itemPic;
    private String price;
    private String num;
    private String totalPrice;
    private String createTime;
    private String userId;
    private String state;
    private String orderId;
    private String itemState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    @Override
    public String toString() {
        return "CartDO{" +
                "id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPic='" + itemPic + '\'' +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userId='" + userId + '\'' +
                ", state='" + state + '\'' +
                ", orderId='" + orderId + '\'' +
                ", itemState='" + itemState + '\'' +
                '}';
    }
}
