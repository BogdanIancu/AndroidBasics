package ro.facemsoft.myfirstapplication.workers;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import ro.facemsoft.myfirstapplication.adapters.ToDoItemAdapter;
import ro.facemsoft.myfirstapplication.models.ToDoItem;

public class AvatarsWorker
        extends AsyncTask<ArrayList<ToDoItem>, Void, ArrayList<ToDoItem>> {
    private ToDoItemAdapter _adapter;

    public AvatarsWorker(ToDoItemAdapter adapter) {
        _adapter = adapter;
    }

    @Override
    protected ArrayList<ToDoItem> doInBackground(ArrayList<ToDoItem>... arrayLists) {
        ArrayList<ToDoItem> list = arrayLists[0];

        for(ToDoItem item : list) {
            try {
                URL url =
                        new URL("https://api.adorable.io/avatars/48/" +
                                item.getTitle() +".png");
                HttpsURLConnection connection =
                        (HttpsURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                item.image = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<ToDoItem> toDoItems) {
        _adapter.notifyDataSetChanged();
    }
}
