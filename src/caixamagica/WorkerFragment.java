package caixamagica;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;

import database.RepositorioScripts;

import activities.Updater;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WorkerFragment extends DialogFragment {
	private static String FILE_URL = "http://leu.lemanolos.com/dados.dat";
	private TaskCallbacks mCallbacks;
	File extFilesDir;
	Context mContext;
	DownloadFileFromURL downloader;
	int currentDialogType;
	ProgressDialog pDialog;
	
	
	public static final int progress_bar_type = 0;
    public static final int progress_circle_type = 1;
	
    public static WorkerFragment newInstance(int title) {
		WorkerFragment frag = new WorkerFragment();
		Bundle args = new Bundle();
		args.putInt("ProgressStyle", ProgressDialog.STYLE_HORIZONTAL);
		frag.setArguments(args);
		return frag;
	}
	
	public static interface TaskCallbacks {
	    void onPostExecute(String str);
	  }
	/**
	   * Hold a reference to the parent Activity so we can report the
	   * task's current progress and results. The Android framework
	   * will pass us a reference to the newly created Activity after
	   * each configuration change.
	   */
	@Override
	public void onAttach(Activity activity) {
	    Log.i("OnAttach","OnAttach Chamado");
		super.onAttach(activity);
	    mCallbacks = (TaskCallbacks) activity;
	    
	    Log.i("OnAttach","OnAttach Finalizado\n mCallbacks = "+mCallbacks);	
	    
		//((Updater)activity).mostrarDialogs(currentDialogType);
	}
	@Override
	  public void onDetach() {
	    super.onDetach();
	    mCallbacks = null;
	  }
	
	
	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		return null;
//	}
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
    	new DownloadFileFromURL(mContext, extFilesDir).execute("ignore");
    	
    }
    
    
    /**
     *  Showing Dialog
    * */
   @Override
   public Dialog onCreateDialog(Bundle id) {
       
	ProgressDialog pDialog = new ProgressDialog(getActivity());
	
	switch (id.getInt("ProgressStyle")) {
       case ProgressDialog.STYLE_HORIZONTAL: // we set this to 0
    	   
    	   pDialog.setMessage("Baixando arquivos de mídia");
           pDialog.setIndeterminate(false);
           pDialog.setMax(100);
           pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
           pDialog.setCancelable(false);
//           pDialog.show();
           currentDialogType=progress_bar_type;
           return pDialog;
           
       case ProgressDialog.STYLE_SPINNER:
    	   
           pDialog.setMessage("Verificando consistência de arquivos...");
           pDialog.setIndeterminate(true);
           pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           pDialog.setCancelable(true);
           pDialog.show();
           currentDialogType=progress_circle_type;
           return pDialog;
           
           
           
       default:
           return null;
       }     
      
   }
   
//    public void  onProgressUpdate(String... progress) {
//    	// setting progress percentage
//    	
    	
    
    
    
    public void changeDialogType(){
    	
    	pDialog.setIndeterminate(true);
    	pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	
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
    		currentDialogType=progress_circle_type;
    		
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
    				//onProgressUpdate("baixando");
    				
    				OutputStream output = new FileOutputStream(localFile);

    				byte data[] = new byte[4096];

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
        protected void onProgressUpdate(String... progress) {
    		
    		//Log.i("onProgressUpdate Fragment", "progress[0]= "+percent[0]);    		
    		if(isVisible())
        	{
        		
        		if(dialogType == progress_bar_type)
        		{	
        			pDialog.setProgress(Integer.parseInt(progress[1]));        			  		
        		}
        		else if(dialogType == progress_circle_type) 
        		{
        			changeDialogType();
        		}
        	}
        	else
        	{
        		Log.i("onProgressUpdate","pDialog = "+pDialog);
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
