package lan.server.administracao;

public class AdministradorInvalidoException extends Exception{
	public AdministradorInvalidoException() {
		super("Falha ao logar: usu�rio ou senha incorreta!");
	}
}