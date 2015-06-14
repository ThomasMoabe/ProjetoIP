package lan.caixa;

public class Caixa {
	private double saldocache; //pra não percorrer toda hora as transações calculando o saldo
	private RepositorioTransacao transacoes;
	
	public Caixa(String tipoarmazenamento) {
		switch(tipoarmazenamento) {
		case "array":
			this.transacoes = new RepositorioTransacoesArray();
			break;
		case "lista":
			this.transacoes = new RepositorioTransacoesLista();
			break;
		}
		this.saldocache = transacoes.getSaldo();
	}
	
	public void novaTransacao (String tipo, String descricao, double valor) throws SaldoInsuficienteException {
		if (tipo.equals("saida") && (saldocache-valor)<0) {
			throw new SaldoInsuficienteException();
		}
		transacoes.inserir(tipo, descricao, valor);
		this.saldocache = tipo.equals("entrada") ? (this.saldocache + valor) : (this.saldocache - valor);
	}
	
	public Transacao procuraTransacao(int id) throws TransacaoNaoEncontradaException {
		Transacao transacao = this.transacoes.procura(id);
		if (transacao == null) {
			throw new TransacaoNaoEncontradaException();
		}
		return transacao;
	}
	
	public void removeTransacao(int id) throws TransacaoNaoEncontradaException {
		Transacao remove = this.procuraTransacao(id);
		this.transacoes.remove(id);
		this.saldocache = remove.getTipo().equals("entrada") ? (this.saldocache - remove.getValor()) : (this.saldocache + remove.getValor());
	}
	
	public double getSaldo() {
		return this.saldocache;
	}
	
	public Transacao[] getArrayTransacoes() { //formato unificado para laço de repetição da interface com o usuário
		return this.transacoes.toArray();
	}
}