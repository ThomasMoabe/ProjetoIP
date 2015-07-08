package lan.server.clientes;

import java.util.Arrays;

import lan.server.bd.Registro;
import lan.server.util.Iterator;

public class TempoClienteIterator implements Iterator{
	private int registroatual;
	private TempoCliente[] tempocliente;
	
	public TempoClienteIterator(Registro[] registros) {
		this.registroatual = 0;
		this.tempocliente = Arrays.copyOf(registros, registros.length, TempoCliente[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.tempocliente.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public TempoCliente next() {
		this.registroatual++;
		return this.tempocliente[this.registroatual-1];
	}
}