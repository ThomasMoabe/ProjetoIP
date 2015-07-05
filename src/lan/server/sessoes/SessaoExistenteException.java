package lan.server.sessoes;

public class SessaoExistenteException extends Exception { //o cliente só pode ter uma única sessão por vez e não pode ser excluido se uma sessão existir
	public SessaoExistenteException() {
		super("Cliente tem uma sessão ativa no momento");
	}
}
