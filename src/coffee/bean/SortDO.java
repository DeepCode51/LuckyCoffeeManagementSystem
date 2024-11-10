package coffee.bean;

/**
 * @author yu
 * @Description: 分类表
 * @date 2022-3-2 21:25
 */
public class SortDO {
    private String id;
    private String sort;
    private String sortValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public String toString() {
        return "SortDO{" +
                "id='" + id + '\'' +
                ", sort='" + sort + '\'' +
                ", sortValue='" + sortValue + '\'' +
                '}';
    }
}
