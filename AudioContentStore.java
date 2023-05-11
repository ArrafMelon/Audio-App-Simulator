import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> titleMap;
		private Map<String, ArrayList<Integer>> artistMap;
		private Map<String, ArrayList<Integer>> genreMap;

		// create private method to add store.txt contents in audiocontent arraylist
		private ArrayList<AudioContent> storeReader() throws FileNotFoundException
		{
			ArrayList<AudioContent> W = new ArrayList<>();
			File newFile = new File("store.txt"); // read in txt file
			Scanner scan = new Scanner(newFile); // create scanner variable
			while(scan.hasNextLine()) // loop over scanner variable
			{
				String type = scan.nextLine(); // next line is type of audiocontent
				if(type.equals("SONG")) // check if it is a song
				{
					String id = scan.nextLine(); // next line is id
					String title = scan.nextLine(); // next line is title
					int year = Integer.parseInt(scan.nextLine()); // next line is year
					int length = Integer.parseInt(scan.nextLine()); // next line is length
					String artist = scan.nextLine(); // next line is artist
					String composer = scan.nextLine(); // next line is composer
					String genre = scan.nextLine(); // next line is genre
					Song.Genre genre2 = Song.Genre.valueOf(genre); // make genre into Song.Genre variable 
					String lyrics = ""; // initialize lyrics
					int lineNum = Integer.parseInt(scan.nextLine()); // next line is how many lines the lyrics have
					for(int i = 0; i < lineNum; i++) // loop over the number of lines
					{
						lyrics += scan.nextLine() + "\n"; // add however many lines of lyrics into lyrics
					}
					// add new song instance into contents array list
					W.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer,genre2,lyrics));
				}

				else if(type.equals("AUDIOBOOK")) // check if content is audiobook type
				{
					String id = scan.nextLine(); // next line is id
					String title = scan.nextLine(); // next line is title
					int year = Integer.parseInt(scan.nextLine()); // next line is year
					int length = Integer.parseInt(scan.nextLine()); // next line is length
					String author = scan.nextLine(); // next line is author
					String narrator = scan.nextLine(); // next line is narrator
					int numberChap = Integer.parseInt(scan.nextLine()); // next line is number of chapters
					ArrayList<String> chapName = new ArrayList<>(); // create new variable to store names of chapters
					for(int i = 0; i < numberChap; i++) // loop over number of chapters
					{
						chapName.add(scan.nextLine()); // add the name of each chapter to arraylist
					}
					ArrayList<String> chapters = new ArrayList<>(); // create new arraylist to store text
					for(int i = 0; i < numberChap; i++) // loop over number of chapters
					{
						int lineNum = Integer.parseInt(scan.nextLine()); // this is how many lines each chapter has
						String story = ""; // create new string to store the text for each chapter
						for(int j = 0; j < lineNum; j++) // loop over the number of lines of each chapter
						{
							story += scan.nextLine() + "\n"; // add each line to story
						}
						chapters.add(story); // add story to the arraylist which holds each chapters text
					}
					// add audiobook into contents arraylist
					W.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapName, chapters));
				}
			}
			scan.close(); //close scanner
			return W;
		}

		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			titleMap = new HashMap<String, Integer>();
			artistMap = new HashMap<String, ArrayList<Integer>>();
			genreMap = new HashMap<String, ArrayList<Integer>>();

			try // allow the contents array list to be filled up using the private method
			{
				this.contents = storeReader();
			}
			catch(IOException e) // for any errors, print the error message and exit the program
			{
				System.out.println(e.getMessage());
				System.exit(1);
			}
			// Adding store contents into their respective maps using a for loop
			for(int i = 0; i < contents.size(); i++)
			{
				// add the title of each audiocontent as the key and the integer index as values
				titleMap.put(contents.get(i).getTitle(), i+1);

				// if the audiocontent is a song
				if(contents.get(i) instanceof Song)
				{
					Song song = (Song) contents.get(i);
					String artist = song.getArtist();
					// check if the artist is already in the Map
					if(artistMap.containsKey(artist))
					{
						// if artist already in map, retireve the arraylist in the map and add the
						// other index of the song by the same artist
						ArrayList<Integer> artistIndex = artistMap.get(artist);
						artistIndex.add(i+1);
					}
					else
					{
						// else create a new arraylist, add the index value and add the String and
						// ArrayList into the Map
						ArrayList<Integer> artistIndex = new ArrayList<>();
						artistIndex.add(i+1);
						artistMap.put(song.getArtist(), artistIndex);
					}
					// same steps for checking the genre of the song as used for artists
					String genre = song.getGenre().toString();
					if(genreMap.containsKey(genre))
					{
						ArrayList<Integer> genreIndex = genreMap.get(genre);
						genreIndex.add(i+1);
					}
					else
					{
						ArrayList<Integer> genreIndex = new ArrayList<>();
						genreIndex.add(i+1);
						genreMap.put(song.getGenre().toString(), genreIndex);
					}
				}
				// check if audiocontent is audiobook, and do same method as song/ genre for author
				else if(contents.get(i) instanceof AudioBook)
				{
					AudioBook audiobook = (AudioBook) contents.get(i);
					String author = audiobook.getAuthor();
					if(genreMap.containsKey(author))
					{
						ArrayList<Integer> authorIndex = genreMap.get(author);
						authorIndex.add(i+1);
					}
					else
					{
						ArrayList<Integer> authorIndex = new ArrayList<>();
						authorIndex.add(i+1);
						artistMap.put(audiobook.getAuthor(), authorIndex);
					}

				}

			}
		}
		////
		public void getTitleMap(String title)
		{
			// If key of the map contains the title input, put it through the for loop
			if(titleMap.keySet().contains(title))
			{
				for(int i = 0; i < titleMap.keySet().size(); i++)
				{
					// if the title at index i equals the title input, print out the value of the map
					// and the information of the specific audiocontent
					if(contents.get(i).getTitle().equals(title))
					{
						System.out.print(titleMap.get(title) + ". ");
						contents.get(i).printInfo();
					}			
				}
			}
			// if invalid title is put, return this message
			else
			{
				System.out.println("Title not found in store");
			}				
		}

		public void getArtistMap(String artist)
		{
			// if key in map contains parameter
			if(artistMap.keySet().contains(artist))
			{
				// loop over the map
				for(int i : artistMap.get(artist))
				{
					//print index and info
					System.out.print(i + ". ");
					contents.get(i-1).printInfo();							
				}
			}
			else
			{
				// print error
				System.out.println(artist+" does not exist in store");
			}
		}

		public void getGenreMap(String genre)
		{
			// check if key of map contains genre parameter
			if(genreMap.keySet().contains(genre))
			{
				// loop over map
				for(int i: genreMap.get(genre))
				{
					// print index and info
					System.out.print(i + ". ");
					contents.get(i-1).printInfo();							
					
				}
			}
			else
			{
				// print error
				System.out.println("The genre " + genre + " does not exist");
			}
		}

		public ArrayList<AudioContent> getContent()
		{
			return contents;
		}
		////
		
		public ArrayList<AudioContent> getContent(int index, int index2)
		{
			if (index < 1 || index > contents.size() || index2 < 1 || index2 > contents.size())
			{
				return null;
			}
			else
			{
				// create new arraylist
				ArrayList<AudioContent> newList = new ArrayList<>();
				// loop over arraylist and add the contents in the range of specified indices
				for(int i = index-1; i < index2; i++)
				{
					newList.add(contents.get(i));
				}
				return newList;
			}
		}

		// method used to download songs given specified artists using artistMap
		public ArrayList<AudioContent> getArtistContent(String artist)
		{
			if (!artistMap.keySet().contains(artist))
			{
				return null;
			}
			else
			{
				// create new arraylist
				ArrayList<AudioContent> newList = new ArrayList<>();
				// loop over map and add the contents of specified artist
				for(int i : artistMap.get(artist))
				{
					// add each of the artists contents into array list
					newList.add(contents.get(i-1));
				}
				// return arraylist
				return newList;
			}
		}

		public ArrayList<AudioContent> getGenreContent(String genre)
		{
			if (!genreMap.keySet().contains(genre))
			{
				return null;
			}
			else
			{
				// create new arraylist
				ArrayList<AudioContent> newList = new ArrayList<>();
				// loop over map and add the contents of specified genre
				for(int i : genreMap.get(genre))
				{
					// add each of the genre contents into array list
					newList.add(contents.get(i-1));
				}
				// return arraylist
				return newList;
			}			
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
}
