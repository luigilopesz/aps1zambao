package br.insper.loja.product;

import br.insper.loja.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {
}

