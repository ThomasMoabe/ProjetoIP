package lan.server.produtos;

public class NenhumProdutoCadastradoException extends Exception {
	public NenhumProdutoCadastradoException() {
		super("Nenhum produto cadastrado para esta categoria");
	}
}
