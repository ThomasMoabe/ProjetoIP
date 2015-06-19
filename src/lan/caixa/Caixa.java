package lan.caixa;

import lan.bd.*;

public class Caixa {
	private double saldocache; //pra não varrer todos os registros sempre que receber um getSaldo()
	private Tabela tabelatransacoes;
	
	public Caixa() {
		this.saldocache = 0;
		BD.banco.novatabela("transacoes", new String[] {"id", "tipo", "descricao", "valor", "data", "administrador"}, BD.tipobanco);
		this.tabelatransacoes = BD.banco.selecionatabela("transacoes");
		Registro[] transacoes = tabelatransacoes.procura(""); //query vazia > pega todas as transações cadastradas na tabela
		for (int i = 0; i < transacoes.length; i++) {
			if (((Transacao) transacoes[i]).getTipo().equals("entrada")) {
				this.saldocache += ((Transacao) transacoes[i]).getValor();
			} else if (((Transacao) transacoes[i]).getTipo().equals("saida")) {
				this.saldocache -= ((Transacao) transacoes[i]).getValor();
			}
		}
	}
	
	public void novatransacao(String tipo, String descricao, String valor, String data, String administrador) throws SaldoInsuficienteException {
		String[] valores = {String.valueOf(this.tabelatransacoes.getIdAtual()), tipo, descricao, valor, data, administrador};
		Transacao insere = new Transacao(valores);
		if (insere.getTipo().equals("saida") && (this.saldocache - insere.getValor()) < 0) {
			throw new SaldoInsuficienteException();
		}
		this.saldocache += insere.getTipo().equals("entrada") ? insere.getValor() : -insere.getValor();
		this.tabelatransacoes.inserir((Registro) insere);
	}
	
	public double getSaldo() {
		return this.saldocache;
	}
}