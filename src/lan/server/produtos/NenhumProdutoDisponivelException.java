package lan.server.produtos;

public class NenhumProdutoDisponivelException extends Exception {
	public NenhumProdutoDisponivelException() {
		super("Todos os produtos da categoria escolhida est�o alugados no momento");
	}
}
