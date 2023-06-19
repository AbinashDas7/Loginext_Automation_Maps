package Code;

import org.openqa.selenium.WebDriver;

public class PageObjectFactory {

	private static WebDriver driver;
	private static MapCode mapcode;
	public  ThreadLocal<PageObjectFactory> pageManager= new ThreadLocal<PageObjectFactory>();
	public PageObjectFactory(WebDriver driver) {
		this.driver = driver;
	}
	public static MapCode getMapCode() {
		return(mapcode == null)? mapcode = new MapCode() : mapcode;
	}
	public PageObjectFactory getPageobject() {
		return pageManager.get();
	}

}
