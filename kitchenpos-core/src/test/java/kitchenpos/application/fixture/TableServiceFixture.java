package kitchenpos.application.fixture;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.tablegroup.domain.TableGroup;
import kitchenpos.common.dto.request.PutOrderTableEmptyRequest;
import kitchenpos.common.dto.request.PutOrderTableGuestsNumberRequest;
import kitchenpos.order.persistence.JpaOrderRepository;
import kitchenpos.ordertable.persistence.JpaOrderTableRepository;
import kitchenpos.tablegroup.persistence.JpaTableGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class TableServiceFixture {

    @Autowired
    private JpaOrderTableRepository orderTableRepository;

    @Autowired
    private JpaOrderRepository orderRepository;

    @Autowired
    private JpaTableGroupRepository tableGroupRepository;

    protected OrderTable 사용_불가능한_상태의_테이블;
    protected OrderTable 주문_상태가_COMPLETION인_주문_테이블;
    protected OrderTable 손님이_한_명인_테이블;
    protected long 유효하지_않은_주문_테이블_아이디;
    protected long 유효하지_않은_주문_테이블의_테이블아이디;
    protected OrderTable 조회용_주문_테이블_1;
    protected OrderTable 조회용_주문_테이블_2;
    protected PutOrderTableEmptyRequest 상태_변경_요청_dto;
    protected PutOrderTableEmptyRequest 유효하지_않은_테이블아이디의_주문_테이블_상태_변경_요청_dto;
    protected OrderTable 그룹테이블에_포함된_주문_테이블_1;
    protected PutOrderTableGuestsNumberRequest 손님수_변경_요청_dto;
    protected PutOrderTableGuestsNumberRequest 손님수가_음수인_손님수_변경_요청_dto;

    protected void 모든_테이블을_조회한다_픽스처_생성() {
        조회용_주문_테이블_1 = new OrderTable(3, false);
        조회용_주문_테이블_2 = new OrderTable(3, false);
        orderTableRepository.saveAll(List.of(조회용_주문_테이블_1, 조회용_주문_테이블_2));
    }

    protected void 사용_불가능한_테이블_상태를_사용_가능한_상태로_바꾼다_픽스처_생성() {
        사용_불가능한_상태의_테이블 = new OrderTable(3, false);
        사용_불가능한_상태의_테이블.changeEmptyStatus(true);
        orderTableRepository.save(사용_불가능한_상태의_테이블);

        상태_변경_요청_dto = new PutOrderTableEmptyRequest(false);
    }

    protected void 유효하지_않은_테이블_아이디를_전달_받은_경우_예외가_발생한다_픽스처_생성() {
        유효하지_않은_주문_테이블의_테이블아이디 = -999L;
        유효하지_않은_테이블아이디의_주문_테이블_상태_변경_요청_dto = new PutOrderTableEmptyRequest(false);
    }

    protected void 주문_테이블_아이디가_그룹_테이블에_포함되어_있다면_예외가_발생한다_픽스처_생성() {
        그룹테이블에_포함된_주문_테이블_1 = new OrderTable(3, false);
        final OrderTable 주문_테이블_2 = new OrderTable(3, false);

        final TableGroup 테이블_그룹 = new TableGroup(LocalDateTime.now());
        tableGroupRepository.save(테이블_그룹);

        그룹테이블에_포함된_주문_테이블_1.addToTableGroup(테이블_그룹);
        orderTableRepository.saveAll(List.of(그룹테이블에_포함된_주문_테이블_1, 주문_테이블_2));

        final Order 주문 = new Order(그룹테이블에_포함된_주문_테이블_1.getId(), OrderStatus.COMPLETION);
        orderRepository.save(주문);

        상태_변경_요청_dto = new PutOrderTableEmptyRequest(false);
    }

    protected void 주문_테이블_아이디에_해당하는_테이블의_주문_상태가_COMPLETION이_아닌_경우_예외가_발생한다_픽스처_생성() {
        주문_상태가_COMPLETION인_주문_테이블 = new OrderTable(3, false);
        orderTableRepository.save(주문_상태가_COMPLETION인_주문_테이블);

        final Order 주문 = new Order(주문_상태가_COMPLETION인_주문_테이블.getId(), OrderStatus.COOKING);
        orderRepository.save(주문);

        상태_변경_요청_dto = new PutOrderTableEmptyRequest(false);
    }

    protected void 손님_수를_변경한다_픽스처_생성() {
        손님이_한_명인_테이블 = new OrderTable(1, false);
        orderTableRepository.save(손님이_한_명인_테이블);

        손님수_변경_요청_dto = new PutOrderTableGuestsNumberRequest(10);
    }

    protected void 입력_받은_손님_수가_0보다_작으면_예외가_발생한다_픽스처_생성() {
        손님이_한_명인_테이블 = new OrderTable(1, false);
        orderTableRepository.save(손님이_한_명인_테이블);

        손님수가_음수인_손님수_변경_요청_dto = new PutOrderTableGuestsNumberRequest(-1);
    }

    protected void 유효하지_않은_주문_테이블_아이디를_전달_받은_경우_예외가_발생한다_픽스처_생성() {
        유효하지_않은_주문_테이블_아이디 = -999L;
        손님수_변경_요청_dto = new PutOrderTableGuestsNumberRequest(10);
    }
}
