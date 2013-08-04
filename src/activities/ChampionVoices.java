package activities;

import gridView.AdapterGridView;
import gridView.ItemGridView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import caixamagica.ZipResourceFile;

import com.lolchampionsvoices.R;
import com.lolchampionsvoices.R.layout;
import com.lolchampionsvoices.R.menu;

import database.RepositorioLoL;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ChampionVoices extends Activity implements OnItemClickListener{
	private GridView gridView;
    private AdapterGridView adapterGridView;
    private ArrayList<ItemGridView> itens;
	private ZipResourceFile pacote;
	private String champion_name;
	private MediaPlayer mplayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_champion_voices);
		
		try {
			pacote = new ZipResourceFile(getExternalFilesDir(null).getAbsolutePath()+"/dados.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		champion_name= getIntent().getStringExtra("Champion");
		ImageView iv = (ImageView) findViewById(R.id.imageView_champion_thumb);
		try {
			iv.setImageDrawable(Drawable.createFromStream(pacote.getInputStream("Thumbs/"+champion_name+".png"), null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView textView = (TextView) findViewById(R.id.textView_champion_name);
		textView.setText(champion_name);
		
		gridView = (GridView) findViewById(R.id.gridViewVoices);
        
		createGridView();
        
        gridView.setOnItemClickListener(this);
        
        mplayer = new MediaPlayer();
        
		
	}
	
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		//on item click
		ItemGridView item = adapterGridView.getItem(position);
		mplayer.reset();
		try {
			//pegando o arquivo dentro do ZIP e criando um arquivo temporário de cache para uso no media player
			InputStream input = pacote.getInputStream("Sons/"+champion_name+"."+item.getTexto()+".mp3");
			FileOutputStream fos = new FileOutputStream(new File(getExternalCacheDir(),"voice.mp3"));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = input.read(bytes)) != -1) {
				fos.write(bytes, 0, read);			
			}



			if (input != null) {

				input.close();

			}
			if (fos != null) {

				// outputStream.flush();
				fos.close();


			}

			//fim da criação do arquivo temporário
			File tempMP3 = new File(getExternalCacheDir(),"voice.mp3");
			FileInputStream fis=null;

			fis = new FileInputStream(tempMP3);


			mplayer.setDataSource(fis.getFD());
			mplayer.prepare();
			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			mplayer.start();
		} catch (Exception e)
		{
			Log.e("Sound","Erro ao iniciar a reproduçao");
		}

		
	}

	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.champion_voices, menu);
		return true;
	}
	 
	 
	 private void createGridView()
	 {
		 itens = new ArrayList<ItemGridView>();
			
		 RepositorioLoL bd = new RepositorioLoL(getApplicationContext());
		 Log.i("BANCO","Consegui criar o repositorioLoL de novo");
		 ArrayList<String> sons = new ArrayList<String>();
		 Cursor c=null;

		 c=bd.query("SELECT som FROM sons WHERE heroi='"+champion_name+"'");

		 if(c.getCount()>0)
		 {
			 c.moveToFirst();
			 for(int i=0; i<c.getCount();i++)
			 {
				 sons.add(c.getString(c.getColumnIndex("som")));
				 c.moveToNext();
			 }
		 }
			
			
			for(int i=0;i<sons.size();i++)
			{				
				ItemGridView item = new ItemGridView(sons.get(i), getResources().getDrawable(R.drawable.autofalante));
				itens.add(item);
			}
			
			adapterGridView = new AdapterGridView(this, itens);
			
			gridView.setAdapter(adapterGridView);
			
			
			
			
			
			
	 }

}
