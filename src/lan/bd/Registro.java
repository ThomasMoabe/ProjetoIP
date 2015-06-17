package lan.bd;

public class Registro { //classe genérica que trata qualquer coisa (pessoa, produto, etc) que precise salvar em um repositório (seja ele que tipo for) da mesma forma
	private Tabela tabela;
	private String[] valores;
	protected int id; //referência para procura e exclusão
	public Registro (String[] valores) {
		this.valores = valores;
	}
	
	public String toString() { 
		String registro = "";
		String[] campostabela = this.tabela.getCampos();
		for (int i = 0; i < campostabela.length; i++) {
			registro += "{" + campostabela[i] + ":" + this.valores[i] + "}";
		}
		return registro;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}
}