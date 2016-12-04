package yakov.bondar.mindtask.ui.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yakov.bondar.mindtask.R;
import yakov.bondar.mindtask.ui.activities.day.DayActivity;
import yakov.bondar.mindtask.utils.Day;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_monday)
    Button mBtnMonday;
    @BindView(R.id.btn_tuesday)
    Button mBtnTuesday;
    @BindView(R.id.btn_wednesday)
    Button mBtnWednesday;
    @BindView(R.id.btn_thursday)
    Button mBtnThursday;
    @BindView(R.id.btn_friday)
    Button mBtnFriday;
    @BindView(R.id.btn_saturday)
    Button mBtnSaturday;
    @BindView(R.id.btn_sunday)
    Button mBtnSunday;
    @BindView(R.id.btn_timer)
    Button mBtnTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick({R.id.btn_monday, R.id.btn_tuesday, R.id.btn_wednesday,
            R.id.btn_thursday, R.id.btn_friday, R.id.btn_saturday,
            R.id.btn_sunday, R.id.btn_timer})
    public void onDayClick(Button day) {
        switch (day.getId()) {
            case R.id.btn_monday:
                startDayActivity(Day.MONDAY);
                break;
            case R.id.btn_tuesday:
                startDayActivity(Day.TUESDAY);
                break;
            case R.id.btn_wednesday:
                startDayActivity(Day.WEDNESDAY);
                break;
            case R.id.btn_thursday:
                startDayActivity(Day.THURSDAY);
                break;
            case R.id.btn_friday:
                startDayActivity(Day.FRIDAY);
                break;
            case R.id.btn_saturday:
                startDayActivity(Day.SATURDAY);
                break;
            case R.id.btn_sunday:
                startDayActivity(Day.SUNDAY);
                break;
            case R.id.btn_timer:
                Toast.makeText(this, R.string.main_toast_no_such_functionality,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startDayActivity(Day day) {
        startActivity(new Intent(this, DayActivity.class).
                putExtra(Day.DAY, day));
    }
}
