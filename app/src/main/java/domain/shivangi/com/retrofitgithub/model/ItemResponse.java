package domain.shivangi.com.retrofitgithub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shiavngi Pandey on 01/05/2018.
 */

public class ItemResponse {

    private List<User> items;

    public List<User> getItems(){
        return items;
    }
    public void setItems(List<User> items){
        this.items = items;
    }
}
