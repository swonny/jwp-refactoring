package kitchenpos.ui;

import kitchenpos.common.dto.request.CreateProductRequest;
import kitchenpos.common.dto.response.ProductResponse;
import kitchenpos.product.application.ProductService;
import kitchenpos.product.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> create(@RequestBody final CreateProductRequest productRequest) {
        final Product product = productService.create(productRequest);
        final URI uri = URI.create("/api/products/" + product.getId());
        return ResponseEntity.created(uri)
                             .body(toResponse(product));
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> list() {
        final List<ProductResponse> productResponses = productService.list()
                                                                     .stream()
                                                                     .map(this::toResponse)
                                                                     .toList();
        return ResponseEntity.ok()
                             .body(productResponses);
    }

    private ProductResponse toResponse(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getPrice(),
                product.getName()
        );
    }
}
