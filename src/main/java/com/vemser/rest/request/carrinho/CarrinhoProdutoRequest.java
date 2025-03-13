package com.vemser.rest.request.carrinho;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrinhoProdutoRequest {
    private String idProduto;
    private Long quantidade;

    public CarrinhoProdutoRequest(String id, Long quantidade) {
        this.idProduto = id;
        this.quantidade = quantidade;
    }
}