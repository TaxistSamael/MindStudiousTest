package yakov.bondar.mindtask.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import yakov.bondar.mindtask.model.CategoryModel;

public interface ApiContract {

    @GET("api/v1/application/gym_exercises/category")
    Call<List<CategoryModel>> getCategories();
}
