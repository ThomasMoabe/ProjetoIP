package lan.server.sessoes;

import lan.server.bd.Registro;
import lan.server.bd.Tabela;

public class AtualizacaoMinutos extends Thread{ //Medida de seguran�a, caso o programa seja fechado pelo gerenciador de tarefas ou por queda de energia, uma atualiza��o no reposit�rio de tempo dos clientes deve ser feita com certa frequ�ncia (em minutos) amenizando preju�zos
	private SessaoIterator atualiza;
	private Tabela tempoclientes;
	
	public AtualizacaoMinutos(SessaoIterator atualiza, Tabela tempoclientes) {
		this.atualiza = atualiza;
		this.tempoclientes = tempoclientes;
	}
	
	public void run() {
		while (this.atualiza.hasNext()) {
			Sessao atualiza = this.atualiza.next();
			String idcliente = String.valueOf(atualiza.getIdCliente());
			String idcategoria = String.valueOf(atualiza.getIdCategoria());
			String segundosrestantes = String.valueOf(atualiza.temporestante());
			String query = "{segundos=" + segundosrestantes + "} WHERE {idcliente=" + idcliente + "}{idcategoriaproduto=" + idcategoria + "}";
			this.tempoclientes.atualiza(query);
		}
	}
}
