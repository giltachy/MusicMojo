/**
/**
 * <h1>SongsDb</h1>
 * The SongsDb program implements an application that
 * fetchs songs information from www.malayalasangeetham.info
 * <p>
 * This program is for proprietary use and not for distribution 
 * @author  Sudheer Velatt
 * @version 1.0
 * @since   2014-09-07 
 */

package com.giltachy.SongsDb.MalayalamDb;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.giltachy.SongsDb.Movie;
import com.giltachy.SongsDb.Song;

// TODO: Auto-generated Javadoc
/**
 * The Class SongsDb.
 */
public class MalayalamSongsDb {

	/** Initialise the log4j logger. */
	static Logger log = Logger.getLogger(
			MalayalamSongsDb.class.getName());

	/** The string used for searching the song. */
	private static String url = "http://www.malayalasangeetham.info/movies.php?tag=Search&movie=";
	/** The url used for initialising the page. */
	private static String loginUrl="http://www.malayalasangeetham.info";

	/** The web client used to fetch pages. */
	private static WebClient webClient = new WebClient(BrowserVersion.CHROME);

	/**
	 * Instantiates a new songs db.
	 * Initialises web client
	 * Sets the language cookie
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FailingHttpStatusCodeException  from html web client
	 * @throws InterruptedException from html webclient
	 */
	public MalayalamSongsDb() throws IOException, FailingHttpStatusCodeException, InterruptedException{
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("handleRefresh");
			}
		});
		getHtmlPage(loginUrl);
		log.debug("logged in using "+ loginUrl);
		setLanguageToEnglish();
	}

	/**
	 * Gets the html page using web client.
	 *
	 * @param url : The url for which the page has to be opened
	 * @return the html page fetched
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws MalformedURLException the malformed url exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HtmlPage getHtmlPage(String url) 
			throws FailingHttpStatusCodeException,
			MalformedURLException, IOException{

		HtmlPage page = (HtmlPage) 
				webClient.getPage(url);

		return page;
	}

	/**
	 * Sets the language to english.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws MalformedURLException the malformed url exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void setLanguageToEnglish() 
			throws InterruptedException, 
			FailingHttpStatusCodeException, 
			MalformedURLException, 
			IOException{
		CookieManager cookieManager = webClient.getCookieManager();
		Boolean isEnglishSet=false;
		/* Loop through cookies to check langauage preference */
		for(Cookie cookie:cookieManager.getCookies()){
			if (cookie.getName().equals("Language")){
				isEnglishSet=true;
				log.debug("Language was already set");
			}
		}
		cookieManager=null;

		if(!isEnglishSet){
			System.out.println("English Not Set");
			Thread.sleep(500);
			@SuppressWarnings("unused")
			HtmlPage page = getHtmlPage(loginUrl+"/index.php?cl=1");
			log.debug("Language Changed");
		}

		cookieManager = webClient.getCookieManager();
		isEnglishSet=false;
		for(Cookie cookie:cookieManager.getCookies()){
			if (cookie.getName().equals("Language")){
				isEnglishSet=true;
				log.debug("Language Set Sucessfuly");
			}
		}

	}

	/**
	 * Gets the moive list from www.malayalasangeetham.info
	 * Note: This method is based on the website version 
	 * as on 2014-09-07; When the website changes this method
	 * has to change
	 * 
	 * @param movieName the movie name for which 
	 * @return the list movies if not exact match multiple movies
	 */
	public ArrayList<Movie> getMoives(String movieName){
		ArrayList<Movie> movies = new ArrayList<Movie>();
		HtmlPage page;
		try {
			String encodeMovieName=movieName
					.replaceAll(" ", "%20")
					.replaceAll("&","%26")
					+"&sl=1";
			page = getHtmlPage(url+encodeMovieName);
		} catch (FailingHttpStatusCodeException | IOException e) {
			log.error("Could not search movie "+movieName);
			return movies;
		}
		/* Get the table elements from the page */
		DomNodeList <DomElement> tableList  =  page.getElementsByTagName("table");

		HtmlTable htmlTable=null;
		for(DomElement domElement:tableList){
			htmlTable=(HtmlTable) domElement;
			//	System.out.println(htmlTable.getAttribute("class").toString());
			// Get the  Go through each table row 
			List <HtmlElement> rows = htmlTable.getHtmlElementsByTagName("tr");
			for (HtmlElement row: rows) {
				// Check is the class is ptablelist
				// This is the popular movies list
				if (row.getAttribute("class").equals("ptableslist")){
					//Get the table data(aka cells)
					List <HtmlElement> cells= row.getElementsByTagName("td");
					int cellCnt=1;
					Movie movie = new Movie();
					for(HtmlElement cell:cells){
						//System.out.println(cell.getAttribute("class").toString());
						//Populate movie object
						switch (cellCnt) {
						case 1: 
							movie.setTitle(cell.asText());
							HtmlAnchor htmlAnchor = (HtmlAnchor) cell.getChildElements().iterator().next();
							movie.setLink(htmlAnchor.getHrefAttribute()); 
							break;
						case 2: 
							movie.setYear(cell.asText());
							break;
						case 3: 
							movie.setMusicDirector(cell.asText());
							break;
						case 4: 
							movie.setLyricist(cell.asText());
							break;
						case 5: 
							movie.setDirector(cell.asText());
							break;
						default: 
							break;
						}
						cellCnt++;
					}
					movies.add(movie);
					log.debug(movie.toString());
					movie=null;
				}
			}	    
		}
		return movies;
	}
	
	/**
	 * Gets the song list of  moive from www.malayalasangeetham.info
	 * Note: This method is based on the website version 
	 * as on 2014-09-07; When the website changes this method
	 * has to change
	 * 
	 * @param movie the movie
	 * @return the movie songs
	 */
	public ArrayList<Song> getMovieSongs(Movie movie){
		ArrayList<Song> songs=new ArrayList<Song>();
		HtmlPage page;
		String genre="cinema";
		try {
			page = getHtmlPage(loginUrl+"/"+movie.getLink());
		} catch (FailingHttpStatusCodeException | IOException e) {
			log.error("Could not get details of movie"+movie.getTitle());
			//e.printStackTrace();
			return songs;
		}
		//log.debug(page.asXml());
		DomNodeList <DomElement> tableList  =  page.getElementsByTagName("table");

		HtmlTable htmlTable=null;
		for(DomElement domElement:tableList){
			htmlTable=(HtmlTable) domElement;
			//System.out.println(">>"+htmlTable.getAttribute("class").toString());
			//System.out.println(htmlTable.getHeader().asXml());
			List <HtmlElement> rows = htmlTable.getHtmlElementsByTagName("tr");
			boolean isSongTable=false;
			//System.out.println(rows.get(0).asText());
			if(rows.get(0).asText().equals("Songs"))
				isSongTable=true;
			if (!isSongTable)
				continue;
			int track=1;
			for (HtmlElement row: rows) {

				if (row.getAttribute("class").length()==0){
					List <HtmlElement> cells= row.getElementsByTagName("td");
					int cellCnt=1;
					Song song=new Song();
					boolean isSong=false;
					for(HtmlElement cell:cells){
						//HtmlTableDataCell htmlCell = (HtmlTableDataCell) cell;
						//log.debug("Child Node" + htmlCell.hasChildNodes()+cellCnt);
						//System.out.println(htmlCell.getHeadersAttribute());
						//log.debug(cell.asXml());
						
						switch (cellCnt) {
						case 1: 
							HtmlAnchor htmlAnchor=null ;
							if (cell.getChildElements().iterator().hasNext()){
								boolean castExcep=false;
								try {
								htmlAnchor=(HtmlAnchor) cell.getChildElements().iterator().next();
								} catch (ClassCastException e){
									castExcep=true;
								}
								if (castExcep || 
										(htmlAnchor!=null
										&& htmlAnchor.getHrefAttribute().contains("playVideo"))){
									log.trace("Setting Title");
									//System.out.println("Play video cells");
									break;
								} else {
									log.debug("Setting Title");
									song.setTitle(cell.asText());
									isSong=true;
								}
							}
							break;
						case 2: 
							log.debug("Setting Music Director");
							song.setMusicDirector(cell.asText());
							break;
						case 3: 
							log.debug("Setting Lyricist");
							song.setLyricist(cell.asText());
							break;
						case 4: 
							log.debug("Setting Singers");
							song.setSingers(cell.asText());
							break;
						default: 
							log.trace("Default Case");
							break;
						}
						cellCnt++;
						if(!isSong){
							log.debug("Breaking");
							break;
						}
						//log.debug("Not Breaking");
						
					}
					if (isSong&&(song.getTitle().length()!=0)){
						song.setMovie(movie);
						song.setTrack(String.valueOf(track));
						song.setLanguage("mal");
						song.setGenre(genre);
						
						log.debug(song.toString());
						songs.add(song);
						track++;
					}

				}

			}	    
		}

		return songs;

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		WebClient lWebClient = new WebClient(BrowserVersion.CHROME);
		// webClient.set
		lWebClient.getOptions().setThrowExceptionOnScriptError(false);
		lWebClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("handleRefresh");
			}

		});
		System.out.println("Starting");
		HtmlPage page = (HtmlPage) 
				lWebClient.getPage(loginUrl);
		CookieManager cookieManager = lWebClient.getCookieManager();
		Boolean isEnglishSet=false;
		for(Cookie cookie:cookieManager.getCookies()){
			if (cookie.getName().equals("Language")){
				isEnglishSet=true;
				System.out.println("Engalish Set");
			}
		}
		cookieManager=null;

		if(!isEnglishSet){
			System.out.println("English Not Set");
			Thread.sleep(2000);
			page = (HtmlPage) 
					lWebClient.getPage(loginUrl+"/index.php?cl=1");
			System.out.println("Language Changed");
		}
		cookieManager = lWebClient.getCookieManager();
		isEnglishSet=false;
		for(Cookie cookie:cookieManager.getCookies()){
			if (cookie.getName().equals("Language")){
				isEnglishSet=true;
				System.out.println("English Set after");
			}
		}
		if(!isEnglishSet){
			System.out.println("English Not Set yet");
		}
		Thread.sleep(2000);
		/* Get Movie List Start */
		String movieString="7th Day";
		String tmpUrl = url+movieString.replaceAll(" ", "%20");
		System.out.println(tmpUrl);
		page = (HtmlPage) 
				lWebClient.getPage(tmpUrl);
		log.debug(page.asXml());
		DomNodeList <DomElement> tableList  =  page.getElementsByTagName("table");

		HtmlTable htmlTable=null;
		for(DomElement domElement:tableList){
			htmlTable=(HtmlTable) domElement;
			System.out.println(htmlTable.getAttribute("class").toString());
			List <HtmlElement> rows = htmlTable.getHtmlElementsByTagName("tr");
			for (HtmlElement row: rows) {

				if (row.getAttribute("class").equals("ptableslist")){
					List <HtmlElement> cells= row.getElementsByTagName("td");
					int cellCnt=1;
					for(HtmlElement cell:cells){
						HtmlTableDataCell htmlCell = (HtmlTableDataCell) cell;
						System.out.println(htmlCell.getHeadersAttribute());
						if (cellCnt==1){
							//System.out.println(cell.asXml());
							HtmlAnchor htmlAnchor = (HtmlAnchor) cell.getChildElements().iterator().next();
							System.out.println(htmlAnchor.getHrefAttribute());
						}
						System.out.println(cellCnt +htmlCell.asText());
						cellCnt++;



					}

				}

			}	    
		}
		/*Get movie list end*/
		
		page = (HtmlPage) 
				lWebClient.getPage(loginUrl+"/m.php?7586");
		log.debug(page.asXml());
		tableList  =  page.getElementsByTagName("table");

		htmlTable=null;
		for(DomElement domElement:tableList){
			htmlTable=(HtmlTable) domElement;
			System.out.println(">>"+htmlTable.getAttribute("class").toString());
			//System.out.println(htmlTable.getHeader().asXml());
			List <HtmlElement> rows = htmlTable.getHtmlElementsByTagName("tr");
			boolean isSongTable=false;
			System.out.println(rows.get(0).asText());
			if(rows.get(0).asText().equals("Songs"))
				isSongTable=true;
			if (!isSongTable)
				continue;
			for (HtmlElement row: rows) {
				
				if (row.getAttribute("class").length()==0){
					List <HtmlElement> cells= row.getElementsByTagName("td");
					int cellCnt=1;
					for(HtmlElement cell:cells){
						HtmlTableDataCell htmlCell = (HtmlTableDataCell) cell;
						System.out.println(htmlCell.getHeadersAttribute());
						if (cellCnt==1){
							//System.out.println(cell.asXml());
							HtmlAnchor htmlAnchor = (HtmlAnchor) cell.getChildElements().iterator().next();
							System.out.println(htmlAnchor.getHrefAttribute());
							if (htmlAnchor.getHrefAttribute().contains("playVideo")){
								System.out.println("Play video cells");
								break;
							}
						}
						System.out.println(cellCnt +htmlCell.asText());
						cellCnt++;



					}

				}

			}	    
		}
		
		
		System.out.println("Done");
	}

}
