package lan.server.caixa;
import lan.server.bd.*;
import lan.server.util.Iterator;

import java.util.Arrays;

public class TransacaoIterator implements Iterator{
	private int registroatual;
	private Transacao[] transacoes;
	
	public TransacaoIterator(Registro[] registros) {
		this.registroatual = 0;
		this.transacoes = Arrays.copyOf(registros, registros.length, Transacao[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.transacoes.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Transacao next() {
		this.registroatual++;
		return this.transacoes[this.registroatual-1];
	}
}