// Name: Suhaib Khan
// Student ID: 501112462
import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		// Loop through arraylist and print the index, and print the info of the content at each index
		// Index labeled is i+1 because we cannot state the index starts at 0
		for(int i = 0; i<contents.size(); i++)
		{
			System.out.print((i+1) + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		// Loop through arraylist and play the AudioContent from each index
		for(int i = 0; i<contents.size(); i++)
		{
			contents.get(i).play();
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		// Return nothing if index is not in the correct range
		if (!contains(index))
		{
			System.out.println("Invalid");
			return;
		} 
		// Play AudioContent from the arraylist using the play method
		contents.get(index-1).play(); 
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		// check if other is a playlist object
		if(other instanceof Playlist)
		{
			// create new playlist variable and compare titles, return true if titles match
			Playlist other2 = (Playlist) other;
			return this.getTitle().equals(other2.getTitle()); 
		}
		return false;
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		// return nothing if index is not in correct range
		if (!contains(index)) return; 
		// delete content at index
		contents.remove(index-1);
	}
	
	
}
