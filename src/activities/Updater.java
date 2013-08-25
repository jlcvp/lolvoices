package activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.Menu;
import android.widget.ImageView;
import caixamagica.WorkerFragment;

import com.lolchampionsvoices.R;

public class Updater extends Activity implements WorkerFragment.TaskCallbacks {
	private WorkerFragment myWorker;
	// Progress Dialog
    private ProgressDialog pDialog;
    ImageView my_image;
    
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    public static final int progress_circle_type = 1;
    
    
    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updater);
		ImageView iv = (ImageView) findViewById(R.id.updaterImg);
		iv.setImageResource(R.drawable.lolbg);
		FragmentManager fm = getFragmentManager();
		myWorker = (WorkerFragment) fm.findFragmentByTag("task");
		
		if(myWorker == null)
		{
			myWorker = new WorkerFragment();
			fm.beginTransaction().add(myWorker, "task").commit();
			myWorker.setarFragment(getApplicationContext(), getExternalFilesDir(null));
			myWorker.startTask();
		}
		
		
		

		// starting new Async Task
		//new DownloadFileFromURL().execute(file_url);
		

		
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
    
    @Override
    public void onPreExecute() 
    {  
    	
    }
   
    @Override
    public void onProgressUpdate(String percent) {  }
   
    @Override
    public void onCancelled() {  }
   
    @Override
    public void onPostExecute(String ignore) {  }
 
    

}
