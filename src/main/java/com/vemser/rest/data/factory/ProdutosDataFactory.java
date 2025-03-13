package com.vemser.rest.data.factory;

import com.vemser.rest.request.produto.ProdutosRequest;
import org.apache.commons.lang3.StringUtils;

public class ProdutosDataFactory extends BaseDataFactory{


    public static ProdutosRequest produtoValido() {
        return novoProduto();
    }

    public static ProdutosRequest produtoComNomeEmBranco() {
        ProdutosRequest produto = novoProduto();
        produto.setNome(StringUtils.EMPTY);
        return produto;
    }

    public static ProdutosRequest produtoComPrecoNegativo() {
        ProdutosRequest produto = novoProduto();
        produto.setPreco(-1);
        return produto;
    }
    public static ProdutosRequest produtoComPrecoEmBranco() {
        ProdutosRequest produto = novoProduto();
        produto.setPreco(null);
        return produto;
    }

    public static ProdutosRequest produtoComQuantidadeEmBranco() {
        ProdutosRequest produto = novoProduto();
        produto.setQuantidade(null);
        return produto;
    }
    public static ProdutosRequest produtoComDescricaoEmBranco() {
        ProdutosRequest produto = novoProduto();
        produto.setDescricao(StringUtils.EMPTY);
        return produto;
    }

    public static ProdutosRequest produtoComQuantidadeNegativa() {
        ProdutosRequest produto = novoProduto();
        produto.setQuantidade(-1); // Quantidade inválida
        return produto;
    }

    public static ProdutosRequest produtoComDadosEmBranco() {
        ProdutosRequest produto = novoProduto();
        produto.setNome(StringUtils.EMPTY);
        produto.setDescricao(StringUtils.EMPTY);
        produto.setPreco(0);
        produto.setQuantidade(0);
        return produto;
    }




    private static ProdutosRequest novoProduto() {
        ProdutosRequest produto = new ProdutosRequest();
        produto.setNome(faker.commerce().productName());
        produto.setPreco(gerarIntAleatorio());
        produto.setDescricao(faker.commerce().material());
        produto.setQuantidade(gerarIntAleatorio());
        return produto;
    }
}
