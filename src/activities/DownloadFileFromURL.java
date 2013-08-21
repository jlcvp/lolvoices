package activities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import database.RepositorioScripts;

/**
 * Background Async Task to download file
 * */
class DownloadFileFromURL extends AsyncTask<String, String, String> {
	public static final int progress_bar_type = 0;
	public static final int progress_circle_type = 1;
	Updater painho;

	

	int dialogType;
	
	public DownloadFileFromURL(Updater caller) {
		// TODO Auto-generated constructor stub
		painho = caller;
	}

	/**
	 * Before starting background thread
	 * Show Progress Bar Dialog
	 * */
	@SuppressWarnings("deprecation")
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogType = 0;
        painho.showDialog(progress_circle_type);
    }
	
	

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        RepositorioScripts bd = new RepositorioScripts(painho);
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
            File localFile = new File(painho.getExternalFilesDir(null),"dados.dat"); 
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
        	painho.pDialog.setProgress(Integer.parseInt(progress[1]));
        }
        else 
        {
        	painho.dismissDialog(progress_circle_type);
        	painho.showDialog(progress_bar_type);
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
        		painho.dismissDialog(progress_circle_type);
        }
        else
        {
        	painho.dismissDialog(progress_bar_type);
        }

        /*AQUI É ONDE A MÁGICA COMEÇA! */
        
        Intent intent = new Intent(painho, ChampionsGrid.class);
        painho.startActivity(intent);
        painho.finish();
        

    }

}