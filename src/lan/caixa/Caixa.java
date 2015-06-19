package lan.caixa;

import lan.bd.*;
import lan.util.DataHora;

public class Caixa {
	private double saldocache; //pra não varrer todos os registros sempre que receber um getSaldo()
	private Tabela tabelatransacoes;
	
	public Caixa() {
		this.saldocache = 0;
		BD.banco.novatabela("transacoes", new String[] {"id", "tipo", "descricao", "valor", "data", "hora", "administrador"}, BD.tipobanco);
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
	
	public void novatransacao(String tipo, String descricao, String valor, String administrador) throws SaldoInsuficienteException {
		String[] valores = {String.valueOf(this.tabelatransacoes.getIdAtual()), tipo, descricao, valor, DataHora.getData(), DataHora.getHora(), administrador};
		Transacao insere = new Transacao(valores);
		if (insere.getTipo().equals("saida") && (this.saldocache - insere.getValor()) < 0) {
			throw new SaldoInsuficienteException();
		}
		this.saldocache += insere.getTipo().equals("entrada") ? insere.getValor() : -insere.getValor();
		this.tabelatransacoes.inserir((Registro) insere);
	}
	
	public void removetransacao(String id, String administrador) throws TransacaoNaoEncontradaException { //por motivos de $egurança, este método tem efeito de estorno, a remoção de uma entrada significa a adição de um estorno de saída, o contrário também
		Registro[] transacoes = tabelatransacoes.procura("{id:" + id + "}");
		if (transacoes.length == 0) {
			throw new TransacaoNaoEncontradaException();
		} else {
			Transacao estorna = (Transacao)transacoes[0];
			String[] valoresinsere = {String.valueOf(this.tabelatransacoes.getIdAtual()), estorna.getTipo().equals("entrada") ? "saida" : "entrada", "Estorno referente à movimento de número " + id + " (" + estorna.getDescricao() + ")", String.valueOf(estorna.getValor()), DataHora.getData(), DataHora.getHora(), administrador};
			//Transacao insere = 
		}
	}
	
	public TransacaoIterator iterator() {
		Registro[] transacoes = tabelatransacoes.procura("");
		return new TransacaoIterator(transacoes);
	}
	
	public double getSaldo() {
		return this.saldocache;
	}
}