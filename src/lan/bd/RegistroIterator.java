package lan.bd;

public class RegistroIterator implements Iterator{
	Registro[] registros;
	private int atual;
	private int quantidade;
	
	public RegistroIterator(Registro[] registros) {
		this.atual = 0;
		this.quantidade = registros.length;
		this.registros = registros;
	}
	
	public Registro next() {
		this.atual++;
		return this.registros[atual-1];
	}
	
	public boolean hasNext() {
		return ((this.atual) < this.quantidade) ? true : false;
	}
	
	public int quantidadeElementos() {
		return this.quantidade;
	}
}