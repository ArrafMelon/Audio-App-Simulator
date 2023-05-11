/*
 * A Song is a type of AudioContent. A Song has extra fields such as Artist (person(s) singing the song) and composer 
 */
public class Song extends AudioContent implements Comparable<Song> // implement the Comparable interface
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 		// Can be multiple names separated by commas
	private String composer; 	// Can be multiple names separated by commas
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional Song instance variables. 
		super(title, year, id, type, audioFile, length);
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
        this.lyrics = lyrics;    
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	// Print information about the song. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print artist, composer, genre 
	public void printInfo()
	{
		//Print out existing information using superclass in audiocontent
		super.printInfo();
		// Add on to existing information with info about the song (excluding the lyrics)
		System.out.println(" Artist: " + artist + " Composer: " + composer + " Genre: " + genre);
		System.out.println();
	}
	
	// Play the song by setting the audioFile to the lyrics string and then calling the play() method of the superclass
	public void play()
	{
		// Set audio file to lyrics
		setAudioFile(lyrics);
		// Call play method to play song
		super.play();
	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	// Two songs are equal if their AudioContent information is equal and both the composer and artists are the same
	// Make use of the superclass equals() method
	public boolean equals(Object other)
	{
		// check if other is a song object
		if(other instanceof Song)
		{
			// create new variable and compare to check if the two songs have all true comparisons
			Song song2 = (Song) other;
			return (super.equals(song2) && this.artist.equals(song2.artist) 
			&& this.composer.equals(song2.composer));
		}
		return false;
	}
	
	// Implement the Comparable interface 
	// Compare two songs based on their title
	// This method will allow songs to be sorted alphabetically
	public int compareTo(Song other)
	{
		// if the first string is larger than the second, 1 will be returned
		if(this.getTitle().compareTo(other.getTitle())>0)
		{
			return 1;
		}
		// else if the first string is shorter than the second, -1 will be returned
		else if(this.getTitle().compareTo(other.getTitle())<0)
		{
			return -1;
		}
		return 0;
	}
}
