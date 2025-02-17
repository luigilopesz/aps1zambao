package br.insper.loja.product;

public record ProductDTO(
        String name,
        Float price,
        Integer stock
) {
}
