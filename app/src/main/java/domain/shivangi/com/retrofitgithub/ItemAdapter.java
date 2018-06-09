package domain.shivangi.com.retrofitgithub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import domain.shivangi.com.retrofitgithub.controller.DetailActivity;
import domain.shivangi.com.retrofitgithub.model.User;

/**
 * Created by Shiavngi Pandey on 01/05/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<User> items;
    private Context context;

    public ItemAdapter(Context context, List<User> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getLogin());
        holder.githublink1.setText(items.get(position).getHtmlUrl());

        Picasso.with(context)
                .load(items.get(position).getAvatarUrl())
                .placeholder(R.drawable.load)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(User item, ItemAdapter adapter){
        if(items.contains(item))
            items.remove(item);
        items.add(0,item);
        adapter.notifyItemInserted(items.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title, githublink1;
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            githublink1 = (TextView)itemView.findViewById(R.id.gethublink1);
            imageView = (ImageView)itemView.findViewById(R.id.cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        User clickedDataItem = items.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("login", items.get(pos).getLogin());
                        intent.putExtra("htmlUrl",items.get(pos).getHtmlUrl());
                        intent.putExtra("avatarUrl",items.get(pos).getAvatarUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked "+ clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
