package lan.util;

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
}