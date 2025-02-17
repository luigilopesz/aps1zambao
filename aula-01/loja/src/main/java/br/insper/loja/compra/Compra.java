package br.insper.loja.compra;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Compra {

    @Id
    private String id;
    private String usuario;
    private String nome;
    private List<String> produtos = new ArrayList<>();
    private LocalDateTime dataCompra;
    private Float totalPrice; // Novo campo para armazenar o preço total da compra

}
