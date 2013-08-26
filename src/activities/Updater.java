package activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import caixamagica.WorkerFragment;

import com.lolchampionsvoices.R;

public class Updater extends Activity implements WorkerFragment.TaskCallbacks {
	private WorkerFragment myWorker;
	// Progress Dialog
    
    ImageView my_image;
    boolean isUpdating;
    
    
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    public static final int progress_circle_type = 1;
    int dialogType;
    
    //int Debugs
    int debugProgress;
    
    
    
    
    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updater);
		
//		ImageView iv = (ImageView) findViewById(R.id.updaterImg);
//		iv.setImageResource(R.drawable.lolbg);
		debugProgress = 0;
		FragmentManager fm = getFragmentManager();
		myWorker = (WorkerFragment) fm.findFragmentByTag("task");
		if(myWorker == null)
		{
			myWorker = WorkerFragment.newInstance(1);
			//fm.beginTransaction().add(myWorker, "task").commit();
			myWorker.setarFragment(getApplicationContext(), getExternalFilesDir(null));							
		}
		myWorker.show(fm, "task");
		
		
		
		// starting new Async Task
		//new DownloadFileFromURL().execute(file_url);
		

		
	}

//	public void mostrarDialogs(int currentDialogType)
//	{
//		if (pDialog == null)
//		{
//			if(currentDialogType == progress_circle_type)
//			{
//				showDialog(progress_circle_type);
//			}
//			else
//			{
//				showDialog(progress_bar_type);
//			}
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.updater, menu);
		return true;
	}
	
	
	/**
//     * Showing Dialog
//     * */
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//        case progress_bar_type: // we set this to 0
//            pDialog = new ProgressDialog(this);
//            pDialog.setMessage("Baixando arquivos de m�dia");
//            pDialog.setIndeterminate(false);
//            pDialog.setMax(100);
//            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            pDialog.setCancelable(false);
//            pDialog.show();
//            dialogType=progress_bar_type;
//            return pDialog;
//            
//        case progress_circle_type:
//        	pDialog = new ProgressDialog(this);
//            pDialog.setMessage("Verificando consist�ncia de arquivos...");
//            pDialog.setIndeterminate(true);
//            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            pDialog.setCancelable(true);
//            pDialog.show();
//            dialogType=progress_circle_type;
//            return pDialog;
//            
//        default:
//            return null;
//        }     
//       
//    }
//    
//    @Override
//    public void onPreExecute() 
//    {
//    	Log.i("onPreExecute","entrou");
//    }
   
//    @Override
//	public synchronized void  onProgressUpdate(String... progress) {
//    	// setting progress percentage
//    	
//    	if(pDialog!=null)
//    	{
//    		
//    		if(dialogType == progress_bar_type)
//    		{
//    			
//    			if(Integer.parseInt(progress[1])!=debugProgress)
//    			{
//    				debugProgress = Integer.parseInt(progress[1]);
//    				Log.i("onProgressUpdate","debugProgress = "+debugProgress);
//    			}
//    			pDialog.setProgress(Integer.parseInt(progress[1]));    		
//    		}
//    		else if(dialogType == progress_circle_type) 
//    		{
//    			dismissDialog(progress_circle_type);
//    			showDialog(progress_bar_type);    			
//    		}
//    	}
//    	else
//    	{
//    		Log.i("onProgressUpdate","pDialog = "+pDialog);
//    	}
//    	
//    	
//
//    }
    
   
    
   
    @Override
    public void onPostExecute(String ignore) 
    {
    	//dismiss the dialog after the file was downloaded
		myWorker.dismiss();
		/*AQUI � ONDE A M�GICA COME�A! */
		
			Intent intent = new Intent(Updater.this, ChampionsGrid.class);
			startActivity(intent);
			finish();
    }
    
    
 
    

}
