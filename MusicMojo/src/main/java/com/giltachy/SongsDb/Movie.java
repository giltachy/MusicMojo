/*
 * 
 */
package com.giltachy.SongsDb;

import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * This class represents the details of a Movie.
 */
public class Movie implements Comparator<Movie> {
	
	/** The title. */
	private String title;
	
	/** The Year of Release. */
	private String Year;
	
	/** The music director. */
	private String musicDirector;
	
	/** The lyricist. */
	private String lyricist;
	
	/** The director. */
	private String director;
	
	/** The link. */
	private String link;

	

	/**
	 * Instantiates a new movie.
	 */
	public Movie() {
	
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
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear() {
		return Year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year) {
		Year = year;
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
	 * Gets the director.
	 *
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Sets the director.
	 *
	 * @param director the new director
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	
	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link the new link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Movie [Title=" + title + "| Year=" + Year + "| MusicDirector="
				+ musicDirector + "| Lyricist=" + lyricist + "| Director="
				+ director + "| Link=" + link + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Year == null) ? 0 : Year.hashCode());
		result = prime * result
				+ ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((lyricist == null) ? 0 : lyricist.hashCode());
		result = prime * result
				+ ((musicDirector == null) ? 0 : musicDirector.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (Year == null) {
			if (other.Year != null)
				return false;
		} else if (!Year.equals(other.Year))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (lyricist == null) {
			if (other.lyricist != null)
				return false;
		} else if (!lyricist.equals(other.lyricist))
			return false;
		if (musicDirector == null) {
			if (other.musicDirector != null)
				return false;
		} else if (!musicDirector.equals(other.musicDirector))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Movie firstMovie, Movie secondMovie) {
		//Compares tile of movies
		return firstMovie.getTitle().compareTo(secondMovie.getTitle());
	}

}
