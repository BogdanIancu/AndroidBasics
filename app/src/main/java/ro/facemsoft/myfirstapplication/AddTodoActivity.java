package ro.facemsoft.myfirstapplication;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ro.facemsoft.myfirstapplication.database.DbConstants;
import ro.facemsoft.myfirstapplication.database.DbHelper;
import ro.facemsoft.myfirstapplication.models.ToDoItem;
import ro.facemsoft.myfirstapplication.workers.DbInsertWorker;

public class AddTodoActivity extends AppCompatActivity {

    private Date currentDate;
    private Spinner prioritySpinner;
    private EditText dueDateEditText;
    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Intent intent = getIntent();
        if (intent != null) {
            currentDate = (Date) intent.getSerializableExtra("today");
        }

        prioritySpinner = findViewById(R.id.priority_spinner);
        dueDateEditText = findViewById(R.id.date_edit_text);
        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.details_edit_text);

        ToDoItem.Priority[] priorities = ToDoItem.Priority.values();
        ArrayAdapter<ToDoItem.Priority> adapter = new ArrayAdapter<>(AddTodoActivity.this,
                R.layout.support_simple_spinner_dropdown_item, priorities);
        prioritySpinner.setAdapter(adapter);
    }

    public void displayDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        DatePickerDialog dialog =
                new DatePickerDialog(AddTodoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar newDateCalender = Calendar.getInstance();
                                newDateCalender.set(year, month, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String value = sdf.format(newDateCalender.getTime());
                                dueDateEditText.setText(value);
                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void saveData(View view) {
        if (titleEditText.getText().toString().isEmpty() ||
                descriptionEditText.getText().toString().isEmpty() ||
                dueDateEditText.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(view, "All fields are mandatory", Snackbar.LENGTH_LONG);
            //titleEditText.setError("something");
            snackbar.show();
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dueDate = null;
            try {
                dueDate = sdf.parse(dueDateEditText.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ToDoItem.Priority priority =
                    ToDoItem.Priority.valueOf(prioritySpinner.getSelectedItem().toString());
            ToDoItem item = new ToDoItem(titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(), dueDate, currentDate, priority);
            Intent intent = new Intent();
            intent.putExtra("item", item);
            setResult(RESULT_OK, intent);

            DbInsertWorker worker = new DbInsertWorker(AddTodoActivity.this, item);
            worker.start();

            finish();
        }
    }
}