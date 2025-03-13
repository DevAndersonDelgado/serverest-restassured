package com.vemser.rest.response.produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {
    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
    @JsonProperty("_id")
    private String id;
}
