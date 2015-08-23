package com.giltachy.SongsDb;

import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class Song represents the songs in a movie.
 */
public class Song implements Comparator<Song>{
	
	/** The title. */
	private String title;
	
	/** The music director. */
	private String musicDirector;
	
	/** The lyricist. */
	private String lyricist;
	
	/** The singers. */
	private String singers;
	
	/** The movie. */
	private Movie movie;
	
	/** The language. */
	private String language;
	
	/** The genre. */
	private String genre;
	
	/** The track. */
	private String track;
	
	
	/** The file name. */
	private String fileName;
	
	
	/**
	 * Instantiates a new song.
	 */
	public Song() {

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Song [title=" + title + "| musicDirector=" + musicDirector
				+ "| lyricist=" + lyricist + "| singers=" + singers
				+ "| movie=[" + movie.toString() + "]| language=" + language + "| genre="
				+ genre + "| track=" + track + "]";
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the music director.
	 *
	 * @return the music director
	 */
	public String getMusicDirector() {
		return musicDirector;
	}

	/**
	 * Sets the music director.
	 *
	 * @param musicDirector the new music director
	 */
	public void setMusicDirector(String musicDirector) {
		this.musicDirector = musicDirector;
	}

	/**
	 * Gets the lyricist.
	 *
	 * @return the lyricist
	 */
	public String getLyricist() {
		return lyricist;
	}

	/**
	 * Sets the lyricist.
	 *
	 * @param lyricist the new lyricist
	 */
	public void setLyricist(String lyricist) {
		this.lyricist = lyricist;
	}

	/**
	 * Gets the singers.
	 *
	 * @return the singers
	 */
	public String getSingers() {
		return singers;
	}

	/**
	 * Sets the singers.
	 *
	 * @param singers the new singers
	 */
	public void setSingers(String singers) {
		this.singers = singers;
	}

	/**
	 * Gets the movie.
	 *
	 * @return the movie
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * Sets the movie.
	 *
	 * @param movie the new movie
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the genre.
	 *
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Sets the genre.
	 *
	 * @param genre the new genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Gets the track.
	 *
	 * @return the track
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * Sets the track.
	 *
	 * @param track the new track
	 */
	public void setTrack(String track) {
		this.track = track;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Song firstSong, Song secondSong) {
		//Compare titles of song
		return firstSong.getTitle().compareTo(secondSong.getTitle());
	}


}
