package pl.maikeru.taski;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	private MyTask dft = null;
	private ProgressBar pb = null;
	// private Handler myHandler = new Handler();
	
	// ====================================================================
	
	private class MyTask extends AsyncTask<String, Integer, Long> {
		private ProgressBar pb = null;
		public MyTask(ProgressBar pb) {
			this.pb = pb;
		}
		protected Long doInBackground(String... urls) {
			Log.d("methods", "DownloadFilesTask::doInBackground");
			int count = urls.length;
			long totalSize = 0;
			for (int i = 0; i < count; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				totalSize += urls[i].length();
				publishProgress((int) ((i / (float) count) * 100));
				if (isCancelled()) {
					break;
				}
			}
			return totalSize;
		}
	
	     protected void onProgressUpdate(Integer... progress) {
	    	 Log.d("methods", "DownloadFilesTask::onProgressUpdate");
	    	 if (!isCancelled()) {
	    		 pb.setProgress(progress[0]);
	    	 }
	     }
	
	     protected void onPostExecute(Long result) {
	    	 Log.d("methods", "DownloadFilesTask::onPostExecute");
	         showInfo("Downloaded " + result + " bytes");
	         pb.setProgress(100);
	     }
	     
	     protected void onCancelled() {
	    	 Log.d("methods", "DownloadFilesTask::onCancelled");
	         pb.setProgress(0);
	     }
	 }
	
	// =================================================================
	
//	private void setProgressPercent(int progress) {
//		Log.d("methods", "DownloadFilesTask::setProgressPercent");
//		Log.i("progress", " " + progress + "%");
//		pb.setProgress(progress);
//	}

	public void showInfo(String string) {
		Log.d("methods", "DownloadFilesTask::showInfo");
		Log.i("info", "End: " + string);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("methods", "MainActivity::onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("methods", "MainActivity::onCreateOptionsMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void launchTask(View view)
	{
		Log.d("methods", "MainActivity::launchTask");
		dft = new MyTask(pb);
		dft.execute("foo", "bar", "bazz", "bazz", "bazz", "bazz", "bazz");
		return;
	}
	public void cancelTask(View view)
	{
		Log.d("methods", "MainActivity::cancelTask");
		if (dft != null) {
			Log.i("status", "cancelResult: " + dft.cancel(false));
			
		}
		return;
	}

}
