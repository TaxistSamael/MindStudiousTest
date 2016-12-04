package yakov.bondar.mindtask.ui.activities.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import yakov.bondar.mindtask.R;
import yakov.bondar.mindtask.model.Categories;
import yakov.bondar.mindtask.ui.adapters.CategoriesAdapter;
import yakov.bondar.mindtask.utils.Day;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View, CategoriesAdapter.CategoryOnClickListener {

    public static final String TAG_CATEGORY_DIALOG_FRAGMENT = "CategoryDialogFragment";
    public static final String BUNDLE_KEY_CATEGORY_NAME = "category_name";

    private CategoryContract.Presenter mPresenter;
    private Day mDay;

    @BindView(R.id.lv_categories)
    ListView mLvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mDay = (Day) getIntent().getSerializableExtra(Day.DAY);


        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        mPresenter = new CategoryPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadCategories();
    }

    @Override
    public void showCategories(Categories[] categories) {
        mLvCategories.setAdapter(new CategoriesAdapter(this, categories, this));
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCategoryAdded() {
        finish();
    }

    @Override
    public void onCategoryAdd(int id, String name) {
        mPresenter.addCategory(id, name, mDay);
    }

    @Override
    public void onCategoryNameClick(String name) {
        CategoryDialogFragment dialog = new CategoryDialogFragment();
        dialog.setCancelable(true);
        Bundle arguments = new Bundle();
        arguments.putString(BUNDLE_KEY_CATEGORY_NAME, name);
        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(), TAG_CATEGORY_DIALOG_FRAGMENT);
    }

    public static class CategoryDialogFragment extends android.support.v4.app.DialogFragment {

        private String mName;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mName = getArguments().getString(BUNDLE_KEY_CATEGORY_NAME);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_category_dialog_background);
            }
            View view = inflater.inflate(R.layout.dialog_category_name, container);
            ((TextView) view.findViewById(R.id.tv_dialog_category_name)).setText(mName);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setContentView(view);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
