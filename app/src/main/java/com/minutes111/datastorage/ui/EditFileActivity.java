package com.minutes111.datastorage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.minutes111.datastorage.Const;
import com.minutes111.datastorage.R;
import com.minutes111.datastorage.util.FileWorker;

public class EditFileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditText;
    private Button mButtonSave;
    private Button mButtonCancel;

    private FileWorker mFileWorker = new FileWorker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        mEditText = (EditText) findViewById(R.id.edit_edit_file);
        mButtonSave = (Button)findViewById(R.id.btn_edit_save);
        mButtonCancel = (Button) findViewById(R.id.btn_edit_cancel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mButtonSave.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

        String fileName = getIntent().getStringExtra(Const.ATTR_FILE_NAME);
        sendBroadcast(new Intent(Const.BROADCAST_ACTION).putExtra(Const.ATTR_BROADCAST_FILE,fileName));
        String text = mFileWorker.readFile(EditFileActivity.this,fileName);
        mEditText.setText(text.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_save:
                String file = getIntent().getStringExtra(Const.ATTR_FILE_NAME);
                String fileName = file.substring(0,file.indexOf("."));
                mFileWorker.writeFile(EditFileActivity.this, fileName, mEditText.getText().toString());
                finish();
                break;
        }
    }

}
