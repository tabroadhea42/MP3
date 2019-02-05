package music;

import java.util.Iterator;
import table.TableInterface;
import table.TableFactory;
import java.util.ArrayList;

public class Artist
{
	private TableInterface<CD, String> cds;
	private TableFactory<CD , String> tf;
	private String ArtistName;
	private ArrayList<String> cd_list;
	
	public Artist(String name)
	{
		CompareCDTitles comp = new CompareCDTitles(true);
		ArtistName = name;
		cd_list = new ArrayList<String>();
		cds = tf.createTable(comp);
	}
	
	public void AddCD(CD cd)
	{
		cds.tableInsert(cd);
		cd_list.add(cd.getTitle());
	}
	
	public String getArtist()
	{
		return ArtistName;
	}
	
	public ArrayList<String> getCDList()
	{
		return this.cd_list;
	}
	
	public CD getCD(String cd)
	{
		return cds.tableRetrieve(cd);
	}
}