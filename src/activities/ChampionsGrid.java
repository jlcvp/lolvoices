package activities;


import java.io.IOException;
import java.util.ArrayList;

import gridView.*;

import caixamagica.ZipResourceFile;

import com.lolchampionsvoices.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ChampionsGrid extends Activity implements OnItemClickListener {

	private GridView gridView;
    private AdapterGridView adapterGridView;
    private ArrayList<ItemGridView> itens;
	private ZipResourceFile pacote;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions_grid);
        gridView = (GridView) findViewById(R.id.champ_gridView1);
        createGridView();
        
        gridView.setOnItemClickListener(this);
       
    }
    
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
        //Pega o item que foi selecionado.
        ItemGridView item = adapterGridView.getItem(position);
        //Toast.makeText(this, "Clicou em: "+item.getTexto(), Toast.LENGTH_LONG).show();
        Log.i("Champions Grid", "Clicou em: "+item.getTexto());
        Intent it = new Intent(ChampionsGrid.this, ChampionVoices.class);
        it.putExtra("Champion", item.getTexto());
        startActivity(it);
        
    }


    private void createGridView() {
		itens = new ArrayList<ItemGridView>();
		
		String[] hue = new String[]{"Aatrox", 
				"Ahri", 
				"Akali", 
				"Alistar", 
				"Amumu", 
				"Anivia", 
				"Annie", 
				"Ashe", 
				"Blitzcrank", 
				"Brand", 
				"Caitlyn", 
				"Cassiopeia", 
				"Chogath", 
				"Corki", 
				"Darius", 
				"Diana", 
				"Draven", 
				"DrMundo", 
				"Elise", 
				"Evelynn", 
				"Ezreal", 
				"Fiddlesticks", 
				"Fiora", 
				"Fizz", 
				"Galio", 
				"Gangplank", 
				"Garen", 
				"Gragas", 
				"Graves", 
				"Hecarim", 
				"Heimerdinger", 
				"Irelia", 
				"Janna", 
				"JarvanIV", 
				"Jax", 
				"Jayce", 
				"Karma", 
				"Karthus", 
				"Kassadin", 
				"Katarina", 
				"Kayle", 
				"Kennen", 
				"Khazix", 
				"KogMaw", 
				"Leblanc", 
				"LeeSin", 
				"Leona", 
				"Lissandra", 
				"Lulu", 
				"Lux", 
				"Malphite", 
				"Malzahar", 
				"Maokai", 
				"MasterYi", 
				"MissFortune", 
				"MonkeyKing", 
				"Mordekaiser", 
				"Morgana", 
				"Nami", 
				"Nasus", 
				"Nautilus", 
				"Nidalee", 
				"Nocturne", 
				"Nunu", 
				"Olaf", 
				"Orianna", 
				"Pantheon", 
				"Poppy", 
				"Quinn", 
				"Rammus", 
				"Renekton", 
				"Rengar", 
				"Riven", 
				"Rumble", 
				"Ryze", 
				"Sejuani", 
				"Shaco", 
				"Shen", 
				"Shyvana", 
				"Singed", 
				"Sion", 
				"Sivir", 
				"Skarner", 
				"Sona", 
				"Soraka", 
				"Swain", 
				"Syndra", 
				"Talon", 
				"Taric", 
				"Teemo", 
				"Thresh", 
				"Tristana", 
				"Trundle", 
				"Tryndamere", 
				"TwistedFate", 
				"Twitch", 
				"Udyr", 
				"Urgot", 
				"Varus", 
				"Vayne", 
				"Veigar", 
				"Vi", 
				"Viktor", 
				"Vladimir", 
				"Volibear", 
				"Warwick", 
				"Xerath", 
				"XinZhao", 
				"Yorick", 
				"Zac", 
				"Zed", 
				"Ziggs", 
				"Zilean", 
				"Zyra" };
		
		
		try {
			pacote = new ZipResourceFile(getExternalFilesDir(null).getAbsolutePath()+"/thumbs.zip");
			
		} catch (IOException e) {
			
			Log.e("ERRO","Deu merda abrindo o zip");
			e.printStackTrace();
		}
		
		
		
		for(int i=0;i<hue.length;i++)
		{
			Drawable icone=null; 
			try {
				icone = Drawable.createFromStream(pacote.getInputStream("Thumbs/"+hue[i]+".png"), null);
			} catch (IOException e) {

				e.printStackTrace();
			}
			ItemGridView elemento = new ItemGridView(hue[i],icone);
			itens.add(elemento);			
		}
		
		adapterGridView = new AdapterGridView(this, itens);
		
		gridView.setAdapter(adapterGridView);
		
		
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.champions_grid, menu);
        return true;
    }
    
}
