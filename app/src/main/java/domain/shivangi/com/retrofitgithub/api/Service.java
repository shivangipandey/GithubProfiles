package domain.shivangi.com.retrofitgithub.api;

import java.util.List;

import domain.shivangi.com.retrofitgithub.model.ItemResponse;
import domain.shivangi.com.retrofitgithub.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Shiavngi Pandey on 01/05/2018.
 */

public interface Service {
    //@GET("/search/users?q=language:java+location:lagos")
    @GET("/users?page=2&per_page=100")
    //Call<ItemResponse> getUsers();
    Call<List<User>> getUsers();

    @GET("https://api.github.com/users/{username}")
    Call<User> getSingleUser(@Path("username") String username);
}
