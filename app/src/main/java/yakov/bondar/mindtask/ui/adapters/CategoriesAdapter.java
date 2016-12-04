package yakov.bondar.mindtask.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yakov.bondar.mindtask.R;
import yakov.bondar.mindtask.model.Categories;

public class CategoriesAdapter extends BaseAdapter {

    private List<Categories> mCategoriesList;
    private Context mContext;
    private CategoryOnClickListener mListener;

    public CategoriesAdapter(Context context, Categories[] array, CategoryOnClickListener listener) {
        mContext = context;
        mCategoriesList = Arrays.asList(array);
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mCategoriesList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCategoriesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.list_row_categories, viewGroup, false);
        }

        final ViewHolder vHolder = new ViewHolder(view);
        vHolder.mTvCategoryName.setText(mCategoriesList.get(i).getName());

        vHolder.mTvCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategoryNameClick(mCategoriesList.get(i).getName());
            }
        });

        vHolder.mIbCategoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategoryAdd(mCategoriesList.get(i).getId(), mCategoriesList.get(i).getName());
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.category_list_tv_name)
        TextView mTvCategoryName;
        @BindView(R.id.category_list_iv_add)
        ImageView mIbCategoryAdd;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface CategoryOnClickListener {
        void onCategoryAdd(int id, String name);

        void onCategoryNameClick(String name);
    }


}
