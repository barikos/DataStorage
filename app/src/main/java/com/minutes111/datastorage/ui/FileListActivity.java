package com.minutes111.datastorage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.minutes111.datastorage.Const;
import com.minutes111.datastorage.R;
import com.minutes111.datastorage.util.FileWorker;

public class FileListActivity extends AppCompatActivity {

    private ListView mListView;

    private FileWorker mFileWorker = new FileWorker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        mListView = (ListView) findViewById(R.id.lv_file_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] fileNames = mFileWorker.getArrayFileNames(FileListActivity.this);
        ArrayAdapter mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,fileNames);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = (String) mListView.getAdapter().getItem(position);
                Intent intent = new Intent(FileListActivity.this, EditFileActivity.class);
                intent.putExtra(Const.ATTR_FILE_NAME, fileName);
                startActivity(intent);
            }
        });
    }

}
