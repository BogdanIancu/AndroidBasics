package ro.facemsoft.myfirstapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

import ro.facemsoft.myfirstapplication.R;
import ro.facemsoft.myfirstapplication.models.ToDoItem;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    public ToDoItemAdapter(Context context, int resource, List<ToDoItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_template, null);
        }
        TextView titleTextView =
                convertView.findViewById(R.id.title_text_view);
        TextView detailsTextView =
                convertView.findViewById(R.id.details_text_view);
        TextView dueDateTextView =
                convertView.findViewById(R.id.due_date_text_view);
        TextView priorityTextView =
                convertView.findViewById(R.id.priority_text_view);

        ToDoItem item = getItem(position);
        titleTextView.setText(item.getTitle());
        detailsTextView.setText(item.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dueDateTextView.setText(sdf.format(item.getDueDate()));
        priorityTextView.setText(item.getPriority().toString());

        return convertView;
    }


}
