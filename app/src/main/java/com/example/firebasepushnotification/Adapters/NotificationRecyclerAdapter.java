package com.example.firebasepushnotification.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firebasepushnotification.Models.Notification;
import com.example.firebasepushnotification.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder>{

    private List<Notification> mNotificationsList;
    private FirebaseFirestore mFireStore;
    private Context context;

    public NotificationRecyclerAdapter(List<Notification> mNotificationsList, Context context) {
        this.mNotificationsList = mNotificationsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final NotificationRecyclerAdapter.ViewHolder holder, final int position) {

        Notification notification = mNotificationsList.get(position);

        mFireStore = FirebaseFirestore.getInstance();

        String from_id = notification.getFrom();

        holder.mNotifMessage.setText(notification.getMessage());

        mFireStore.collection("Users").document(from_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");
                String image = documentSnapshot.getString("image");

                holder.mNotifName.setText(name);

                RequestOptions placeholder = new RequestOptions();
                placeholder.placeholder(R.drawable.default_profile_image);

                Glide.with(context).setDefaultRequestOptions(placeholder).load(image).into(holder.mNotifImage);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mNotificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mNotifImage;
        private TextView mNotifName;
        private TextView mNotifMessage;

        public ViewHolder(View itemView){
            super(itemView);

            mNotifImage = itemView.findViewById(R.id.notif_image);
            mNotifName = itemView.findViewById(R.id.notif_name);
            mNotifMessage = itemView.findViewById(R.id.notif_message);

        }
    }
}