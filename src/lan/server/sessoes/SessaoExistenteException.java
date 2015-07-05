package lan.server.sessoes;

public class SessaoExistenteException extends Exception { //o cliente s� pode ter uma �nica sess�o por vez e n�o pode ser excluido se uma sess�o existir
	public SessaoExistenteException() {
		super("Cliente tem uma sess�o ativa no momento");
	}
}
