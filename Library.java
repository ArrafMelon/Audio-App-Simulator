import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.IIOException;
import javax.lang.model.util.ElementScanner14;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	public void download(ArrayList<AudioContent> content)
	{
		// check if content is song typename
		for(int i  = 0; i < content.size(); i++)
		{
			if(content.get(i).getType().equals(Song.TYPENAME))
			{
				// if content is already in list, error message will pop, else it will add it
				if(songs.contains(content.get(i)))
				{
					System.out.println(content.get(i).getTitle()+" is already downloaded");
				}
				else
				{
					songs.add((Song) content.get(i));
					System.out.println(content.get(i).getTitle() + " added to library");
				}
			}
			else if(content.get(i).getType().equals(AudioBook.TYPENAME))
			{
				if(audiobooks.contains((AudioBook) content.get(i)))
				{
					System.out.println(content.get(i).getTitle() + " is already downloaded");;
				}
				else
				{
					audiobooks.add((AudioBook) content.get(i));
					System.out.println(content.get(i).getTitle() + " added to library");
				}
		}
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			System.out.print("" + (i+1) + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		// loop through array list and use printInfo() for each element
		for(int i = 0; i<audiobooks.size(); i++)
		{
			System.out.print(" " + (i+1) + ": ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
    // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for(int i = 0; i<playlists.size(); i++)
		//for each index print out the index number, and the title of the playlist
		System.out.print(" " + (i+1) + ": " + playlists.get(i).getTitle());
		System.out.println();
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> allArtist = new ArrayList<>();
		for(int i = 0; i<songs.size(); i++)
		{
			if(!allArtist.contains(songs.get(i).getArtist()))
			{
				allArtist.add(songs.get(i).getArtist());
			}
		}
		for(int i = 0; i<songs.size(); i++)
		{
			System.out.println((i+1) + ". " + songs.get(i).getArtist());
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) throws IndexOutOfBoundsException
	{
		// checks if index is out of range
		if(index < 1 || index > songs.size())
		{
			throw new IndexOutOfBoundsException("Invalid index");
		}
		// create new song variable and use the remove method to get rid of it
		Song songRemove = songs.get(index-1);
		songs.remove(songRemove);
		// Use a for loop to remove the specific song in the playlist as well
		for(int i = 0; i<playlists.size(); i++)
		{
			playlists.get(i).deleteContent(i+1);
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		// Sort song by year
		Collections.sort(songs,new SongYearComparator());
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		// create class and compare the difference of year in song number 1 and 2 by subtracting
		public int compare(Song s1, Song s2)
		{
			return s1.getYear() - s2.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
		 // Use Collections.sort() 
	 	Collections.sort(songs, new SongLengthComparator());
	}
    // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		// create class and compare the difference in length of the two songs by subtracting
		public int compare(Song s1, Song s2)
		{
			return(s1.getLength() - s2.getLength());
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
	  Collections.sort(songs);
		// class Song should implement the Comparable interface
		// see class Song code
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws ContentNotFoundException
	{
		if (index < 1 || index > songs.size())
		{
			throw new ContentNotFoundException("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) throws ContentNotFoundException
	{
		// check if index in range
		if(index < 1 || index > audiobooks.size())
		{
			throw new ContentNotFoundException("Audiobook not found");
		}
		// check if chapter is valid
		if(chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters())
		{
			throw new ContentNotFoundException("Chapter not found");
		}
		// chapter gets selected and played
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) throws ContentNotFoundException
	{
		// check if index is valid
		if(index < 1 || index > audiobooks.size())
		{
			throw new ContentNotFoundException("Audiobook does not exist");
		}
		// print the chapter titles using audiobooks printTOC function
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) throws ExistingPlaylistException
	{
		// loop through playlists arraylist to check if there is already a same title
		for(int i = 0; i<playlists.size(); i++)
		{
			if(title.equals(playlists.get(i).getTitle()))
			{
				throw new ExistingPlaylistException("Playlist already exists");
			}
		}
		// create new playlist and add it into arraylist
		Playlist newPl = new Playlist(title);
		playlists.add(newPl);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws PlaylistNotFoundException
	{
		// loop over arraylist and if title equals a title in the list, we are able to print the contents
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(title))
			{
				playlists.get(i).printContents();
			}
			else
			{
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws PlaylistNotFoundException
	{
		// loop over arraylist and if title equals a title in the list, we can play all content in the playlist
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				playlists.get(i).playAll();
			}
			else
			{
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws ContentNotFoundException, PlaylistNotFoundException
	{
		// loop over array and play the specific song/book in the given index
		for(int i = 0; i<playlists.size(); i++)
		{
			if(playlists.get(i).getTitle().equals(playlistTitle))
			{
				// if index is not in the playlist, set error message
				if(!playlists.get(i).contains(indexInPL))
				{
					throw new ContentNotFoundException("Index not found");
				}
				playlists.get(i).play(indexInPL);
			}
			else
			{
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws IndexOutOfBoundsException
	{
		// create new playlist with the title of given parameter playlistTitle
		Playlist newpl = new Playlist(playlistTitle);
		if(type.equalsIgnoreCase(Song.TYPENAME))
		{	// add the song at the index to the specified playlist with the index of the given title
			if(index < 1 || index > songs.size())
		{
			throw new IndexOutOfBoundsException("Index not found");
		}
			this.playlists.get(this.playlists.indexOf(newpl)).addContent(this.songs.get(index-1));
		}
		else if(type.equalsIgnoreCase(AudioBook.TYPENAME))
		{	// add the song at the index to the specified playlist with the index of the given title
			if(index < 1 || index > audiobooks.size())
		{
			throw new IndexOutOfBoundsException("Index not found");
		}
			this.playlists.get(this.playlists.indexOf(newpl)).addContent(this.audiobooks.get(index-1));
		}
	}

  // Delete a song/audiobook from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws ContentNotFoundException
	{
		// loop through playlists arraylist
		for(int i = 0; i<playlists.size(); i++)
		{
			// if index is not in specified playlist, print error message
			if(!playlists.get(i).contains(index))
			{
				throw new ContentNotFoundException("Index not found");
			}
			// if playlist has the same title as in given parameter, delete the playlist at 
			//specified index and return true
			else if(playlists.get(i).getTitle().equals(title))
			{
				playlists.get(i).deleteContent(index);
				break;
			}
		}
	}
	
}
// create exception classes for errors which occur in the methods above
class ContentNotFoundException extends RuntimeException
{
	public ContentNotFoundException(){}
	public ContentNotFoundException(String s)
	{
		super(s);
	}
}
class PlaylistNotFoundException extends RuntimeException
{
	public PlaylistNotFoundException(){}
	public PlaylistNotFoundException(String s)
	{
		super(s);
	}
}
class ExistingPlaylistException extends RuntimeException
{
	public ExistingPlaylistException(){}
	public ExistingPlaylistException(String s)
	{
		super(s);
	}
}
