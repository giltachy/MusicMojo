package com.giltachy.MusicTagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;

import com.giltachy.SongsDb.Movie;
import com.giltachy.SongsDb.Song;


// TODO: Auto-generated Javadoc
/**
 * The Class MusicTagger.
 * This class enables the management of ID3 Tags
 * 
 */
public class MusicTagger {
	
	/** The log. */
	static Logger log = Logger.getLogger(MusicTagger.class.getName());
	

	/**
	 * Instantiates a new music tagger.
	 */
	public MusicTagger() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	/**
	 * Delete id3 v1 tags.
	 *
	 * @param mp3FileIn the mp3 file in
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void deleteId3V1Tags(File mp3FileIn) throws 
	FileNotFoundException, IOException, CannotWriteException, 
	TagException, ReadOnlyFileException, InvalidAudioFrameException{
		MP3File mp3File=new MP3File(mp3FileIn);
		if (mp3File.hasID3v1Tag()){
			log.debug("Has ID3V1 tag");
			ID3v1Tag id3V1Tag= mp3File.getID3v1Tag();
			mp3File.delete(id3V1Tag);
		} else {
			log.debug("does not have ID3V1 tag");
		}
		mp3File.commit();
		mp3File.save();
		
		
	}
	
	/**
	 * Delete id3 v1 tags.
	 *
	 * @param mp3FileName the mp3 file name
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void deleteId3V1Tags(String mp3FileName) throws 
	FileNotFoundException, IOException, CannotWriteException, 
	TagException, ReadOnlyFileException, InvalidAudioFrameException{
		File mp3FileIn = new File(mp3FileName);
		deleteId3V1Tags(mp3FileIn);
	}
	
	/**
	 * Delete id3 v2 tags.
	 *
	 * @param mp3FileIn the mp3 file in
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void deleteId3V2Tags(File mp3FileIn) throws 
	FileNotFoundException, IOException, CannotWriteException, 
	TagException, ReadOnlyFileException, InvalidAudioFrameException{
		MP3File mp3File=new MP3File(mp3FileIn);
		if (mp3File.hasID3v2Tag()){
			log.debug("Has ID3V2 tag");
			AbstractID3Tag id3V2Tag= mp3File.getID3v2Tag();
			mp3File.delete(id3V2Tag);
		} else {
			log.debug("does not have ID3V2 tag");
		}
		mp3File.commit();
		mp3File.save();
		
		
	}
	
	/**
	 * Delete id3 v2 tags.
	 *
	 * @param mp3FileName the mp3 file name
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void deleteId3V2Tags(String mp3FileName) throws 
	FileNotFoundException, IOException, CannotWriteException, 
	TagException, ReadOnlyFileException, InvalidAudioFrameException{
		
		File mp3FileIn = new File(mp3FileName);
		deleteId3V2Tags(mp3FileIn);
		
	}
	
	/**
	 * Delete all tags.
	 *
	 * @param mp3FileName the mp3 file name
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CannotWriteException the cannot write exception
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static void deleteAllTags(String mp3FileName) throws
	FileNotFoundException, IOException, CannotWriteException, 
	TagException, ReadOnlyFileException, InvalidAudioFrameException{
		//File mp3FileIn = new File(mp3FileName);
		deleteId3V2Tags(mp3FileName);
		deleteId3V1Tags(mp3FileName);
	}
	
	/**
	 * Update mp3 tags.
	 *
	 * @param song the song
	 * @param mp3FileIn the mp3 file in
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 * @throws CannotWriteException the cannot write exception
	 */
	public static void updateMp3Tags(Song song,String mp3FileIn) throws
	IOException, TagException, ReadOnlyFileException, 
	InvalidAudioFrameException, CannotWriteException{
		/*
		FieldKey.ALBUM;
		FieldKey.ALBUM_ARTIST;
		FieldKey.ARTIST;
		FieldKey.COMPOSER;
		FieldKey.GENRE;
		FieldKey.LANGUAGE;
		FieldKey.LYRICIST;
		FieldKey.TITLE;
		FieldKey.TRACK;
		FieldKey.YEAR;
		*/
		
		File file=new File(mp3FileIn);
		MP3File mp3File= new MP3File(file);
		ID3v1Tag id3V1Tag=null;
		if(mp3File.hasID3v1Tag()) {
			id3V1Tag = mp3File.getID3v1Tag();
		} else {
			id3V1Tag= new ID3v1Tag();
		}
		
		ID3v23Tag id3V2Tag = null;
		id3V2Tag = (ID3v23Tag) mp3File.getTagOrCreateDefault();
		//FieldKey.ALBUM;
		id3V1Tag.setField(FieldKey.ALBUM, song.getMovie().getTitle());
		id3V2Tag.setField(FieldKey.ALBUM, song.getMovie().getTitle());
		
		//FieldKey.ALBUM_ARTIST
		//id3V1Tag.setField(FieldKey.ALBUM_ARTIST,song.getSingers());
		id3V2Tag.setField(FieldKey.ALBUM_ARTIST,song.getSingers());
		
		//FieldKey.ARTIST;
		id3V1Tag.setField(FieldKey.ARTIST,song.getSingers());
		id3V2Tag.setField(FieldKey.ARTIST,song.getSingers());
		
		//FieldKey.COMPOSER;
		//id3V1Tag.setField(FieldKey.COMPOSER,song.getMusicDirector());
		id3V2Tag.setField(FieldKey.COMPOSER,song.getMusicDirector());
		
		//FieldKey.GENRE;
		id3V1Tag.setField(FieldKey.GENRE,song.getGenre());
		id3V2Tag.setField(FieldKey.GENRE,song.getGenre());
	
		//FieldKey.LANGUAGE;
		//id3V1Tag.setField(FieldKey.LANGUAGE,song.getLanguage());
		id3V2Tag.setField(FieldKey.LANGUAGE,song.getLanguage());
		
		//FieldKey.LYRICIST;
		//id3V1Tag.setField(FieldKey.LYRICIST,song.getLyricist());
		id3V2Tag.setField(FieldKey.LYRICIST,song.getLyricist());
		
		//FieldKey.TITLE;
		id3V1Tag.setField(FieldKey.TITLE,song.getTitle());
		id3V2Tag.setField(FieldKey.TITLE,song.getTitle());
		
		//FieldKey.TRACK;
		id3V1Tag.setField(FieldKey.TRACK,song.getTrack());
		id3V2Tag.setField(FieldKey.TRACK,song.getTrack());
		
		//FieldKey.YEAR;
		//id3V1Tag.setField(FieldKey.YEAR,song.getYear());
		id3V2Tag.setField(FieldKey.YEAR,song.getMovie().getYear() );
		
		mp3File.setID3v1Tag(id3V1Tag);
		mp3File.commit();
		mp3File.setID3v2Tag( id3V2Tag);
		mp3File.commit();
		mp3File.save();
		
	}
	
