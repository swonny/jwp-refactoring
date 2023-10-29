package kitchenpos.order.domain.repository;

import kitchenpos.order.domain.OrderLineItem;

import java.util.List;
import java.util.Optional;

public interface OrderLineItemRepository {

    OrderLineItem save(OrderLineItem orderLineItem);

    List<OrderLineItem> saveAll(List<OrderLineItem> orderLineItems);

    Optional<OrderLineItem> findById(Long id);

    List<OrderLineItem> findAll();
}