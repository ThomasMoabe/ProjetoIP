package lan.caixa;

public class RepositorioTransacoesArray implements RepositorioTransacao {
	private Transacao[] listatransacoes;
	private int qtdtransacoes;
	
	RepositorioTransacoesArray() {
		this.listatransacoes = new Transacao[2];
		this.qtdtransacoes = 0;
	}
	
	public void inserir(String tipo, String descricao, double valor) {
		if (this.listatransacoes.length==this.qtdtransacoes) {
			Transacao[] listatransacoesmaior = new Transacao[this.listatransacoes.length*2];
			for (int i = 0; i < this.qtdtransacoes; i++) {
				listatransacoesmaior[i] = this.listatransacoes[i];
			}
			this.listatransacoes = listatransacoesmaior;
		}
		this.listatransacoes[this.qtdtransacoes] = new Transacao(tipo, descricao, valor);
		this.qtdtransacoes++;
	}
	
	public Transacao procura(int id) {
		Transacao transacao = null;

		for (int i = 0; i < this.qtdtransacoes && transacao == null; i++) {
			if(this.listatransacoes[i].getId() == id) {
				transacao = this.listatransacoes[i];
			}
		}
		return transacao;
	}
	
	public void remove(int id) {
		for (int i = 0, saltos = 0; i < this.qtdtransacoes; i++) {
			if (this.listatransacoes[i].getId() == id) {
				saltos = 1;
			}
			this.listatransacoes[i] = (i == (this.qtdtransacoes - 1)) ? null : this.listatransacoes[i + saltos];
		}
		this.qtdtransacoes--;
	}
	
	public double getSaldo() {
		double saldo = 0;
		
		for (int i = 0; i < this.qtdtransacoes; i++) {
			saldo += this.listatransacoes[i].getTipo().equals("entrada") ? this.listatransacoes[i].getValor() : -this.listatransacoes[i].getValor();
		}
		return saldo;
	}
	
	public Transacao[] toArray() { //devolve um array já tratado sem espaços vazios que seria comum em um banco de dados
		Transacao[] listatransacoes = new Transacao[this.qtdtransacoes];
		
		for (int i = 0; i < this.qtdtransacoes; i++) {
			listatransacoes[i] = this.listatransacoes[i];
		}
		return listatransacoes;
	}
}