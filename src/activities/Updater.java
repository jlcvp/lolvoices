package activities;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import com.lolchampionsvoices.R;
import com.lolchampionsvoices.R.layout;
import com.lolchampionsvoices.R.menu;

import database.RepositorioScripts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Updater extends Activity {

	// Progress Dialog
	public ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
	public static final int progress_circle_type = 1;
 
    // File url to download
    private static String file_url = "http://leu.lemanolos.com/dados.dat";
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updater);
		ImageView iv = (ImageView) findViewById(R.id.updaterImg);
		//iv.setImageResource(R.drawable.lolbg);

		// starting new Async Task
		new DownloadFileFromURL(this).execute(file_url);

		
	}
	/**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case progress_bar_type: // we set this to 0
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Baixando arquivos de mídia");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
            return pDialog;
            
        case progress_circle_type:
        	pDialog = new ProgressDialog(this);
            pDialog.setMessage("Verificando consistência de arquivos...");
            pDialog.setIndeterminate(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
            pDialog.show();
            return pDialog;
            
        default:
            return null;
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.updater, menu);
		return true;
	}
	
	
	
 
    

}
