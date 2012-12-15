package com.boh.flatmate.data;

import android.os.AsyncTask;
import android.util.Log;

public class LoadingTask extends AsyncTask<String, Integer, Integer> {

	public interface LoadingTaskFinishedListener {
		void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
	}

	// This is the listener that will be told when this task is finished
	private final LoadingTaskFinishedListener finishedListener;

	/**
	 * A Loading task that will load some resources that are necessary for the app to start
	 * @param progressBar - the progress bar you want to update while the task is in progress
	 * @param finishedListener - the listener that will be told when this task is finished
	 */
	public LoadingTask(LoadingTaskFinishedListener finishedListener) {
		this.finishedListener = finishedListener;
	}

	@Override
	protected Integer doInBackground(String... params) {
		Log.i("Tutorial", "Starting task with url: "+params[0]);
		if(resourcesDontAlreadyExist()){
			downloadResources();
		}
		// Perhaps you want to return something to your post execute
		return 1234;
	}

	private boolean resourcesDontAlreadyExist() {
		// Here you would query your app's internal state to see if this download had been performed before
		// Perhaps once checked save this in a shared preference for speed of access next time
		return true; // returning true so we show the splash every time
	}


	private void downloadResources() {
		// We are just imitating some process thats takes a bit of time (loading of resources / downloading)
		int count = 10;
		for (int i = 0; i < count; i++) {

			// Update the progress bar after every step
			int progress = (int) ((i / (float) count) * 100);
			publishProgress(progress);

			// Do some long loading things
			try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
	}
}