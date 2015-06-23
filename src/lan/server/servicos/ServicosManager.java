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
	
	public Servico procura(String id) throws ServicoNaoEncontradoException {
		Registro[] servico = this.tabelaservicos.procura("{id=" + id + "}");
		if (servico.length == 0) {
			throw new ServicoNaoEncontradoException();
		}
		return (Servico) servico[0];
	}
	
	public ServicoIterator iterator() {
		Registro[] servicosdisponiveis = this.tabelaservicos.procura("");
		return new ServicoIterator(servicosdisponiveis);
	}
}