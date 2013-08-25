package caixamagica;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import database.RepositorioScripts;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WorkerFragment extends Fragment {
	private static String FILE_URL = "http://leu.lemanolos.com/dados.dat";
	public static interface TaskCallbacks {
	    void onPreExecute();
	    void onProgressUpdate(String... progress);
	    void onCancelled();
	    void onPostExecute(String str);
	  }
	
	private TaskCallbacks mCallbacks;
	File extFilesDir;
	Context mContext;
	DownloadFileFromURL downloader;
	
	
	/**
	   * Hold a reference to the parent Activity so we can report the
	   * task's current progress and results. The Android framework
	   * will pass us a reference to the newly created Activity after
	   * each configuration change.
	   */
	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    mCallbacks = (TaskCallbacks) activity;
	}
	@Override
	  public void onDetach() {
	    super.onDetach();
	    mCallbacks = null;
	  }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);   
      // Retain this fragment across configuration changes.
      setRetainInstance(true);      
    }
    
    
    public void setarFragment(Context mContext, File externalFilesDir)
    {
    	this.mContext=mContext;
    	extFilesDir=externalFilesDir;
    	downloader = new DownloadFileFromURL(mContext, extFilesDir);
    	
    }
    
    public void startTask()
    {
    	downloader.execute("go");    	
    }
    
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {
    	Context applicationContext;
    	File extFilesDir;
    	int dialogType;
    	

    	public DownloadFileFromURL(Context appCtx, File externalFilesDir)
    	{
    		applicationContext=appCtx;
    		extFilesDir=externalFilesDir;		
    	}

    	@Override
        protected void onPreExecute() {
          Log.i("WorkerFragment","OnPreExecutedChamado\nmCallbacks=="+mCallbacks);
    		if (mCallbacks != null) {
            mCallbacks.onPreExecute();
          }
        }
//    	@Override
//    	protected void onPreExecute() {
//    		super.onPreExecute();
//    		dialogType = 0;            
//    		//showDialog(progress_circle_type);
//    	}


    	/**
    	 * Downloading file in background thread
    	 * */
    	@Override
    	protected String doInBackground(String... f_url) {
    		int count;
    		RepositorioScripts bd = new RepositorioScripts(applicationContext);
    		
    		try {
    			URL url = new URL(FILE_URL);
    			URLConnection conection = url.openConnection();
    			conection.connect();
    			// this will be useful so that you can show a tipical 0-100% progress bar
    			int lengthOfRemoteFile = conection.getContentLength();
    			Log.i("UpdaterActivity", "Length of remote file: " + lengthOfRemoteFile);

    			// download the file
    			InputStream input = new BufferedInputStream(url.openStream(), 8192);

    			// Output stream
    			File localFile = new File(extFilesDir,"dados.dat"); 
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
    	@Override
        protected void onProgressUpdate(String... percent) {
          if (mCallbacks != null) {
            mCallbacks.onProgressUpdate(percent);
          }
        }
    	
    	

    	/**
    	 * After completing background task
    	 * Dismiss the progress dialog
    	 * **/
    	@Override
    	protected void onPostExecute(String file_url) {
    		if (mCallbacks != null) {
    	        mCallbacks.onPostExecute(null);
    	      }
//    		// dismiss the dialog after the file was downloaded
//    		if(dialogType == 0){
//    			//dismissDialog(progress_circle_type);
//    		}
//    		else
//    		{
//    			//dismissDialog(progress_bar_type);
//    		}

    		/*AQUI É ONDE A MÁGICA COMEÇA! */

//    		Intent intent = new Intent(Updater.this, ChampionsGrid.class);
//    		startActivity(intent);
//    		finish();


    	}

    }
    

	



		


	

	

	


	


}
