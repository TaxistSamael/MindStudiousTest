package yakov.bondar.mindtask.ui.activities.day;


import android.content.Context;
import android.database.Cursor;

import yakov.bondar.mindtask.provider.TrainingsProvider;
import yakov.bondar.mindtask.utils.Day;

public class DayPresenter implements DayContract.Presenter {

    private DayContract.View mView;
    private Context mContext;

    public DayPresenter(DayContract.View view) {
        mView = view;
        mContext = (Context) view;
    }

    @Override
    public void getTrainings(Day day) {
        String selection = "day = ?";
        String [] selectionArgs = new String[] {day.name()};
        Cursor cursor = mContext.getContentResolver()
                .query(TrainingsProvider.TRAINING_CONTENT_URI,null,selection,selectionArgs,null);
        if (cursor != null && cursor.getCount()>0){
            mView.onTrainingsLoaded(cursor);
        } else {
            mView.onNoTrainingsShow();
        }
    }

    @Override
    public void removeTraining(int day, int id) {

    }
}
