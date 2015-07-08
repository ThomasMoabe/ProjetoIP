package lan.server.gui;

import lan.server.produtos.Produto;

public class ProdutosCombo {
	private String opcao;
    private Produto produto;

    public ProdutosCombo(String opcao, Produto produto) {
        this.opcao = opcao;
        this.produto = produto;
    }

    public String toString() {
        return opcao;
    }

    public String getKey() {
        return opcao;
    }

    public Produto getValue() {
        return this.produto;
    }
}
