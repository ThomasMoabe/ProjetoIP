package lan.caixa;

public class Caixa {
	private double saldocache; //pra não percorrer toda hora as transações calculando o saldo
	private RepositorioTransacao transacoes;
	
	public Caixa() {
		this.saldocache = transacoes.getSaldo();
	}
	
	public void novaTransacao(String tipo, String descricao, double valor) {
		if(tipo.equals("saida") && (saldocache-valor)<0)
			new SaldoInsuficienteException();
		transacoes.inserir(tipo, descricao, valor);
		this.saldocache = tipo.equals("entrada")?this.saldocache+valor:this.saldocache-valor;
	}
	
	public Transacao procuraTransacao(int id) {
		return this.transacoes.procurar(id);
	}
	
	public void removeTransacao(int id) {
		this.transacoes.remover(id);
	}
	
	public double getSaldo() {
		return this.saldocache;
	}
}