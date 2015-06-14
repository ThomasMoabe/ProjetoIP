package lan.caixa;

public class RepositorioTransacoesLista implements RepositorioTransacao {
	private Transacao transacao;
	private RepositorioTransacoesLista proxima;
	
	public RepositorioTransacoesLista() {
		this.transacao = null;
		this.proxima = null;
	}
	
	public void inserir(String tipo, String descricao, double valor) {
		if (this.transacao == null) {
			this.transacao = new Transacao(tipo, descricao, valor);
		}
		else if (this.proxima == null) {
			this.proxima = new RepositorioTransacoesLista();
			this.proxima.inserir(tipo, descricao, valor);
		} else {
			this.proxima.inserir(tipo, descricao, valor);
		}
	}
	
	public Transacao procura(int id) {
		if (this.transacao.getId() == id) {
			return this.transacao;
		} else if (this.proxima == null) {
			return null;
		} else {
			return this.proxima.procura(id);
		}
	}
	
	public void remove(int id) {
		if (this.transacao.getId() == id) {
			this.transacao = this.proxima.getTransacao();
			if (this.proxima != null) {
				this.proxima = this.proxima.getProxima();
			}
		} else {
			this.proxima.remove(id);
		}
	}
	
	public double getSaldo() {
		if (this.transacao == null) {
			return 0;
		} else if (this.proxima == null) {
			return this.transacao.getValor();
		} else {
			return this.transacao.getValor() + (this.proxima.getTransacao().getTipo().equals("entrada") ? this.proxima.getTransacao().getValor() : -this.proxima.getTransacao().getValor());
		}
	}
	
	public Transacao[] toArray() {
		int qtdtransacoes = (this.transacao == null) ? 0 : 1;
		RepositorioTransacoesLista proxima = this.proxima;
		while (proxima != null) {
			qtdtransacoes++;
			proxima = proxima.getProxima();
		}
		Transacao[] listatransacoes = new Transacao[qtdtransacoes];
		proxima = this.proxima;
		listatransacoes[0] = this.transacao;
		qtdtransacoes = 1;
		while (proxima != null) {
			listatransacoes[qtdtransacoes] = proxima.getTransacao();
			proxima = proxima.getProxima();
			qtdtransacoes ++;
		}
		return listatransacoes;
	}
	
	public Transacao getTransacao() {
		return this.transacao;
	}
	
	public RepositorioTransacoesLista getProxima() {
		return this.proxima;
	}
}