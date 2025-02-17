package br.insper.loja.compra;

import br.insper.loja.evento.EventoService;
import br.insper.loja.usuario.Usuario;
import br.insper.loja.usuario.UsuarioService;
import br.insper.loja.product.Product;
import br.insper.loja.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ProductRepo productRepository; // Adicionando repositório de produtos

    public Compra salvarCompra(Compra compra) {
        Usuario usuario = usuarioService.getUsuario(compra.getUsuario());
        compra.setNome(usuario.getNome());
        compra.setDataCompra(LocalDateTime.now());

        float totalPrice = 0f;

        for (String productId : compra.getProdutos()) {
            Optional<Product> productOpt = productRepository.findById(productId);

            if (productOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with ID " + productId + " does not exist.");
            }

            Product product = productOpt.get();

            if (product.getStock() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product " + product.getName() + " is out of stock.");
            }

            // Decrementa o estoque do produto
            product.setStock(product.getStock() - 1);
            productRepository.save(product);

            // Adiciona ao total do preço
            totalPrice += product.getPrice();
        }

        compra.setTotalPrice(totalPrice);

        eventoService.salvarEvento(usuario.getEmail(), "Compra realizada");
        return compraRepository.save(compra);
    }

    public List<Compra> getCompras() {
        return compraRepository.findAll();
    }
}
