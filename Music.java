package music;

import java.util.Iterator;
import table.TableInterface;
import table.TableFactory;
import java.util.ArrayList;

public class Music
{
    private TableInterface<Artist, String> artists;
	TableFactory<Artist , String> tf;
	Iterator<Artist> iter;
	private ArrayList<String> art_list;
	
	public Music(String file)
	{
		CompareArtists comp = new CompareArtists(true);
		this.artists = tf.createTable(comp);
		art_list = new ArrayList<String>();
		readMusic(file);
	}

   private void readMusic(String file_name)
   {	   
      util.ReadTextFile rf = new util.ReadTextFile(file_name);
      String art = rf.readLine();
      while (!rf.EOF())
      {
         String title = rf.readLine();
         System.out.println(title);
         int year = Integer.parseInt(rf.readLine());
         int rating = Integer.parseInt(rf.readLine());
         int numTracks = Integer.parseInt(rf.readLine());
         CD cd = new CD(title, art, year, rating, numTracks);

         int tracks = 1;

         while (tracks <= numTracks)
         {
            String temp = rf.readLine();
            String[] line = temp.split(",");
            String len = line[0];
            String song_title = line[1];
			Song song = new Song(song_title, len, art, cd.getTitle(), tracks);
            cd.addSong(song);
            tracks++;
         }
		 
		 //DO THIS
         //if the artist isn't already present in the table, create a new artist and insert it
       
		Artist artist = artists.tableRetrieve(art);
		
		if(artist == null)
		{
			artist = new Artist(art);
			artists.tableInsert(artist);
			art_list.add(art);
			System.out.println("added");
		}
		artist.AddCD(cd);

         art = rf.readLine();
      }
	  
	  rf.close();
   }
  
  
   private ArrayList<String> getList()
   {
	   return this.art_list;
   }
   
   private TableInterface<Artist, String> getTable()
   {
	   return this.artists;
   }
  

   public static void main(String[] args)
   {
      Music mc = new Music("resources/cds.txt");
      //instantiate your GUI here
	  
	  MusicGUI gui = new MusicGUI(500, 500, mc.getList(), mc.getTable());
	
		//create the gui last
	}
   
}