package music;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.util.ArrayList;

import gui.CenterFrame;
import gui.Drawable;
import gui.DrawPanel;
import gui.EasyGridBag;

import table.TableInterface;

public class MusicGUI extends CenterFrame implements Drawable, ActionListener
{
	private JComboBox<String> cb_cds;
	private JComboBox<String> cb_artists;
	private ManySongs cb_songs;
	private SingleSong target;
	private TableInterface<Artist, String> artTable;
	private Artist curr;
	
	private java.awt.Image img0;
	private java.awt.Image img2;
	private java.awt.Image img;
	
   public MusicGUI(int width, int height, ArrayList<String> art_list, TableInterface<Artist, String> artT) 
   {
      super(width, height, "Music GUI");
	  
	  artTable = artT;
	  curr = new Artist("new");
	  cb_songs = new ManySongs();
	  target = new SingleSong();
	  
	  DragSource AsongDS = DragSource.getDefaultDragSource();
	  DragGestureRecognizer AsongDGR = AsongDS.createDefaultDragGestureRecognizer(cb_songs, DnDConstants.ACTION_COPY, cb_songs);
	  DropTarget AsongDT = new DropTarget(target, target);
	  
      setLayout(new BorderLayout());
	  JPanel center_panel = new JPanel();
      
	  center_panel.setBackground(java.awt.Color.WHITE);
	  add(center_panel, BorderLayout.CENTER);
	  
	  center_panel.setLayout(new BorderLayout());
     
	  cb_artists = new JComboBox<String>();
	  cb_cds = new JComboBox<String>();
	  
	  for(int i = 0; i < art_list.size(); i++)
	  {
		  String variable = art_list.get(i);
		  cb_artists.addItem(variable);
	  }
	  
	 JPanel pnlNorth = new JPanel();
     pnlNorth.setLayout(new GridLayout(1, 2));
	 add(pnlNorth, BorderLayout.NORTH);
	  
	 pnlNorth.add(cb_artists);
	 pnlNorth.add(cb_cds);
	
	//register for Observer
	cb_artists.addActionListener(new CBArtists());
	cb_cds.addActionListener(new CBCds());
	//allow multiple components to use the same controller
	//provide each component with an id
	cb_artists.setActionCommand("Artist");
	
	cb_songs.setFont(new Font("Verdana", Font.PLAIN, 8));
	cb_songs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	cb_songs.setVisible(true);
	target.setFont(new Font("Verdana", Font.PLAIN, 20));
	target.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	target.setVisible(true);
	CD voided = null;
	cb_songs.setCD(voided);
	
	JPanel pnlWest = new JPanel();
	pnlWest.setLayout(new GridLayout(2, 1));
	add(pnlWest, BorderLayout.WEST);
	pnlWest.add(cb_songs);
	pnlWest.add(target);
	
	//cb_songs.addActionListener(new CBSongs());
	
		gui.ImageLoader il = gui.ImageLoader.getImageLoader();
		img0 = il.getImage("resources/art/Be'lakor - Of Breath and Bone.jpg");
		DrawPanel pnl = new DrawPanel();
		pnl.setDrawable(this);
		center_panel.add(pnl, BorderLayout.CENTER);
		img = img0;
		//img2 = il.getHighLightImage(img0);
		setVisible(true);
   }
   
   //ActionListeners are used by buttons and combo boxes (the majority of components that I use)
   //inner class to act as the controller
   private class CBArtists implements ActionListener
   {   
	   public void actionPerformed(ActionEvent evt)
		{	
			cb_songs.removeAll();		
			int index = cb_artists.getSelectedIndex();
			//String selection = cb_artists.getItemAt(index);
			if(index < 0)
			{
				return;
			}
			String selection = cb_artists.getItemAt(index);
			System.out.println(selection + ": " + index);	
			Artist art = artTable.tableRetrieve(selection);
			ArrayList<String> cd_list = art.getCDList();
			/*
			Iterator<CD> i = art.iterator();
			while(i.hasNext())
			{
				
			}
			*/
			cb_cds.removeAllItems();
			for(int j = 0; j < cd_list.size(); j++)
			{
			  cb_cds.addItem(cd_list.get(j));
			}
			curr = art;
			repaint();
		}
   }
   
   private class CBCds implements ActionListener
   {
	   public void actionPerformed(ActionEvent evt)
		{
			int index = cb_cds.getSelectedIndex();
			if(index < 0)
			{
				return;
			}
			String something = cb_cds.getItemAt(index);
			System.out.println(something + ": " + index);			
			
			CD compact_disk = curr.getCD(something);
			
			cb_songs.setCD(compact_disk);
		
			repaint();
		}
   }
   
   //needed to implement the ActionListener interface
   public void actionPerformed(ActionEvent evt)
   {
	
   }

   //needed to implement the Drawable interface
   public void draw(java.awt.Graphics g, int width, int height)
   {
	   //all graphics to be drawn in a separate thread
	   int index = cb_cds.getSelectedIndex();
	   String something = cb_cds.getItemAt(index);
	   CD compact_disk = curr.getCD(something);
	   //compact_disk.draw(g, width, height);
	   g.drawImage(img, 0, 0, null);  
	   //compact_disk.draw(g, width, height);
   }

   public void mouseClicked(int x, int y){}
   public void keyPressed(char key){}
}