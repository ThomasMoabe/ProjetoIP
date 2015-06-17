package lan.bd;
import lan.caixa.*;;

public class RepositorioArray extends Tabela{ //um repositório que armazena qualquer coisa em array
	private Registro[] registros;
	private int qtdregistros;
	
	RepositorioArray(String nome, String[] campos) {
		super(nome, campos);
		this.registros = new Registro[2];
		this.qtdregistros = 0;
	}
	
	public void inserir(Registro registro) {
		if (this.qtdregistros == this.registros.length-1) {
			Registro[] registrosmaior = new Registro[this.registros.length*2];
			for (int i = 0; i < this.qtdregistros; i++) {
				registrosmaior[i] = this.registros[i];
			}
			this.registros = registrosmaior;
		}
		registro.setTabela(this); //o registro precisa saber a que tabela pertence para validar os campos
		this.registros[this.qtdregistros] = registro;
		this.qtdregistros++;
	}
	
	public Registro getRegistro(int indice, int saltos) {
		Registro encontrado = null;
		for (int i = 0; i < this.qtdregistros; i++) {
			if (i==indice) {
				encontrado = this.registros[i];
			}
		}
		return encontrado;
	}
	
	public void removeporid(int id) {
		for (int i = 0, saltos = 0; i < this.qtdregistros; i++) {
			if (this.registros[i].getId() == id) {
				saltos = 1;
			}
			this.registros[i] = (i == (this.qtdregistros - 1)) ? null : this.registros[i + saltos];
		}
		this.qtdregistros--;
	}
}