package lan.server.sessoes;

import java.util.Arrays;

import lan.server.bd.Registro;
import lan.server.util.Iterator;

public class SessaoIterator implements Iterator{
	private int registroatual;
	private Sessao[] sessoes;
	
	public SessaoIterator(Registro[] registros) {
		this.registroatual = 0;
		this.sessoes = Arrays.copyOf(registros, registros.length, Sessao[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.sessoes.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Sessao next() {
		this.registroatual++;
		return this.sessoes[this.registroatual-1];
	}
}