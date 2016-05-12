package com.minutes111.datastorage.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.minutes111.datastorage.Const;
import com.minutes111.datastorage.R;
import com.minutes111.datastorage.util.FileWorker;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButtonCreate;
    private Button mButtonList;

    private SharedPreferences mPreferences;
    BroadcastReceiver mReceiver;

    private FileWorker mFileWorker = new FileWorker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText)findViewById(R.id.edit_main);
        mButtonCreate = (Button)findViewById(R.id.btn_main_create);
        mButtonList = (Button) findViewById(R.id.btn_main_list);

        mPreferences = getSharedPreferences("name", MODE_PRIVATE);
        String file = mPreferences.getString(Const.ATTR_PREF_FILE,"");

        Log.d(Const.LOG_TAG,"my pref -> " + file);
        if (!file.equals("")){
            startActivity(new Intent(MainActivity.this,EditFileActivity.class).putExtra(Const.ATTR_FILE_NAME,file));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mReceiver = getReceiver(mPreferences);
        registerReceiver(mReceiver,new IntentFilter(Const.BROADCAST_ACTION));
    }

    public void onClickCreate(View v){
        if (mEditText.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Please, enter the file name", Toast.LENGTH_SHORT).show();
            return;
        }
        mFileWorker.writeFile(MainActivity.this, mEditText.getText().toString());
    }

    public void onClickList(View v){
        startActivity(new Intent(this, FileListActivity.class));
    }

    private BroadcastReceiver getReceiver(final SharedPreferences pref){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String file = intent.getStringExtra(Const.ATTR_BROADCAST_FILE);
                Log.d(Const.LOG_TAG,file+"sss");
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Const.ATTR_PREF_FILE,file);
                editor.commit();
            }
        };
    }

}
