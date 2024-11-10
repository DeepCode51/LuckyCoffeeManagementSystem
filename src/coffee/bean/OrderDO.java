package coffee.bean;

/**
 * @author yu
 * @Description: 订单表
 * @date 2021/7/1 22:42
 */
public class OrderDO {
    private String id;
    private String orderNo;
    private String totalPrice;
    private String userId;
    private String userName;
    private String state;
    private String phone;
    private String address;
    private String createTime;
    private String payTime;
    private String sendTime;
    private String finishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "OrderDO{" +
                "id='" + id + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", state='" + state + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                '}';
    }
}
