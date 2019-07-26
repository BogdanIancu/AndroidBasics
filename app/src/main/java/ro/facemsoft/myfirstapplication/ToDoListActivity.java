package ro.facemsoft.myfirstapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import ro.facemsoft.myfirstapplication.workers.AvatarsWorker;

public class ToDoListActivity extends AppCompatActivity {
    private ListView listView;
    private ToDoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        listView = findViewById(R.id.list_view);

        final ArrayList<ToDoItem> list = new ArrayList<>();

        new Thread((new Runnable() {
            @Override
            public void run() {
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
                db.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter =
                                new ToDoItemAdapter(ToDoListActivity.this,
                                        R.layout.item_template,
                                        list);
                        listView.setAdapter(adapter);
                        AvatarsWorker worker = new AvatarsWorker(adapter);
                        worker.execute(list);
                    }
                });
            }
        })).start();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ToDoListActivity.this);
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
}
