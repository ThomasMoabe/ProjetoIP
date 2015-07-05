package lan.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHora {
	public static String getData() {
		Date data = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		return ft.format(data);
	}
	
	public static String getHora() {
		Date data = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("HH:mm:ss");
		return ft.format(data);
	}
	
	public static String segundosToString(int segundos) {
		int horastem = (int) Math.floor(segundos/3600);
		int minutostem = (int) Math.floor((segundos % 3600) / 60);
		int segundostem = (int) Math.floor(segundos % 60);
		String horaformatada = (horastem > 0 ? String.valueOf(horastem) + (horastem > 1 ? " horas" : " hora") : "") + ((horastem > 0 && minutostem > 0 && segundostem > 0) ? ", " : (horastem > 0 && minutostem > 0 && segundostem == 0) ? " e " : "") + ((minutostem > 0) ? String.valueOf(minutostem) + (minutostem > 1 ? " minutos" : " minuto") : "") + ((horastem > 0 || minutostem > 0) && (segundostem  >0) ? " e " : "") + (segundostem > 0 ? String.valueOf(segundostem) + (segundostem > 1 ? " segundos" : " segundo") : "");
		return horaformatada;
	}
}