package lan.server.gui;

import lan.server.produtos.Produto;

public class TipoTransacaoCombo {
	private String opcao;
    private String tipo;

    public TipoTransacaoCombo(String opcao, String tipo) {
        this.opcao = opcao;
        this.tipo = tipo;
    }

    public String toString() {
        return opcao;
    }

    public String getKey() {
        return opcao;
    }

    public String getValue() {
        return this.tipo;
    }
}
