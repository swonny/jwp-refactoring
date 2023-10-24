package kitchenpos.ui.dto;

public class OrderLineItemDto {

    private Long menuId;
    private int quantity;

    public OrderLineItemDto(final Long menuId, final int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public Long getMenuId() {
        return menuId;
    }

    public int getQuantity() {
        return quantity;
    }
}