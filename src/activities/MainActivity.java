package activities;

import com.lolchampionsvoices.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checagemInicial();
        Log.d("HUE","caminho getExternalFilesDir: " + getExternalFilesDir(null).getAbsolutePath());
        Log.d("HUE","caminho getFilesDir(): " + getFilesDir().getAbsolutePath());
    }
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public void chamaGrid(View view)
    {
    	Intent it = new Intent(MainActivity.this, ChampionsGrid.class);
    	startActivity(it);    	
    }
    
    private void checagemInicial()
    {
    	
    	
    }
    
    
    
}
