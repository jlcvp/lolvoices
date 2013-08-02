package activities;


import java.util.ArrayList;

import gridView.*;

import com.example.lolchampionsvoices.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

public class ChampionsGrid extends Activity {

	private GridView gridView;
    private AdapterGridView adapterGridView;
    private ArrayList<ItemGridView> itens;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions_grid);
        gridView = (GridView) findViewById(R.id.champ_gridView1);
        createGridtView();
    }


    private void createGridtView() {
		itens = new ArrayList<ItemGridView>();
		
		String[] hue = new String[]{"Hue","BRBR","HueHUE","BRBR","HEHAHUAHUE","VIVA DILMA"};
		
		for(int i=0;i<10;i++)
		{
			ItemGridView elemento = new ItemGridView(hue[i%6],null);
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
