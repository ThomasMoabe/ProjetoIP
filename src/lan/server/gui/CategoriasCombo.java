package lan.server.gui;

import lan.server.produtos.CategoriaProdutos;

public class CategoriasCombo {
	private String opcao;
    private CategoriaProdutos categoria;

    public CategoriasCombo(String opcao, CategoriaProdutos categoria) {
        this.opcao = opcao;
        this.categoria = categoria;
    }

    public String toString() {
        return opcao;
    }

    public String getKey() {
        return opcao;
    }

    public CategoriaProdutos getValue() {
        return this.categoria;
    }
}
