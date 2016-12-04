package yakov.bondar.mindtask.ui.activities.category;

import yakov.bondar.mindtask.model.Categories;
import yakov.bondar.mindtask.utils.Day;

public interface CategoryContract {
    interface View {
        void showCategories(Categories[] categories);
        void onError(String error);
        void onCategoryAdded();
    }

    interface Presenter {
        void loadCategories();
        void addCategory(int id, String name, Day day);
    }
}
