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
    private ProgressDialog pDialog;
    ImageView my_image;
    
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    public static final int progress_circle_type = 1;
    int dialogType;
    
    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updater);
		ImageView iv = (ImageView) findViewById(R.id.updaterImg);
		iv.setImageResource(R.drawable.lolbg);
		FragmentManager fm = getFragmentManager();
		myWorker = (WorkerFragment) fm.findFragmentByTag("task");
		
		dialogType=0;
		showDialog(progress_circle_type);
		
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
    	Log.i("onPreExecute","entrou");
    	dialogType = 0;            
		showDialog(progress_circle_type);
    	
    }
   
    @Override
	public void onProgressUpdate(String... progress) {
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
    
   
    @Override
    public void onCancelled() {  }
   
    @Override
    public void onPostExecute(String ignore) 
    {
    	// dismiss the dialog after the file was downloaded
//		if(dialogType == 0){
//			dismissDialog(progress_circle_type);
//		}
//		else
//		{
//			dismissDialog(progress_bar_type);
//		}
//
//		/*AQUI É ONDE A MÁGICA COMEÇA! */
//		FragmentManager fm = getFragmentManager();
//		if(fm.findFragmentByTag("task")!=null)
//		{
//			fm.beginTransaction().remove(fm.findFragmentByTag("task"));
//		}
			Intent intent = new Intent(Updater.this, ChampionsGrid.class);
			startActivity(intent);
			finish();
    }
 
    

}
