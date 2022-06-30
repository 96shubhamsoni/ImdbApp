package com.example.ImdbApp.view;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ImdbApp.R;
import com.example.ImdbApp.model.MovieBody;
import com.example.ImdbApp.viewmodel.ActivityViewModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String imageUrl = "https://image.tmdb.org/t/p/w500";
    private static final String TAG = "MyAdapter";
   // public static HashMap<String, String>  map= new HashMap<>();

    private List<MovieBody.Movie> mList;
    private Context mContext;
    private ActivityViewModel mViewModel;
    private OnMyItemClickListener mOnItemClickListener;

    public MyAdapter(List<MovieBody.Movie> list , Context context , ActivityViewModel viewmodel, OnMyItemClickListener mOnItemClickListener){
        this.mList = list;
        this.mContext = context;
        this.mViewModel = viewmodel;
        this.mOnItemClickListener = mOnItemClickListener;
        Log.d(TAG,"onMyAdapter");
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new DataItem(view , mOnItemClickListener);
    }





    public class DataItem extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnMyItemClickListener onMyItemClickListener;
        ImageView imageView;
        TextView title;
        TextView overview;
        public DataItem(@NonNull View itemView  , OnMyItemClickListener onItemClickListener) {
            super(itemView);

            onMyItemClickListener = onItemClickListener;

            this.imageView = itemView.findViewById(R.id.movie_pic);
            this.title = itemView.findViewById(R.id.movie_title);
            this.overview = itemView.findViewById(R.id.movie_overview);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG,"onClick");
            onMyItemClickListener.onItemClick(getAdapterPosition());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"onBind");
        if(mList.get(position)!=null){
            Log.d(TAG,"onBind inside if");
            MovieBody.Movie object = mList.get(position);

            Glide.with(mContext)
                    .load(imageUrl+object.poster_path)
                    .centerCrop()
                    .into(((DataItem)holder).imageView);

            ((DataItem)holder).title.setText(object.title);
            ((DataItem)holder).overview.setText(object.overview);


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

    public interface OnMyItemClickListener {
           void onItemClick(int position);
    }
}
