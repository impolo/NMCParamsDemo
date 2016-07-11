package com.appzorro.nmcparamsdemo.view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.appzorro.nmcparamsdemo.R;
import com.appzorro.nmcparamsdemo.dl.DtoDefaultValues;
import com.appzorro.nmcparamsdemo.util.Operations;
import com.appzorro.nmcparamsdemo.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends ListActivity {

    static final String TAG = "Nmc demo activity";

    File logFile;

    List<String> paramList = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;

    //A ProgressDialog object
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logFile = new File(getExternalFilesDir(null).getAbsolutePath() + "log.file");
        DtoDefaultValues.setDeviceId(Util.GetDeviceId(this));

        OperationInitial();
    }

    public void initActivity() {
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, paramList);

        setListAdapter(mAdapter);

    }

    public void OperationInitial () {
        DtoDefaultValues.setPort("8095");//8098<-production || 8081<-development || 8095<-QA || sungard 8081
        Util.WriteLog("Start operation initial,sending request to : " + DtoDefaultValues.getIp() + ":" + DtoDefaultValues.getPort(), logFile.toURI());//Log to file
        Log.i("Start OperationInitial", "sending request to : " + DtoDefaultValues.getIp() + ":" + DtoDefaultValues.getPort());
        OperationInitialTask tskOperationInitial = new OperationInitialTask();
        tskOperationInitial.execute();
    }

    private class OperationInitialTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog title to 'Loading...'
            progressDialog.setTitle("Initialization...");
            //Set the dialog message to 'Loading application View, please wait...'
            // progressDialog.setMessage("Loading application View, please wait...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            //The maximum number of items is 100
            //  progressDialog.setMax(100);

            //Display the progress dialog
            progressDialog.show();
        }


        @Override
        protected Boolean doInBackground(Void... params){
            Boolean bReturn = false;
            bReturn = Operations.OperationInitial(logFile);

            paramList.add(DtoDefaultValues.getServerImage());
            paramList.add(DtoDefaultValues.getServerVideo());
            Log.i("server images:", DtoDefaultValues.getServerImage());

            Log.i("server videos:", DtoDefaultValues.getServerVideo());
            return bReturn;
        }

        @Override
        protected void onPostExecute(Boolean params){

            progressDialog.dismiss();

            initActivity();
        }
    }

}
