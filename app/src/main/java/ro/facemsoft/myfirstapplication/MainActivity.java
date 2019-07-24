package ro.facemsoft.myfirstapplication;

import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listButton = findViewById(R.id.button_list);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Toast de test", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.ro"));
                startActivity(intent);
            }
        });
    }

    public void addToDo(View view) {
        //Toast.makeText(this, "Toast de test", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        intent.putExtra("today", new Date());
        startActivity(intent);
    }
}
