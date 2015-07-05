package lan.server.clientes;

public class SessaoIniciadaException extends Exception { // não pode adicionar tempo para o cliente que tenha uma sessão ativa na categoria que o tempo será adicionado
	public SessaoIniciadaException() {
		super("Existe uma sessão ativa para o cliente na categoria escolhida, por favor finalize antes de adicionar tempo");
	}
}
