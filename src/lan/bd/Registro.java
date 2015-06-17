package lan.bd;

public class Registro { //classe gen�rica que trata qualquer coisa (pessoa, produto, etc) que precise salvar em um reposit�rio (seja ele que tipo for) da mesma forma
	private Tabela tabela;
	private String[] valores;
	protected int id; //refer�ncia para procura e exclus�o
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