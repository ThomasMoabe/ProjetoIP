package lan.server.servicos;

import lan.server.bd.*;

public class ServicosManager {
	private Tabela tabelaservicos;
	
	public ServicosManager() {
		BD.banco.novatabela("servicos", new String[] {"(int)id", "(string)descricao", "(double)preco"}, BD.tipobanco);
		this.tabelaservicos = BD.banco.selecionatabela("servicos");
	}
	
	public void cadastrarservico(String descricao, double valor) {
		String[] valores = {String.valueOf(this.tabelaservicos.getIdAtual()), descricao, String.valueOf(valor)};
		Servico novo = new Servico(valores);
		this.tabelaservicos.inserir((Registro) novo);
	}
	
	public void deletaservico(String id) {
		this.tabelaservicos.remove("{id=" + id + "}");
	}
	
	public ServicoIterator iterator() {
		Registro[] servicosdisponiveis = this.tabelaservicos.procura("");
		return new ServicoIterator(servicosdisponiveis);
	}
}