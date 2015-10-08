package recaptcha.robot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Senio Caires
 */
public class Robot {

	private static final String URL = "http://www.primefaces.org/showcase/ui/misc/captcha.xhtml";

	private static WebDriver driver;

	static Wait<WebDriver> espera;
	static String handlePrincipal;
	static String handleCheckBox;
	static String handleImages;
	static WebDriverWait wait;
	static WebElement checkBox;
	static WebElement iframeCheck; 

	public static void main(String[] args) {

		driver = new FirefoxDriver();

		inicializar();
		sleepThread();

		boolean continuar = true;

		do {

			try {
				wait = new WebDriverWait(driver, 20);
				driver.switchTo().window(handlePrincipal);
				for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
					System.out.println("FRAME -> " + frame.getAttribute("name"));
				}

				List<WebElement> frames = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));
				try {
					driver.findElement(By.xpath("//span[contains(@class, 'rc-anchor-error-msg')]"));
					inicializar();
				} catch (NoSuchElementException csee) {
				}
				while (frames.size() < 2) {
					wait = new WebDriverWait(driver, 20);
					frames = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("iframe")));
				}

				WebElement elementFrameTable = driver.findElements(By.tagName("iframe")).get(1);
				System.out.println("name iframe table -> " + elementFrameTable.getAttribute("name"));
				System.out.println("HTML iframe table -> " + elementFrameTable.getAttribute("innerHTML"));
				driver.switchTo().frame(elementFrameTable.getAttribute("name"));
				handleImages = driver.getWindowHandle();
				System.out.println("handleImages -> " + handleImages);

				wait = new WebDriverWait(driver, 20);
				List<WebElement> imagens = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'rc-image-tile-target')]")));

				Actions builderImagem1 = new Actions(driver);
				builderImagem1.moveToElement(imagens.get(0)).perform();
				imagens.get(0).click();

				sleepThread();
				Actions builderImagem2 = new Actions(driver);
				builderImagem2.moveToElement(imagens.get(1)).perform();
				imagens.get(1).click();

				sleepThread();
				Actions builderImagem3 = new Actions(driver);
				builderImagem3.moveToElement(imagens.get(5)).perform();
				imagens.get(5).click();

				sleepThread();
				WebElement botaoVerify = driver.findElement(By.id("recaptcha-verify-button"));

				Actions builderVerify = new Actions(driver);
				builderVerify.moveToElement(botaoVerify).perform();
				botaoVerify.click();

				for (WebElement elemento : imagens) {
					System.out.println(elemento.getAttribute("innerHTML"));
				}

				wait = new WebDriverWait(driver, 20);
				driver.switchTo().window(handlePrincipal);

				try {
					driver.findElement(By.xpath("//span[contains(@class, 'rc-anchor-error-msg')]"));
					inicializar();
				} catch (NoSuchElementException csee) {
				}

				wait = new WebDriverWait(driver, 20);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
				System.out.println("name iframe checkbox -> " + iframeCheck.getAttribute("name"));
				driver.switchTo().frame(iframeCheck.getAttribute("name"));
				handleCheckBox = driver.getWindowHandle();
				System.out.println("handleCheckBox -> " + handleCheckBox);
				wait = new WebDriverWait(driver, 20);
				checkBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("recaptcha-anchor")));
				continuar = !checkBox.getAttribute("class").contains("recaptcha-checkbox-checked");

			} catch (StaleElementReferenceException sere) {
				continuar = true;
				continue;
			}

			if (!continuar) {
				wait = new WebDriverWait(driver, 40);
				driver.switchTo().window(handlePrincipal);
				driver.findElement(By.tagName("button")).click();
				try {
					driver.findElement(By.xpath("//span[contains(@class, 'ui-messages-error-summary')]"));
					inicializar();
					continuar = true;
				} catch (NoSuchElementException csee) {
				}
			}

		} while (continuar);

		sleepThread();
	}

	private static void inicializar() {
		driver.get(URL);
		sleepThread();
		handlePrincipal = driver.getWindowHandle();
		System.out.println("handlePrincipal -> " + handlePrincipal);

		wait = new WebDriverWait(driver, 20);
		iframeCheck = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

		System.out.println("name iframe checkbox -> " + iframeCheck.getAttribute("name"));

		driver.switchTo().frame(iframeCheck.getAttribute("name"));
		handleCheckBox = driver.getWindowHandle();
		System.out.println("handleCheckBox -> " + handleCheckBox);

		wait = new WebDriverWait(driver, 20);
		checkBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("recaptcha-anchor")));

		Actions builder = new Actions(driver);

		builder.moveToElement(checkBox).perform();
		checkBox.click();

	}

	private static void sleepThread() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
