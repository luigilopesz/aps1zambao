package br.insper.loja.product;


import br.insper.loja.product.Product;
import br.insper.loja.product.ProductDTO;
import br.insper.loja.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/decrease-stock/{quantity}")
    public ResponseEntity<String> decreaseStock(@PathVariable String id, @PathVariable int quantity) {
        boolean updated = productService.decreaseStock(id, quantity);
        if (updated) {
            return ResponseEntity.ok("Stock updated successfully.");
        }
        return ResponseEntity.badRequest().body("Insufficient stock or product not found.");
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
