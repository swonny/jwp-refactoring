package kitchenpos.application;

import kitchenpos.application.fixture.MenuServiceFixture;
import kitchenpos.dao.JdbcTemplateMenuDao;
import kitchenpos.dao.JdbcTemplateMenuGroupDao;
import kitchenpos.dao.JdbcTemplateMenuProductDao;
import kitchenpos.dao.JdbcTemplateProductDao;
import kitchenpos.domain.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MenuServiceTest extends MenuServiceFixture {

    @Mock
    private JdbcTemplateMenuDao menuDao;

    @Mock
    private JdbcTemplateProductDao productDao;

    @Mock
    private JdbcTemplateMenuGroupDao menuGroupDao;

    @Mock
    private JdbcTemplateMenuProductDao menuProductDao;

    @InjectMocks
    private MenuService menuService;

    @Test
    void 메뉴를_생성한다() {
        given(menuGroupDao.existsById(anyLong())).willReturn(true);
        given(menuProductDao.save(any())).willReturn(첫번째_메뉴_상품);
        given(menuDao.save(any(Menu.class))).willReturn(저장된_메뉴);
        given(productDao.findById(any())).willReturn(Optional.of(첫번째_메뉴_상품의_상품));

        final Menu actual = menuService.create(저장된_메뉴);

        assertThat(actual).isEqualTo(저장된_메뉴);
    }

    @Test
    void 전달_받은_메뉴의_가격이_입력되지_않았다면_예외가_발생한다() {
        assertThatThrownBy(() -> menuService.create(가격이_입력되지_않은_메뉴))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 전달_받은_메뉴의_가격이_0보다_작은_경우_예외가_발생한다() {
        assertThatThrownBy(() -> menuService.create(가격이_0보다_작은_메뉴))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유효하지_않은_메뉴_그룹_아이디를_전달_받으면_예외가_발생한다() {
        given(menuGroupDao.existsById(anyLong())).willThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> menuService.create(유효하지_않은_메뉴_그룹_아이디를_갖는_메뉴))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유효하지_않은_메뉴_상품_아이디를_전달_받으면_예외가_발생한다() {
        given(menuGroupDao.existsById(anyLong())).willReturn(true);
        given(productDao.findById(anyLong())).willThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> menuService.create(유효하지_않은_메뉴_상품_아이디를_갖는_메뉴))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴의_가격이_메뉴에_포함된_상품_가격을_합친_것보다_작은_경우_예외가_발생한다() {
        given(menuGroupDao.existsById(anyLong())).willReturn(true);
        given(productDao.findById(anyLong())).willThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> menuService.create(유효하지_않은_가격을_갖는_메뉴))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 모든_메뉴를_조회한다() {
        given(menuDao.findAll()).willReturn(저장된_메뉴_리스트);

        final List<Menu> actual = menuService.list();

        assertThat(actual).isEqualTo(저장된_메뉴_리스트);
    }
}
