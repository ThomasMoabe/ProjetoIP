package lan.server.clientes;

public class ClienteSenhaFracaException extends Exception{
	public ClienteSenhaFracaException() {
		super("A senha do cliente deve ter no m�nimo 4 caracteres");
	}
}