package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProdutoPage {

	private WebDriver driver;
	private By nomeProduto = By.className("h1");
	private By precoProduto = By.cssSelector(".current-price span:nth-child(1)");
	private By tamnhoProduto = By.id("#group_1");

	public ProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obterNomeProduto() {
		String textNomeProduto = driver.findElement(nomeProduto).getText();
		return textNomeProduto;
	}

	public String obterPrecoProduto() {
		String textPrecoProduto = driver.findElement(precoProduto).getText();
		return textPrecoProduto;
	}

	public Select encontrarDropdownSize() {
		return new Select(driver.findElement(tamnhoProduto));

	}
}
