package lan.server.gui;

public class TipoProdutoCombo {
	private String opcao;
    private String tipo;

    public TipoProdutoCombo(String opcao, String tipo) {
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
