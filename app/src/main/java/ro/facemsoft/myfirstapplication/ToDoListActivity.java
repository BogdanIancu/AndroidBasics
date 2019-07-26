package ro.facemsoft.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ro.facemsoft.myfirstapplication.adapters.ToDoItemAdapter;
import ro.facemsoft.myfirstapplication.models.ToDoItem;

public class ToDoListActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        listView = findViewById(R.id.list_view);

        Intent intent = getIntent();
        if(intent != null) {
            ArrayList<ToDoItem> list =
                    (ArrayList<ToDoItem>)intent.getSerializableExtra("items");

            ToDoItemAdapter adapter =
                    new ToDoItemAdapter(ToDoListActivity.this,
                            R.layout.item_template,
                            list);
            listView.setAdapter(adapter);
        }
    }
}
