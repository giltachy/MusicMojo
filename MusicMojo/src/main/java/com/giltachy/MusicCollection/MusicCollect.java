package com.giltachy.MusicCollection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.giltachy.MusicTagger.MusicTagger;
import com.giltachy.SongsDb.Movie;
import com.giltachy.SongsDb.Song;
import com.giltachy.SongsDb.MalayalamDb.MalayalamSongsDb;

// 
/**
 * The Class MusicCollect.
 */
public class MusicCollect {

	/** The log. */
	static Logger log = Logger.getLogger(
			MusicCollect.class.getName());
	final static String  applicationName ="MusicMojo";
	static String processMode="";
	static String workingDirectory="";
	static Boolean helpRequired=false;
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String args[]) throws FileNotFoundException, 
	IOException, CannotWriteException, TagException, ReadOnlyFileException,
	InvalidAudioFrameException, FailingHttpStatusCodeException, 
	InterruptedException{
		

		String rootFolder ="";
	//	Writer writer = null;
	//	String mode="PROCESS";
		 displayBlankLines(1, System.out);
	     displayHeader(System.out);
	     displayBlankLines(2, System.out);
	     
	     useGnuParser(args);
	    if(helpRequired){
	    	printUsage( applicationName, constructGnuOptions(), System.out);
			 printHelp(
			            constructGnuOptions(), 80, "Help", "End of Help",
			               5, 3, true, System.out);
		} else if(processMode.length()==0 || processMode.isEmpty() ){
			
			 System.out.println("Oops! you missed mentionting the processing mode");
			 printHelp(
			            constructGnuOptions(), 80, "Help", "End of Help",
			               5, 3, true, System.out);
		} else 	if(workingDirectory.length()==0 || workingDirectory.isEmpty() ){
			
			 System.out.println("Oops! you missed providing the working directory");
			 printHelp(
			            constructGnuOptions(), 80, "Help", "End of Help",
			               5, 3, true, System.out);
		}
	    rootFolder=workingDirectory;
	    processMode=processMode.toLowerCase();
	    switch (processMode){
	    case "tag":
	    	processSongs(rootFolder);
	    	break;
	    case "org":
	    	organiseMp3File(rootFolder);
	    	break;
	    case "list":
	    	File outputFile= new File(rootFolder+"/SongList.txt");
			 BufferedWriter output = null;
	
			try {
				
				output=	 new BufferedWriter(new FileWriter(outputFile));
				getAllMp3Files(rootFolder,output);
			} catch (IOException ex) {
			  // report
			} finally {
			   try {output.close();} catch (Exception ex) {}
			}
			break;
		default:
			System.out.println("Sorry! I don't know to process your request "
					+processMode);
			 printHelp(
			            constructGnuOptions(), 80, "Help", "End of Help",
			               5, 3, true, System.out);
	    }
	    	
		
		if(rootFolder.equals("a")){
			//code to list all mp3 files
			File outputFile= new File(rootFolder+"/SongList.txt");
			 BufferedWriter output = null;
	
			try {
				
				output=	 new BufferedWriter(new FileWriter(outputFile));
				getAllMp3Files(rootFolder,output);
			} catch (IOException ex) {
			  // report
			} finally {
			   try {output.close();} catch (Exception ex) {}
			} 
		}
		
//		** MusicTagger.deleteAllTags(rootFolder); // Removes all tags from MP3
//		processSongs(rootFolder); // Check the online DB and update the tags
//		organiseMp3File(rootFolder); // Copies Songs by Year and Album Name


//		List<Movie> movies = songsDb.getMoives("7th Day");
//		Collections.sort(movies, new Movie());
//		System.out.println(movies.get(0).toString());
//		ArrayList<Song> songs=songsDb.getMovieSongs(movies.get(0));
//		System.out.println(songs.get(0).toString());
//		MusicTagger.updateMp3Tags(songs.get(0), rootFolder);
		System.out.println("I've finished my job; enjoy the songs :)");

	}
	
	 /**
	    * Apply Apache Commons CLI GnuParser to command-line arguments.
	    * 
	    * @param commandLineArguments Command-line arguments to be processed with
	    *    Gnu-style parser.
	    */
	   public static void useGnuParser(final String[] commandLineArguments)
	   {
	      final CommandLineParser cmdLineGnuParser = new GnuParser();

	      final Options gnuOptions = constructGnuOptions();
	      CommandLine commandLine;
	      try
	      {
	         commandLine = cmdLineGnuParser.parse(gnuOptions, commandLineArguments);
	         if ( commandLine.hasOption("help") )
	         {
	            helpRequired=true;
	         }
	         if ( commandLine.hasOption("mode") )
	         {
	        	  processMode= commandLine.getOptionValue("");
	         }
	         if ( commandLine.hasOption("working-directory") )
	         {
	            workingDirectory=commandLine.getOptionValue("");
	         }
	         
	      }
	      catch (ParseException parseException)  // checked exception
	      {
	         System.err.println(
	              "Encountered exception while parsing using GnuParser:\n"
	            + parseException.getMessage() );
	      }
	   }
	   
	   /**
	    * Construct and provide GNU-compatible Options.
	    * 
	    * @return Options expected from command-line of GNU form.
	    */
	   public static Options constructGnuOptions()
	   {
	      final Options gnuOptions = new Options();
	      gnuOptions.addOption("m", "mode", true, "Mode of processing the files viz tag/org/list")
	                .addOption("w", "working-directory", true, 
	                		"Working direcoty where mp3 files are stored")
	                .addOption("h","help", false, "display help message");
	      return gnuOptions;
	   }
	   
	   /**
	    * Display command-line arguments without processing them in any further way.
	    * 
	    * @param commandLineArguments Command-line arguments to be displayed.
	    */
	   public static void displayProvidedCommandLineArguments(
	      final String[] commandLineArguments,
	      final OutputStream out)
	   {
	      final StringBuffer buffer = new StringBuffer();
	      for ( final String argument : commandLineArguments )
	      {
	         buffer.append(argument).append(" ");
	      }
	      try
	      {
	         out.write((buffer.toString() + "\n").getBytes());
	      }
	      catch (IOException ioEx)
	      {
	         System.err.println(
	            "WARNING: Exception encountered trying to write to OutputStream:\n"
	            + ioEx.getMessage() );
	         System.out.println(buffer.toString());
	      }
	   }
	   
	   /**
	    * Display example application header.
	    * 
	    * @out OutputStream to which header should be written.
	    */
	   public static void displayHeader(final OutputStream out)
	   {
	      final String header =
	           "MusicMojo: App for enabling tagging of Malayalam MP3 songs";
	      try
	      {
	         out.write(header.getBytes());
	      }
	      catch (IOException ioEx)
	      {
	         System.out.println(header);
	      }
	   }

	   /**
	    * Write the provided number of blank lines to the provided OutputStream.
	    * 
	    * @param numberBlankLines Number of blank lines to write.
	    * @param out OutputStream to which to write the blank lines.
	    */
	   public static void displayBlankLines(
	      final int numberBlankLines,
	      final OutputStream out)
	   {
	      try
	      {
	         for (int i=0; i<numberBlankLines; ++i)
	         {
	            out.write("\n".getBytes());
	         }
	      }
	      catch (IOException ioEx)
	      {
	         for (int i=0; i<numberBlankLines; ++i)
	         {
	            System.out.println();
	         }
	      }
	   }

	   /**
	    * Print usage information to provided OutputStream.
	    * 
	    * @param applicationName Name of application to list in usage.
	    * @param options Command-line options to be part of usage.
	    * @param out OutputStream to which to write the usage information.
	    */
	   public static void printUsage(
	      final String applicationName,
	      final Options options,
	      final OutputStream out)
	   {
	      final PrintWriter writer = new PrintWriter(out);
	      final HelpFormatter usageFormatter = new HelpFormatter();
	      usageFormatter.printUsage(writer, 80, applicationName, options);
	      writer.flush();
	   }

	   /**
	    * Write "help" to the provided OutputStream.
	    */
	   public static void printHelp(
	      final Options options,
	      final int printedRowWidth,
	      final String header,
	      final String footer,
	      final int spacesBeforeOption,
	      final int spacesBeforeOptionDescription,
	      final boolean displayUsage,
	      final OutputStream out)
	   {
	      final String commandLineSyntax = "java -cp MusicMojo.jar";
	      final PrintWriter writer = new PrintWriter(out);
	      final HelpFormatter helpFormatter = new HelpFormatter();
	      helpFormatter.printHelp(
	         writer,
	         printedRowWidth,
	         commandLineSyntax,
	         header,
	         options,
	         spacesBeforeOption,
	         spacesBeforeOptionDescription,
	         footer,
	         displayUsage);
	      writer.flush();
	   }
	
	/**
	 * Process songs.
	 * Traverse through root folder , pick all mp3File and update ID3 tag
	 * @param rootFolder the root folder
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void processSongs(String rootFolder) throws 
	FailingHttpStatusCodeException, IOException, InterruptedException,
	CannotWriteException, TagException, ReadOnlyFileException, 
	InvalidAudioFrameException{
		
		//Get the list of sub folder; this will be the movie name
		ArrayList<String> movieFolders=getFolders(rootFolder);
		log.trace("Fetched the movie folders");
		//Initialise songs DB
		MalayalamSongsDb songsDb= new MalayalamSongsDb();
		//Loop through the movieFolders
		for(String movieFolder:movieFolders){
			System.out.println(movieFolder);
			//Get the list of movie from DB
			List<Movie> movies = songsDb.getMoives(movieFolder);
			if(movies.isEmpty()) {
				log.warn("No movie with name " + movieFolder );
				//No movie in DB??? continue to next one
				continue;
			}
			Collections.sort(movies, new Movie());
			
			int movieCount=0;
			for(Movie movie:movies){
				movieCount++;
				// List all movie from the DB
				String movieNum=
						StringUtils.leftPad
						(String.valueOf(movieCount),3," ");
				System.out.println
				(movieNum+"---->"+movie.toString());
			}
			//Get the matching movie from the music buff
			int movieSelect=getMovieNumber(movies.size());
			if(movieSelect<=0){
				//Movie not in the list ???
				log.warn("SKIPPED Movie"+movieFolder);
				continue;
			}
			//Get songs for the movie
			List<Song> songs=
					songsDb.getMovieSongs(movies.get(movieSelect-1));
			if(songs.isEmpty()){
				// Sorry! 
				log.warn("Sorry! No songs for movie" + 
						movies.get(movieSelect-1).getTitle());
				continue;
			}
			
			//Get all mp3Files from movie folder
			ArrayList<String> songFiles=
					getMp3Files(rootFolder+"/"+movieFolder);
			for(String songFile:songFiles){
				System.out.println("Please match song  for <<" + songFile+">>");
				int songCount=0;
				for(Song song:songs){
					songCount++;
					String songNum=
							StringUtils.leftPad
							(String.valueOf(songCount),3," ");
					System.out.println
					(songNum+"---->"+song.toString());
				}
				int songSelect=getSongNumber(songs.size());
				if(songSelect<=0){
					//No songs to match
					log.warn("SKIPPED song"+rootFolder+"/"+movieFolder
							+"/"+songFile);
					continue;
				}
				MusicTagger.deleteAllTags(rootFolder+"/"+movieFolder
						+"/"+songFile);
				MusicTagger.updateMp3Tags(songs.get(songSelect-1), 
						rootFolder+"/"+movieFolder+"/"+songFile);
			}
		}
	}
	
	/**
	 * Gets the folders.
	 *
	 * @param parentFolder the parent folder
	 * @return the folders
	 */
	public static ArrayList<String> getFolders(String parentFolder){
		ArrayList<String> folders=new ArrayList<String>();
		File lParentFolder = new File(parentFolder);

		String[] children = lParentFolder.list();
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			File subFolder =null;
			for (int i=0; i < children.length; i++) {
				// Get filename of file or directory
				subFolder=new File(parentFolder+"/"+children[i]);
				if(subFolder.isDirectory()){
					log.debug("Sub Folder found"+children[i]);
					folders.add(children[i]);
				}
			}
		}
		lParentFolder=null;
		log.info("I found " + folders.size()+" folder(s)" );
		return folders;

	}
	
	/**
	 * Gets the mp3 files.
	 *
	 * @param parentFolder the parent folder
	 * @return the mp3 files
	 */
	public static ArrayList<String> getMp3Files(String parentFolder){
		ArrayList<String> mp3files=new ArrayList<String>();
		File lParentFolder = new File(parentFolder);

		String[] children = lParentFolder.list();
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			File mp3file =null;
			for (int i=0; i < children.length; i++) {
				// Get filename of file or directory
				mp3file=new File(parentFolder+"/"+children[i]);
				if(!mp3file.isDirectory()&&
						children[i].toLowerCase()
						.endsWith(".mp3")){
					log.debug("Sub Folder found"+children[i]);
					mp3files.add(children[i]);
				}
			}
		}
		lParentFolder=null;
		
		log.info("I found " + mp3files.size()+" mp3 file(s)" );

		return mp3files;

	}

	/**
	 * Gets the movie number.
	 *
	 * @param movieCount the movie count
	 * @return the movie number
	 */
	public static int getMovieNumber(int movieCount){
		int movieNumber=-1;


		try{
			while(movieNumber <0) {
				System.out.print("Please select movie number [1 and "+movieCount+"]; 0 to exit : ");
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				try {
					movieNumber=Integer.parseInt(s);
					if ((movieNumber<0) || (movieNumber >movieCount)){
						System.out.println("Invalid selection;Please retry");
						movieNumber =-1;
					}
				} catch(NumberFormatException e) {
					System.out.println("Please choose a number;Please retry");
					movieNumber=-1;
				}
			}

			//System.out.println(s);
		}
		catch(IOException e)
		{
			//e.printStackTrace();
		}
		return movieNumber;
	}
	
	/**
	 * Gets the song number.
	 *
	 * @param songCount the song count
	 * @return the song number
	 */
	public static int getSongNumber(int songCount){
		int songNumber=-1;


		try{
			while(songNumber <0) {
				System.out.print("Please select song number [1 and "+songCount+"]; 0 to exit : ");
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				try {
					songNumber=Integer.parseInt(s);
					if ((songNumber<0) || (songNumber >songCount)){
						System.out.println("Invalid selection;Please retry");
						songNumber =-1;
					}
				} catch(NumberFormatException e) {
					System.out.println("Please choose a number;Please retry");
					songNumber=-1;
				}
			}

			//System.out.println(s);
		}
		catch(IOException e)
		{
			//e.printStackTrace();
		}
		return songNumber;
	}
	
	/**
	 * Assert action.
	 *
	 * @param message the message
	 * @param ignoreCase the ignore case
	 * @return true, if successful
	 */
	public static boolean assertAction(String message,boolean ignoreCase){
		boolean choice=false;


				String caseSensitive=ignoreCase?"":"(Case sensitive)";
				
				System.out.println("Please type > "+message + " < to continue"+caseSensitive);
				System.out.print("Your consent here:");
				
				
				try {
					BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String s = bufferRead.readLine();
					
						if ((s.equals(message)||
								(s.equalsIgnoreCase(message)&&ignoreCase)))
							choice=true;
				} catch (IOException e) {
					System.out.println("Invalid choice....");
					//e.printStackTrace();
				}
					
			


		return choice;
	}
	
	/**
	 * Organise mp3 file.
	 *
	 * @param rootFolder the root folder
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void organiseMp3File(String rootFolder) throws
	IOException, TagException, ReadOnlyFileException, 
	InvalidAudioFrameException{
		System.out.println("****************************************************");
		System.out.println("*           #########################              *");
		System.out.println("*           #    IMPORTANT NOTICE   #              *");
		System.out.println("*           #########################              *");
		System.out.println("* This will movie mp3 files by reading the tags    *");
		System.out.println("* Source: <root>/<movie>/<mp3file>                 *");
		System.out.println("* Tagert: <root>/Organise/<year>/<movie>/<mp3file> *");
		System.out.println("****************************************************");
		System.out.println("");
		if(!assertAction("I aCcePt", false)){
			System.out.println("As you wish;not contiuing with " + rootFolder);
			log.warn("As you wish;not contiuing with " + rootFolder);
			return;
		}
		
		System.out.println("Organising " + rootFolder);
		log.warn("Organising " + rootFolder);
		
		Path rootPath= Paths.get(rootFolder);
		if(!(Files.exists(rootPath)&&
				Files.isDirectory(rootPath))){
			log.error("I, either did not find " + rootFolder +
					" or it is not a folder path");
			return;
		}
		String organiseFolder=rootFolder+"/"+"Organise";
		Path organiseFolderPath= Paths.get(organiseFolder);
		if(!Files.exists(organiseFolderPath)){
			Files.createDirectories(organiseFolderPath);
		} else if (!Files.isDirectory(organiseFolderPath)){
			log.error(organiseFolder+" is not a folder path");
			return;			
		} 
		log.debug("Organise Path is " + organiseFolder);
		ArrayList<String> movieFolders=getFolders(rootFolder);
		log.trace("Fetched the movie folders");
		
		//Initialise songs DB
		//Loop through the movieFolders
		for(String movieFolder:movieFolders){
			System.out.println(movieFolder);
			//Get the list of movie from DB
			
	
			//Get the matching movie from the music buff
			
			//Get all mp3Files from movie folder
			ArrayList<String> songFiles=
					getMp3Files(rootFolder+"/"+movieFolder);
			for(String songFile:songFiles){
				//int songCount=0;
				String srcMp3FileName=rootFolder+"/"+movieFolder+"/"+songFile;
				Path srcMp3FilePath= Paths.get(srcMp3FileName);
				Song songSelect=
						MusicTagger.getMp3Tags(srcMp3FileName);
				String year=songSelect.getMovie().getYear();
				String movieName=songSelect.getMovie().getTitle();
				String yearPathName=organiseFolder+"/"+year;
				String moviePathName=yearPathName+"/"+movieName;
				String trgMp3FileName=moviePathName+"/"+songFile;
				Path trgMp3FilePath=Paths.get(trgMp3FileName);
				log.debug("Year Path is " + yearPathName);
				log.debug("Movie Path is " + moviePathName);
				log.debug("Target Mp3 file is " + trgMp3FileName);
				
						
				Path yearPath=Paths.get(yearPathName);
				if(!Files.exists(yearPath)){
					Files.createDirectories(yearPath);
				} else if(!Files.isDirectory(yearPath)){
					log.error("NOT ORGANISING:" + yearPathName+ 
							"is not a valid folder; not moving "
							+srcMp3FileName);
					return;
				}
				
				Path trgMoviePath=Paths.get(moviePathName);
				if(!Files.exists(trgMoviePath)){
					Files.createDirectories(trgMoviePath);
				} else if(!Files.isDirectory(trgMoviePath)){
					log.error("NOT ORGANISING:" + moviePathName+ 
							"is not a valid folder; not moving "
							+srcMp3FileName);
					return;
				}
				
				Files.move(srcMp3FilePath,trgMp3FilePath);
				
				
			}
		}
		
	}
	
	
	public static void getAllMp3Files(String parentFolder, 
			BufferedWriter output ) throws
	IOException, TagException, ReadOnlyFileException, 
	InvalidAudioFrameException{
		File lParentFolder = new File(parentFolder);

		String[] children = lParentFolder.list();
		if (children == null) {
			log.debug("Children null ");
			// Either dir does not exist or is not a directory
		} else {
			log.debug("Children length of  " + 
					lParentFolder.getAbsolutePath()+" is "
						+ children.length);
			for (int i=0; i < children.length; i++) {
				// Get filename of file or directory
				File subFolder =new File(parentFolder+"/"+children[i]);
				if(subFolder.isDirectory()){
					//log.debug("Sub Folder found "+subFolder.getAbsolutePath());
					getAllMp3Files(subFolder.getAbsolutePath(),output);
					//log.debug("Sub Folder found  " + folders.size() +" sub folders");
				} else if(
						subFolder.getAbsolutePath().
						toLowerCase().endsWith("mp3")){
					log.debug("FILE NAME: " +subFolder.getAbsolutePath());
					Song song= MusicTagger.getMp3Tags(subFolder.getAbsolutePath());
					song.setFileName(subFolder.getAbsolutePath());
					String detailString = 
							song.getTitle()+"|"+
							song.getSingers()+"|"+
							song.getMusicDirector()+"|"+
							song.getMovie().getTitle()+"|"+
							song.getMovie().getYear()+"|"+
							song.getLanguage()+"|"+
							song.getFileName();
					output.write(detailString);
					output.write(System.getProperty("line.separator"));
					//folders.add(song);
				}
			}
		
		}
		lParentFolder=null;

	}
	
	
}
