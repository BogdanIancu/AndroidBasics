package ro.facemsoft.myfirstapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ro.facemsoft.myfirstapplication.adapters.ToDoItemAdapter;
import ro.facemsoft.myfirstapplication.database.DbConstants;
import ro.facemsoft.myfirstapplication.database.DbHelper;
import ro.facemsoft.myfirstapplication.models.ToDoItem;

public class ToDoListActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        listView = findViewById(R.id.list_view);

        ArrayList<ToDoItem> list = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(ToDoListActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c =
                db.rawQuery("SELECT * FROM " + DbConstants.Items.TABLE_NAME, null);
        while(c.moveToNext()) {
            int index = c.getColumnIndex(DbConstants.Items.COLUMN_TITLE);
            String title = c.getString(index);
            index = c.getColumnIndex(DbConstants.Items.COLUMN_DESC);
            String description = c.getString(index);
            index = c.getColumnIndex(DbConstants.Items.COLUMN_DUE_DATE);
            String dueDate = c.getString(index);
            index = c.getColumnIndex(DbConstants.Items.COLUMN_PRIORITY);
            String priority = c.getString(index);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date= null;
            try {
                date = sdf.parse(dueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ToDoItem item = new ToDoItem(title, description, date, new Date(),
                    ToDoItem.Priority.valueOf(priority));
            list.add(item);
        }


        ToDoItemAdapter adapter =
                new ToDoItemAdapter(ToDoListActivity.this,
                        R.layout.item_template,
                        list);
        listView.setAdapter(adapter);
    }
}
