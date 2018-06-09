package domain.shivangi.com.retrofitgithub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shiavngi Pandey on 01/05/2018.
 */

public class User {

    @SerializedName("login")
    @Expose
    private  String login;
    @SerializedName("avatar_url")
    @Expose
    private  String avatarUrl;
    @SerializedName("html_url")
    @Expose
    private  String htmlUrl;

    public User(String login, String avatarUrl, String htmlUrl ){
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
    }

    public  String getLogin(){
        return login;
    }
    public String getAvatarUrl(){
        return avatarUrl;
    }
    public String getHtmlUrl(){
        return htmlUrl;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }
    public void setHtmlUrl(String htmlUrl){
        this.htmlUrl = htmlUrl;
    }
}
