package coffee.bean;

/**
 * @author yu
 * @Description: 商品表
 * @date 2021/7/1 22:37
 */
public class ItemDO {
    private String id;
    private String name;
    private String desc;
    private String price;
    private String total;
    private String place;
    private String pic;
    private String brand;
    private String weight;
    private String packet;
    private String state;
    /**
     * 可用库存
     */
    private String stock;

    /**
     * 销量
     */
    private String sales;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String num) {
        this.total = num;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ItemDO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                ", total='" + total + '\'' +
                ", place='" + place + '\'' +
                ", pic='" + pic + '\'' +
                ", brand='" + brand + '\'' +
                ", weight='" + weight + '\'' +
                ", packet='" + packet + '\'' +
                ", state='" + state + '\'' +
                ", stock='" + stock + '\'' +
                ", sales='" + sales + '\'' +
                '}';
    }
}
