package yakov.bondar.mindtask.ui.activities.day;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yakov.bondar.mindtask.R;
import yakov.bondar.mindtask.ui.activities.category.CategoryActivity;
import yakov.bondar.mindtask.ui.adapters.DayAdapter;
import yakov.bondar.mindtask.utils.Day;

public class DayActivity extends AppCompatActivity implements DayContract.View {

    @BindView(R.id.day_btn_add_category)
    Button mBtnAddCategory;
    @BindView(R.id.day_rl_placeholder)
    RelativeLayout mRlPlaceholder;
    @BindView(R.id.day_lv_exercises)
    ListView mLvCategories;

    private Day mDay;
    private DayContract.Presenter mPresenter;
    private DayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        mDay = (Day) getIntent().getSerializableExtra(Day.DAY);
        String dayName = mDay.toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.day_toolbar);
        ((TextView) (toolbar.findViewById(R.id.toolbar_title))).setText(dayName);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        ButterKnife.bind(this);
        mPresenter = new DayPresenter(this);
        mLvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long position) {
                showDialogDeleteTrainingDialog(view, id, position);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getTrainings(mDay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_day, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.day_add:
                startCategoryActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.day_btn_add_category)
    public void onAddCategoryClick() {
        startCategoryActivity();
    }

    @Override
    public void onTrainingsLoaded(Cursor cursor) {
        startManagingCursor(cursor);
        mAdapter = new DayAdapter(this, cursor, false);
        mLvCategories.setAdapter(mAdapter);
        mRlPlaceholder.setVisibility(View.GONE);
        mLvCategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNoTrainingsShow() {
        mRlPlaceholder.setVisibility(View.VISIBLE);
        mLvCategories.setVisibility(View.GONE);
    }

    private void startCategoryActivity() {
        startActivity(new Intent(this, CategoryActivity.class).
                putExtra(Day.DAY, mDay));
    }

    private void showDialogDeleteTrainingDialog(View view, int id, long position) {

    }
}
