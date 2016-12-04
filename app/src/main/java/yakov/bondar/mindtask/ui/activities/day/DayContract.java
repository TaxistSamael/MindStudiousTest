package yakov.bondar.mindtask.ui.activities.day;


import android.database.Cursor;

import yakov.bondar.mindtask.utils.Day;

public interface DayContract {
    interface View{
        void onTrainingsLoaded(Cursor cursor);
        void onNoTrainingsShow();
    }
    interface Presenter {
        void getTrainings(Day day);
        void removeTraining(int day, int id);
    }
}
