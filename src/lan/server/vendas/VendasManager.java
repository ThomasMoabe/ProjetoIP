package lan.server.vendas;

import lan.server.bd.*;

public class VendasManager {
	private Tabela vendas;
	
	public VendasManager() {
		BD.banco.novatabela("vendas", new String[] {"(int)id", "(string)descricao", "(string)nomeadministrador", "(double)valor", "(double)desconto", "(double)total"}, BD.tipobanco);
		this.vendas = BD.banco.selecionatabela("vendas");
	}
	
	public void novaVenda(String descricao, String nomeadministrador, double valor, double desconto) throws DescontoInvalidoException {
		if (desconto < 0 || (valor - desconto < 0)) {
			throw new DescontoInvalidoException();
		}
		String[] valores = {String.valueOf(this.vendas.getIdAtual()), descricao, nomeadministrador, String.valueOf(valor), String.valueOf(desconto), String.valueOf(valor - desconto)};
		Venda nova = new Venda(valores);
		this.vendas.inserir((Registro) nova);
	}
	
	public VendaIterator iterator() {
		Registro[] vendas = this.vendas.procura("");
		return new VendaIterator(vendas);
	}
}
