package com.vemser.rest.request.produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutosRequest {
    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
}
