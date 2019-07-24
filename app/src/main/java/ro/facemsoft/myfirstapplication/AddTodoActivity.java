package ro.facemsoft.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Intent intent = getIntent();
        if(intent != null) {
            Date currentDate = (Date)intent.getSerializableExtra("today");
            if(currentDate != null) {
                Toast.makeText(AddTodoActivity.this,
                        currentDate.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
