package kitchenpos.tablegroup.domain.repository;

import kitchenpos.tablegroup.domain.TableGroup;

import java.util.List;
import java.util.Optional;

public interface TableGroupRepository {

    TableGroup save(TableGroup entity);

    Optional<TableGroup> findById(Long id);

    List<TableGroup> findAll();
}
