package lan.server.clientes;

public class ClienteValorObrigatorioException extends Exception {
	public ClienteValorObrigatorioException() {
		super("Valor obrigat�rio nulo");
	}
}
