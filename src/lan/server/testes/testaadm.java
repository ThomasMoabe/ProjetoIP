package lan.server.testes;

import lan.server.administracao.*;
import lan.server.bd.BD;

public class testaadm {
	public static void main(String[] args) {
		BD.banco = new BD();
		BD.tipobanco = "lista";
		AdmManager adm = null;
		adm = new AdmManager();
		try {
			adm.cadastraAdministrador("tafm", "Thomas", "casa1234");
			adm.cadastraAdministrador("taaa", "Thomas", "casa1234");
		} catch (AdministradorLoginInvalidoException e) {
			e.printStackTrace();
		} catch (AdministradorSenhaFracaException e) {
			e.printStackTrace();
		} catch (AdministradorJaCadastradoException e) {
			e.printStackTrace();
		}
		AdministradorIterator iterator = adm.iterator();
		Administrador teste = null;
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}