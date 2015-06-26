package lan.server.clientes;

import java.util.Arrays;

import lan.server.bd.Registro;
import lan.server.produtos.Produto;
import lan.server.util.Iterator;

public class ClienteIterator implements Iterator{
	private int registroatual;
	private Cliente[] clientes;
	
	public ClienteIterator(Registro[] registros) {
		this.registroatual = 0;
		this.clientes = Arrays.copyOf(registros, registros.length, Cliente[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.clientes.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Cliente next() {
		this.registroatual++;
		return this.clientes[this.registroatual-1];
	}
}
