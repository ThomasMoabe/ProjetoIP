package lan.administracao;

public class AdministradorSenhaFracaException extends Exception {
	public AdministradorSenhaFracaException() {
		super("Senha inv�lida, a senha deve ter no m�nimo 6 caracteres");
	}
}