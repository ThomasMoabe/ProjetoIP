package lan.server.vendas;

public class DescontoInvalidoException extends Exception {
	public DescontoInvalidoException() {
		super("O valor do desconto/valor total n�o pode ser negativo");
	}
}
