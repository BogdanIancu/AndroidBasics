package ro.facemsoft.myfirstapplication.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

import ro.facemsoft.myfirstapplication.AddTodoActivity;
import ro.facemsoft.myfirstapplication.database.DbConstants;
import ro.facemsoft.myfirstapplication.database.DbHelper;
import ro.facemsoft.myfirstapplication.models.ToDoItem;

public class DbInsertWorker extends Thread {
    private Context _context;
    private ToDoItem _item;

    public DbInsertWorker(Context context, ToDoItem item) {
        _context = context;
        _item = item;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DbHelper dbHelper = new DbHelper(_context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbConstants.Items.COLUMN_TITLE, _item.getTitle());
        cv.put(DbConstants.Items.COLUMN_DESC, _item.getDescription());
        cv.put(DbConstants.Items.COLUMN_DUE_DATE,
                sdf.format(_item.getDueDate()));
        cv.put(DbConstants.Items.COLUMN_PRIORITY,
                _item.getPriority().toString());
        db.insert(DbConstants.Items.TABLE_NAME, null, cv);
        db.close();
    }
}
