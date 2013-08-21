package activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Downloader extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);

	        //The heart and mind of headless fragment is below line. It will keep the fragment alive during configuration change when activities and   //subsequent fragments are "put to death" and recreated
	        setRetainInstance(true);

	        
	    }

}
