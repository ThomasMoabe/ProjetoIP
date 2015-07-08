package lan.server.clientes;

public class SessaoIniciadaException extends Exception { // n�o pode adicionar tempo para o cliente que tenha uma sess�o ativa na categoria que o tempo ser� adicionado
	public SessaoIniciadaException() {
		super("Existe uma sess�o ativa para o cliente na categoria escolhida, por favor finalize antes de adicionar tempo");
	}
}
