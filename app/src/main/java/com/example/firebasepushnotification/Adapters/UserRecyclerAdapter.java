package com.example.firebasepushnotification.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firebasepushnotification.Activities.SendActivity;
import com.example.firebasepushnotification.R;
import com.example.firebasepushnotification.Models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    Context context;
    List<User> userList;

    public UserRecyclerAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerAdapter.ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.name.setText(user.getName());

        RequestOptions placeholder = new RequestOptions();
        placeholder.placeholder(R.drawable.default_profile_image);

        Glide.with(context).setDefaultRequestOptions(placeholder).load(user.getImage()).into(holder.imageView);

        final String user_id = userList.get(position).userId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(context, SendActivity.class);
                sendIntent.putExtra("user_id",user_id);
                sendIntent.putExtra("user_name",user.getName());
                context.startActivity(sendIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name;

        private View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            imageView = itemView.findViewById(R.id.user_item_image);
            name = itemView.findViewById(R.id.user_item_name);
        }
    }
}
