package activities;


import java.io.IOException;
import java.util.ArrayList;

import gridView.*;

import caixamagica.ZipResourceFile;

import com.lolchampionsvoices.R;

import database.RepositorioLoL;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
		
		RepositorioLoL bd = new RepositorioLoL(getApplicationContext());
		Log.i("BANCO","Consegui criar o repositorioLoL");
		ArrayList<String> heroes = new ArrayList<String>();
		Cursor c=null;
		
		c=bd.query("SELECT nome from herois");
		
		if(c.getCount()>0)
		{
			c.moveToFirst();
			for(int i=0; i<c.getCount();i++)
			{
				heroes.add(c.getString(c.getColumnIndex("nome")));
				c.moveToNext();
			}
			
			
		}
		bd.fechar();
	
		try {
			pacote = new ZipResourceFile(getExternalFilesDir(null).getAbsolutePath()+"/dados.dat");
			
		} catch (IOException e) {
			
			Log.e("ERRO","Deu merda abrindo o zip");
			e.printStackTrace();
		}
		
		
		
		for(int i=0;i<heroes.size();i++)
		{
			Drawable icone=null; 
			try {
				icone = Drawable.createFromStream(pacote.getInputStream("Thumbs/"+heroes.get(i)+".png"), null);
			} catch (IOException e) {

				e.printStackTrace();
			}
			ItemGridView elemento = new ItemGridView(heroes.get(i),icone);
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
