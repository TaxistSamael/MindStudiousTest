package yakov.bondar.mindtask.ui.activities.category;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yakov.bondar.mindtask.model.CategoryModel;
import yakov.bondar.mindtask.provider.TrainingsProvider;
import yakov.bondar.mindtask.rest.ApiClient;
import yakov.bondar.mindtask.rest.ApiContract;
import yakov.bondar.mindtask.utils.Day;

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mView;
    private Context mContext;

    public CategoryPresenter(CategoryContract.View view) {
        mView = view;
        mContext = (Context) view;
    }

    @Override
    public void loadCategories() {
        ApiContract apiContract = ApiClient.getInstance(ApiContract.class);
        Call<List<CategoryModel>> call = apiContract.getCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                mView.showCategories(response.body().get(0).getCategories());
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                mView.onError(t.getMessage());
            }
        });

    }

    @Override
    public void addCategory(int id, String name, Day day) {
        ContentValues cv = new ContentValues();
        cv.put(TrainingsProvider.TRAINING_DAY, day.name());
        cv.put(TrainingsProvider.TRAINING_CATEGORY_ID, id);
        cv.put(TrainingsProvider.TRAINING_CATEGORY_NAME, name);
        Uri uri = mContext.getContentResolver().insert(TrainingsProvider.TRAINING_CONTENT_URI,cv);
        Log.d("tag", uri.toString());
        mView.onCategoryAdded();
    }
}
