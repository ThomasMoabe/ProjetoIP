package lan.server.sessoes;

import lan.server.bd.BD;
import lan.server.bd.Registro;
import lan.server.bd.Tabela;
import lan.server.clientes.TempoCliente;

public class SessaoManager extends Thread{
	private Tabela tabelasessoes;
	private Tabela tempoclientes;
	private SessaoListener listener;
	private int intervaloatualizaminutos; //valor em segundos, valor negativo desativa verificação
	private long ultimaatualizacaominutos;
	
	public SessaoManager() {
		BD.banco.novatabela("sessoes", new String[] {"(int)id", "(int)idcliente", "(string)nomecliente", "(int)idcategoria", "(string)nomeproduto", "(int)iniciosessao", "(int)tempodisponivel"}, "array");
		this.tabelasessoes = BD.banco.selecionatabela("sessoes");
		this.tempoclientes = BD.banco.selecionatabela("tempoclientes");
		this.intervaloatualizaminutos = 3;
		this.ultimaatualizacaominutos = System.currentTimeMillis()/1000;
	}
	
	public void run() {
		while (true) {System.out.println(".");
			SessaoIterator iterator = this.iterator();
			while (iterator.hasNext()) {
				Sessao verifica = iterator.next();System.out.println(verifica + "{temporestante=" + verifica.temporestante() + "}");
				if (verifica.temporestante() <= 0) {
					this.finalizaSessao(String.valueOf(verifica.getId()));
				}
			}
			if ((System.currentTimeMillis()/1000 - this.ultimaatualizacaominutos) > this.intervaloatualizaminutos) {
				this.atualizaBaseMinutos();
			}
			try {
				Thread.sleep(2000); //verifica apenas de dois em dois segundos o tempo que os clientes têm, a precisão é menor, porém o prejuizo de um segundo para a lan é considerado pouco...
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public SessaoIterator iterator() {
		Registro[] sessoesativas = this.tabelasessoes.procura("");
		return new SessaoIterator(sessoesativas);
	}
	
	public void novaSessao(String idcliente, String nomecliente, String idcategoria, String nomeproduto) throws ImpossivelCriarSessaoException {
		Registro[] procuratempo = this.tempoclientes.procura("{idcliente=" + idcliente + "}{idcategoriaproduto=" + idcategoria + "}");
		if (procuratempo.length == 0) {
			throw new ImpossivelCriarSessaoException();
		} else {
			int tempodisponivel = ((TempoCliente) procuratempo[0]).getSegundos();
			String[] valores = {String.valueOf(this.tabelasessoes.getIdAtual()), idcliente, nomecliente, idcategoria, nomeproduto, String.valueOf((System.currentTimeMillis()/1000)), String.valueOf(tempodisponivel)};
			Sessao novasessao = new Sessao(valores);
			novasessao.setSessaoListener(this.listener);
			this.tabelasessoes.inserir((Registro) novasessao);
		}
		
	}
	
	public void setSessaoListener(SessaoListener listener) {
		this.listener = listener;
	}
	
	public int qtdSessoesAtivas() {
		Registro[] sessoes = this.tabelasessoes.procura("");
		return sessoes.length;
	}
	
	public void finalizaSessao(String id) {
		Registro[] procurasessao = this.tabelasessoes.procura("{id=" + id + "}");
		if (procurasessao.length != 0) {
			Sessao finaliza = (Sessao)procurasessao[0];
			int idcliente = finaliza.getIdCliente();
			int idcategoria = finaliza.getIdCategoria();
			if (finaliza.temporestante() <= 0) {
				this.tabelasessoes.remove("{idcliente=" + String.valueOf(idcliente) + "}{idcategoria=" + String.valueOf(idcategoria) + "}");
				this.tempoclientes.remove("{idcliente=" + String.valueOf(idcliente) + "}{idcategoriaproduto=" + String.valueOf(idcategoria) + "}");
			} else {
				int temporestante = finaliza.temporestante();
				this.tempoclientes.atualiza("{segundos=" + temporestante + "} WHERE {idcliente=" + String.valueOf(idcliente) + "}{idcategoriaproduto=" + String.valueOf(idcategoria) + "}");
				this.tabelasessoes.remove("{idcliente=" + String.valueOf(idcliente) + "}{idcategoria=" + String.valueOf(idcategoria) + "}");
			}
			if (finaliza.getSessaoListener() != null) {
				finaliza.getSessaoListener().sessaoFinalizada(finaliza.getNomeCliente(), finaliza.getNomeProduto()); //dispara evento de sessão finalizada
			}
		}
	}
	
	public void finalizatodas() { //este método deve ser chamado no evento onclose do programa
		SessaoIterator tudo = this.iterator();
		while (tudo.hasNext()) {
			this.finalizaSessao(String.valueOf(tudo.next().getId()));
		}
	}
	
	public void atualizaBaseMinutos() {
		SessaoIterator tudo = this.iterator();
		this.ultimaatualizacaominutos = System.currentTimeMillis()/1000;
		if (this.qtdSessoesAtivas()  > 0) {
			System.out.println("atualizando minutos");
		}
		//new AtualizaMinutos(tudo).start();
	}
}