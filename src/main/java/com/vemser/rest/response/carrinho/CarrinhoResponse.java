package com.vemser.rest.response.carrinho;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vemser.rest.request.carrinho.CarrinhoProdutoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoResponse {
    private List<CarrinhoProdutoRequest> produtos;
    private Long precoTotal;
    private Long quantidadeTotal;
    private String idUsuario;
    @JsonProperty("_id")
    private String id;
}
