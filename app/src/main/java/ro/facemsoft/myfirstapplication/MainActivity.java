package ro.facemsoft.myfirstapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ro.facemsoft.myfirstapplication.models.ToDoItem;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ToDoItem> itemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listButton = findViewById(R.id.button_list);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,
//                        "Toast de test", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.ro"));
//                startActivity(intent);
                Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
                intent.putExtra("items", itemsList);
                startActivity(intent);
            }
        });

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);

        if(name == null || name.isEmpty()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            dialogBuilder.setTitle("Please input your name");
            final EditText input = new EditText(MainActivity.this);
            dialogBuilder.setView(input);
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     SharedPreferences.Editor editor = sharedPreferences.edit();
                     editor.putString("name", input.getText().toString());
                     editor.commit();
                }
            });
            dialogBuilder.setNegativeButton("Cancel", null);
            dialogBuilder.create().show();
        } else {
            Toast.makeText(MainActivity.this, String.format("Welcome back, %s!", name),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void addToDo(View view) {
        //Toast.makeText(this, "Toast de test", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        intent.putExtra("today", new Date());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            ToDoItem item = (ToDoItem) data.getSerializableExtra("item");
            itemsList.add(item);
        }
    }
}
