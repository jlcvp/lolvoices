package activities;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import com.lolchampionsvoices.R;
import com.lolchampionsvoices.R.layout;
import com.lolchampionsvoices.R.menu;

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
    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    public static final int progress_circle_type = 1;
 
    // File url to download
    private static String file_url = "http://leu.lemanolos.com/thumbs.zip";
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updater);
		ImageView iv = (ImageView) findViewById(R.id.updaterImg);
		//iv.setImageResource(R.drawable.lolbg);

		// starting new Async Task
		new DownloadFileFromURL().execute(file_url);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.updater, menu);
		return true;
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
 
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {
 
    	int dialogType;
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @SuppressWarnings("deprecation")
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogType = 0;
            showDialog(progress_circle_type);
        }
 
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lengthOfRemoteFile = conection.getContentLength();
                Log.i("UpdaterActivity", "Length of remote file: " + lengthOfRemoteFile);
 
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
                // Output stream
                File localFile = new File(getExternalFilesDir(null),"thumbs.zip"); 
                Log.i("UpdaterActivity", "Length of Local file: " + localFile.length());
                
                if(!localFile.exists() || localFile.length() != lengthOfRemoteFile)
                {

                	Log.i("UpdaterActivity","Arquivo local não existe ou possui" +
                			"um tamanho diferente do esperado, baixando de novo");

                	publishProgress("baixando");	

                	OutputStream output = new FileOutputStream(localFile);

                	byte data[] = new byte[1024];

                	long total = 0;

                	while ((count = input.read(data)) != -1) {
                		total += count;
                		// publishing the progress....
                		// After this onProgressUpdate will be called
                		publishProgress("progresso",""+(int)((total*100)/lengthOfRemoteFile));

                		// writing data to file
                		output.write(data, 0, count);
                	}

                	// flushing output
                	output.flush();

                	// closing streams
                	output.close();
                	input.close();
                }
                else
                {
                	Log.i("UpdaterActivity", "O arquivo existe e possui tamanho igual ao arquivo remoto");
                }

            } catch (Exception e) {
            	Log.e("Error: ", e.getMessage());
            }
            
 
            return null;
        }
 
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            if(progress[0].equals("progresso"))
            {
            	pDialog.setProgress(Integer.parseInt(progress[1]));
            }
            else 
            {
            	dismissDialog(progress_circle_type);
            	showDialog(progress_bar_type);
            	dialogType=1;
            	
            }
            
       }
 
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
       	@Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            if(dialogType == 0){
            		dismissDialog(progress_circle_type);
            }
            else
            {
            	dismissDialog(progress_bar_type);
            }
 
            /*AQUI É ONDE A MÁGICA COMEÇA! */
            
            Intent intent = new Intent(Updater.this, ChampionsGrid.class);
            startActivity(intent);
            finish();
            

        }
 
    }

}
