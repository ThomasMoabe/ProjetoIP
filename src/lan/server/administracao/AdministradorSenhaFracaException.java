package lan.server.administracao;

public class AdministradorSenhaFracaException extends Exception {
	public AdministradorSenhaFracaException() {
		super("Senha inválida, a senha deve ter no mínimo 6 caracteres");
	}
}