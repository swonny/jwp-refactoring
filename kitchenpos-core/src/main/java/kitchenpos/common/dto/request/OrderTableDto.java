package kitchenpos.common.dto.request;

public class OrderTableDto {

    private long id;

    public OrderTableDto() {
    }

    public OrderTableDto(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
