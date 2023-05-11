import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			try
			{
			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int indexFrom = 0;
				int indexTo = 0;
				
				System.out.print("Starting from store content: ");
				if (scanner.hasNextInt())
				{
					// store the first inputted index
					indexFrom = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("Ending store content: ");
				if(scanner.hasNext())
				{
					// store last inputted index to indexTo
					indexTo = scanner.nextInt();
					scanner.nextLine();
				}
				// create arraylist of audiocontent to get store contents
				ArrayList<AudioContent> content = store.getContent(indexFrom,indexTo);
				if (content == null)
				{
					System.out.println("Content Not Found in Store");
				}
				mylibrary.download(content);					
			}
			else if(action.equalsIgnoreCase("DOWNLOADA")) // downloads all audiocontent with the specified artist/ author
			{
				System.out.print("Enter Artist/ Authors name whose content you'd like to download: ");
				String artist = "";
				if(scanner.hasNext())
				{
					artist = scanner.nextLine();
				}
				// arraylist created using method using artistMap
				ArrayList<AudioContent> content = store.getArtistContent(artist);
				if (content == null)
				{
					System.out.println("Content Not Found in Store");
				}
				mylibrary.download(content);
			}
			else if(action.equalsIgnoreCase("DOWNLOADG")) // downloads all songs with specified genre
			{
				System.out.print("Genre contents you would like to download: ");
				String genre = "";
				if(scanner.hasNext())
				{
					genre = scanner.nextLine();
				}
				// arraylist created using method using genreMap
				ArrayList<AudioContent> content = store.getGenreContent(genre);
				if (content == null)
				{
					System.out.println("Content Not Found in Store");
				}
				mylibrary.download(content);
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;
				// Print error message if the song doesn't exist in the library
				System.out.print("Song Number: ");
				if(scanner.hasNextInt())
				{
					// index of the song
					index  = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playSong(index);
			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;
			// Print error message if the book doesn't exist in the library
				System.out.print("Book Number: ");
				if(scanner.hasNextInt())
				{
					// gets index of book
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.printAudioBookTOC(index);
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;
				int chapt = 0;
				System.out.print("Book Number: ");
				if(scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				System.out.print("Chapter: ");
				if(scanner.hasNextInt())
				{
					chapt = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playAudioBook(index, chapt);

			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String title = "";
				System.out.print("Playlist Title: ");
				if(scanner.hasNextLine())
				{
					// gets title of playlist
					title = scanner.nextLine();
				}
				// check if there is a playlist with specified title
				mylibrary.playPlaylist(title);
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String title = "";
				int index = 0;
				System.out.print("Playlist Title: ");
				if(scanner.hasNextLine())
				{
					// gets title of playlist
					title = scanner.nextLine();
				}
				System.out.print("Library Number: ");
				if(scanner.hasNextInt())
				{
					// reads index of audio content
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playPlaylist(title, index);
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index = 0;
				System.out.print("Song Number: ");
				if(scanner.hasNextInt())
				{
					// index of the song
					index  = scanner.nextInt();
					scanner.nextLine();
				}
				// print error if song not in library
				mylibrary.deleteSong(index);
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";
				System.out.print("Title of Playlist: ");
				if(scanner.hasNextLine())
				{
					// this will be the title of the playlist
					title = scanner.nextLine();
				}
				mylibrary.makePlaylist(title);
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title;
				System.out.print("Playlist Name: ");
				if(scanner.hasNextLine())
				{
					title = scanner.nextLine();
					mylibrary.printPlaylist(title);
				}
				
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				String type = "";
				int index = 0;
				System.out.print("Playlist Name: ");
				if(scanner.hasNextLine())
				{
					title = scanner.nextLine();
					
					System.out.print("Content Type (Song or AudioBook): ");
					type = scanner.nextLine();
					
					System.out.print("Library Number: ");
					if(scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
						mylibrary.addContentToPlaylist(type, index, title);
					}
				}

			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				int index = 0;
				System.out.print("Playlist Name: ");
				if(scanner.hasNext())
				{
					title = scanner.nextLine();
					System.out.print("Index Number: ");
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.delContentFromPlaylist(index, title);
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}
			// NEW
			else if(action.equalsIgnoreCase("SEARCH")) // search audiocontent given the title
			{
				System.out.print("Title of Audiocontent: ");
				if(scanner.hasNext())
				{
					String title = scanner.nextLine();
					store.getTitleMap(title);
				}
			}
			else if(action.equalsIgnoreCase("SEARCHA")) // search audiocontent given the artist/ author
			{
				System.out.print("Name of Artist or Author: ");
				if(scanner.hasNext())
				{
					String artist = scanner.nextLine();
					store.getArtistMap(artist);
				}
			}
			else if(action.equalsIgnoreCase("SEARCHG")) // search song given the genre
			{
				System.out.print("Name of Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				if(scanner.hasNext())
				{
					String genre = scanner.nextLine();
					store.getGenreMap(genre);
				}
			}
			System.out.print("\n>");
			}
			// catch every exception found and return error message
			catch(ContentNotFoundException e)
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
			catch(PlaylistNotFoundException e)
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
			catch(ExistingPlaylistException e)
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
			catch(NullPointerException e)
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");	
			}
		}
		scanner.close();
	}
}
