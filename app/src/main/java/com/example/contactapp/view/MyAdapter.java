package com.example.contactapp.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contactapp.R;
import com.example.contactapp.model.ContactBody;
import com.example.contactapp.viewmodel.ActivityViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String imageUrl = "https://iie-service-dev.workingllama.com";
    private static final String TAG = "MyAdapter";

    private List<ContactBody.ContactInfo> mList;
    private Context mContext;
    private ActivityViewModel mViewModel;

    public MyAdapter(List<ContactBody.ContactInfo> list , Context context , ActivityViewModel viewmodel){
        this.mList = list;
        this.mContext = context;
        this.mViewModel = viewmodel;
        Log.d(TAG,"onMyAdapter");
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new DataItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"onBind");
       if(mList.get(position)!=null){
           Log.d(TAG,"onBind inside if");
           ContactBody.ContactInfo object = mList.get(position);

           Glide.with(mContext).load(imageUrl + object.thumbnail).into(((DataItem)holder).imageView);
           ((DataItem)holder).txtName.setText(object.name);
           ((DataItem)holder).txtNumber.setText(object.phone);

           if(object.isStarred ==1)
           ((DataItem)holder).btnFavorite.setImageResource(R.drawable.ic_baseline_starred);
           else ((DataItem)holder).btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_unstarred);
       }
    }

    public void setFilteredList(List<ContactBody.ContactInfo> filteredList) {
        this.mList = filteredList;
        notifyDataSetChanged();
    }

    public class DataItem extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView txtName;
        TextView txtNumber;
        ImageButton btnFavorite;
        public DataItem(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_pic);
            txtName = itemView.findViewById(R.id.name);
            txtNumber = itemView.findViewById(R.id.number);
            btnFavorite = itemView.findViewById(R.id.favourite);

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d(TAG, "me "+position);
                    ContactBody.ContactInfo object = mList.get(position);

                    if(object.isStarred==0){
                        object.isStarred = 1;
                        mViewModel.markItStar(object.id);
                    }

                    else {
                        object.isStarred = 0;
                        mViewModel.markUnStar(object.id);
                    }

                    notifyItemChanged(position ,object);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void clearData(){
        if(mList!=null)
            mList.clear();
        notifyDataSetChanged();
    }
}