	/**
	 * Gets the mp3 tags.
	 *
	 * @param mp3FileName the mp3 file name
	 * @return the mp3 tags
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TagException the tag exception
	 * @throws ReadOnlyFileException the read only file exception
	 * @throws InvalidAudioFrameException the invalid audio frame exception
	 */
	public static Song getMp3Tags(String mp3FileName) throws 
	IOException, TagException, ReadOnlyFileException, 
	InvalidAudioFrameException{
		Song song=new Song();
		File file=new File(mp3FileName);
		MP3File mp3File= new MP3File(file);
		ID3v23Tag id3V2Tag = null;
		if(!mp3File.hasID3v2Tag()){
			return song;
		}
		id3V2Tag = (ID3v23Tag) mp3File.getID3v2Tag();
		Movie movie=new Movie();
		//FieldKey.ALBUM;
		movie.setTitle(id3V2Tag.getFirst(FieldKey.ALBUM));
		if(movie.getTitle().length()==0 || movie.getTitle()==null){
			movie.setTitle("UNKOWNMOVIE");
		}
		
		//FieldKey.ARTIST
		song.setSingers(id3V2Tag.getFirst(FieldKey.ARTIST));
		
		//FieldKey.COMPOSER;
		song.setMusicDirector(id3V2Tag.getFirst(FieldKey.COMPOSER));
		
		//FieldKey.GENRE;
		song.setGenre(id3V2Tag.getFirst(FieldKey.GENRE));
	
		//FieldKey.LANGUAGE;
		//id3V1Tag.getField(FieldKey.LANGUAGE,song.getLanguage());
		song.setLanguage(id3V2Tag.getFirst(FieldKey.LANGUAGE));
		
		//FieldKey.LYRICIST;
		//id3V1Tag.getField(FieldKey.LYRICIST,song.getLyricist());
		song.setLyricist(id3V2Tag.getFirst(FieldKey.LYRICIST));
		
		//FieldKey.TITLE;
		song.setTitle(id3V2Tag.getFirst(FieldKey.TITLE));
		
		//FieldKey.TRACK;
		song.setTrack(id3V2Tag.getFirst(FieldKey.TRACK));
		
		//FieldKey.YEAR;
		movie.setYear(id3V2Tag.getFirst(FieldKey.YEAR ));
		try {
			Integer.parseInt(movie.getYear());
		} catch (NumberFormatException e){
			movie.setYear("0000");
		}
		song.setMovie(movie);
		return song;
	}


}
