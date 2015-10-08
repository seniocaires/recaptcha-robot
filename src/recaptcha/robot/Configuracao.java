package recaptcha.robot;

import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * @author Senio Caires
 */
public class Configuracao {

	/* ------------------------------
	 * CONSTANTES
	 * ------------------------------
	 */

	/**
	 * @author Senio Caires
	 */
	public static final boolean USE_PROXY = Boolean.FALSE;

	/* ------------------------------
	 * WEB DRIVE
	 * ------------------------------
	 */

	/**
	 * @author Senio Caires
	 */
	public static FirefoxProfile getFireFoxProfile() {

		FirefoxProfile retorno = new FirefoxProfile();

		if (USE_PROXY) {
			retorno.setPreference("network.proxy.http", "127.0.0.1");
			retorno.setPreference("network.proxy.http_port", 0);
		}

		return retorno;
	}
}