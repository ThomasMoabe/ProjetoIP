package lan.server.testes;

import lan.server.bd.BD;
import lan.server.clientes.ClienteJaCadastradoException;
import lan.server.clientes.ClienteLoginInvalidoException;
import lan.server.clientes.ClienteSenhaFracaException;
import lan.server.clientes.ClientesManager;
import lan.server.util.Config;
import lan.server.util.ConfiguracaoInvalidaException;

public class testaclientes {
	public static void main(String[] args) {
		BD.banco = new BD();
		try {
			Config conf = new Config();
			BD.tipobanco = conf.getTipoBanco();
			ClientesManager clientes = new ClientesManager();
			try {
				clientes.cadastracliente("tafm", "Thomas", "Rua x", "thomas@qqcoisa", "1234", "03/05/1992");
			} catch (ClienteJaCadastradoException
					| ClienteLoginInvalidoException
					| ClienteSenhaFracaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ConfiguracaoInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}