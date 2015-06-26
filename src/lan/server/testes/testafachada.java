package lan.server.testes;

import lan.server.administracao.AdministradorInvalidoException;
import lan.server.administracao.AdministradorIterator;
import lan.server.administracao.AdministradorJaCadastradoException;
import lan.server.administracao.AdministradorLoginInvalidoException;
import lan.server.administracao.AdministradorSenhaFracaException;
import lan.server.caixa.SaldoInsuficienteException;
import lan.server.painel.Lan;
import lan.server.servicos.ServicoIterator;
import lan.server.servicos.ServicoNaoEncontradoException;
import lan.server.util.ConfiguracaoInvalidaException;

public class testafachada {
	public static void main(String[] args) {
		Lan lan = null;
		
		try  {
			lan =new Lan();
		} catch (ConfiguracaoInvalidaException e) {
			System.out.println(e.getMessage());
		}
		try {
			lan.cadastraAdministrador("tafm", "Thomas", "134452");
			try {
				lan.loginAdministrador("tafm", "134452");
			} catch (AdministradorInvalidoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (AdministradorLoginInvalidoException e) {
			e.printStackTrace();
		} catch (AdministradorSenhaFracaException e) {
			e.printStackTrace();
		} catch (AdministradorJaCadastradoException e) {
			e.printStackTrace();
		}
		AdministradorIterator iteratoradm = lan.iteratorAdministradores();
		while(iteratoradm.hasNext()) {
			System.out.println(iteratoradm.next().getNome());
		}
		lan.cadastraServico("xerox", 0.15);
		ServicoIterator iteratorsrv = lan.iteratorServicos();
		while (iteratorsrv.hasNext()) {
			System.out.println(iteratorsrv.next().getDescricao());
		}
		try {
			lan.contrataServico("1", 2);
		} catch (ServicoNaoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaldoInsuficienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lan.getSaldoCaixa());
	}
}