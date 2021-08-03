package com.example.contactapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.contactapp.R;
import com.example.contactapp.model.ContactBody;
import com.example.contactapp.viewmodel.ActivityViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String imageUrl = "https://iie-service-dev.workingllama.com";
    private static final String TAG = "MyAdapter";
   // public static HashMap<String, String>  map= new HashMap<>();

    private List<ContactBody.ContactInfo> mList;
    private Context mContext;
    private ActivityViewModel mViewModel;
    private OnMyItemClickListener mOnItemClickListener;

    public MyAdapter(List<ContactBody.ContactInfo> list , Context context , ActivityViewModel viewmodel, OnMyItemClickListener mOnItemClickListener){
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"onBind");
       if(mList.get(position)!=null){
           Log.d(TAG,"onBind inside if");
           ContactBody.ContactInfo object = mList.get(position);


           Glide.with(mContext).load(imageUrl + object.thumbnail).into(((DataItem)holder).imageView);
           ((DataItem)holder).txtName.setText(object.name);
           ((DataItem)holder).txtNumber.setText(object.phone);


//           new Thread(new Runnable() {
//               @Override
//               public void run() {
//                   Glide.with(mContext)
//                           .asBitmap().load(imageUrl + object.thumbnail)
//                           .into(new CustomTarget<Bitmap>() {
//                               @Override
//                               public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                   String path = saveToLocalStorage(resource);
//                                   map.put(mList.get(position).name , path);
//                                   ( new Handler()).post(new Runnable() {
//                                       @Override
//                                       public void run() {
//                                           ((DataItem)holder).imageView.setImageURI(Uri.parse(path));
//                                       }
//                                   });
//
//                               }
//
//                               @Override
//                               public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                               }
//                           });
//               }
//           }).start();


           if(object.isStarred ==1)
           ((DataItem)holder).btnFavorite.setImageResource(R.drawable.ic_baseline_starred);
           else ((DataItem)holder).btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_unstarred);
       }
    }

    public void setFilteredList(List<ContactBody.ContactInfo> filteredList) {
        this.mList = filteredList;
        notifyDataSetChanged();
    }

    public class DataItem extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView imageView;
        TextView txtName;
        TextView txtNumber;
        ImageButton btnFavorite;
        OnMyItemClickListener onItemClickListener;
        public DataItem(@NonNull View itemView  , OnMyItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;

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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG,"onClick");
            onItemClickListener.onItemClick(getAdapterPosition());
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

//   synchronized public String saveToLocalStorage(Bitmap bitmap){
//        File mypath = getOutputMediaFile();
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(mypath);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.close();
//        } catch (Exception e) {
//            Log.d(TAG + 2, e.toString());
//            e.printStackTrace();
//        }
//        return mypath.toString();
//    }

//    private File getOutputMediaFile() {
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
//                + "/Android/data/"
//                + mContext.getPackageName()
//                + "/Files");
//
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                return null;
//            }
//        }
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
//        File mediaFile;
//        String mImageName = "Impartus" + System.currentTimeMillis() + "Image.jpeg";
//        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
//        return mediaFile;
//    }
}
