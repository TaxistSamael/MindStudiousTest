package yakov.bondar.mindtask.ui.adapters;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import yakov.bondar.mindtask.R;

import static yakov.bondar.mindtask.provider.TrainingsProvider.TRAINING_CATEGORY_NAME;

public class DayAdapter extends CursorAdapter {

    public DayAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_row_day, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.day_list_tv_name);
        String s = cursor.getString(cursor.getColumnIndex(TRAINING_CATEGORY_NAME));
        title.setText(s);
    }
}
