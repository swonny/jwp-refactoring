package kitchenpos.dto.request;

public class PutOrderStatusRequest {

    private String orderStatus;

    public PutOrderStatusRequest() {
    }

    public PutOrderStatusRequest(final String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
