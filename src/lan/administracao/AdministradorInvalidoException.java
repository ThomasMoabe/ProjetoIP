package lan.administracao;

public class AdministradorInvalidoException extends Exception{
	public AdministradorInvalidoException() {
		super("Falha ao logar: usuário ou senha incorreta!");
	}
}