package lan.server.clientes;

public class ClienteJaCadastradoException extends Exception {
	
	public ClienteJaCadastradoException() {
		super("Já existe um cliente cadastrado com este login");
	}
}