package kitchenpos.menu.domain;

import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.common.vo.Price;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_group_id", updatable = false)
    private MenuGroup menuGroup;

    @Embedded
    private Price price;

    private String name;

    protected Menu() {
    }

    private Menu(
            final MenuGroup menuGroup,
            final Price price,
            final String name
    ) {
        this.menuGroup = menuGroup;
        this.price = price;
        this.name = name;
    }

    public static Menu of(
            final MenuGroup menuGroup,
            final String name,
            final BigDecimal price
    ) {
        return new Menu(menuGroup, new Price(price), name);
    }

    public Long getId() {
        return id;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }
}
