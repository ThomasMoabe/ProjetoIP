package lan.server.bd;

public class BD { //um banco de dados genérico que armazena qualquer tipo de "tabela" (array, excel ou lista)
	public static BD banco;
	public static String tipobanco;
	public static String caminhoexcel = System.getProperty("user.dir") + System.getProperty("file.separator") + "LanDB.xls";
	private Tabela[] tabelas;
	int qtdtabelas = 0;
	
	public BD() {
		this.tabelas = new Tabela[2];
	}
	
	public void novatabela(String nome, String[] campos, String tipo) {
		if (this.qtdtabelas == tabelas.length-1) {
			Tabela[] tabelasmaior = new Tabela[this.tabelas.length * 2];
			for (int i = 0; i < this.tabelas.length; i++) {
				tabelasmaior[i] = this.tabelas[i];
			}
			this.tabelas = tabelasmaior;
		}
		switch (tipo.toLowerCase()) {
		case "array":
			this.tabelas[qtdtabelas] = new RepositorioArray (nome, campos);
			break;
		case "lista":
			this.tabelas[qtdtabelas] = new RepositorioLista (nome, campos);
			break;
		case "excel":
			this.tabelas[qtdtabelas] = new RepositorioExcel (nome, campos);
			break;
		}
		this.qtdtabelas++;
	}
	
	public Tabela selecionatabela(String nome) {
		Tabela tabela = null;
		
		for (int i = 0; i < this.qtdtabelas; i++) {
			if (this.tabelas[i].getNome() == nome) {
				tabela = this.tabelas[i];
			}
		}
		return tabela;
	}
	
	public static String quoteSimples(String escapa) { //sempre que houver a possibilidade do usuário inserir o caractere "}" em um UPDATE de qualquer coisa, deve ser escapado para não cair na expressão regular de update
		return escapa.replace("}", "\\}");
	}
}