package coffee.bean;

/**
 * @author yu
 * @Description: 行政单位维护表
 * @date 2022/4/17 10:16
 */
public class AreaDO {
    private String areaCode;
    private String area;
    private String sort;
    private String areaLevel;
    private String areaParent;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaParent() {
        return areaParent;
    }

    public void setAreaParent(String areaParent) {
        this.areaParent = areaParent;
    }

    @Override
    public String toString() {
        return "AreaDO{" +
                "areaCode='" + areaCode + '\'' +
                ", area='" + area + '\'' +
                ", sort='" + sort + '\'' +
                ", areaLevel='" + areaLevel + '\'' +
                ", areaParent='" + areaParent + '\'' +
                '}';
    }
}
