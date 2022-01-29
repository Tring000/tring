package com.example.tring.login.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tring.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class PhotoUploadAdapter extends RecyclerView.Adapter<PhotoUploadAdapter.ViewHolder>
{
    private ContentResolver contentResolver;
    private Resources resources;
    private List<Uri> list;
    private LayoutInflater inflater;
    public PhotoUploadAdapter.ItemClickListener listener;

    public interface ItemClickListener
    {
        void onItemClick(int position);
    }

    public PhotoUploadAdapter(Context context,ContentResolver contentResolver, Resources resources, Uri[] array, PhotoUploadAdapter.ItemClickListener listener)
    {
        this.inflater=LayoutInflater.from(context);
        this.contentResolver=contentResolver;
        this.resources=resources;
        this.list= Arrays.asList(array);
        this.listener=listener;
    }
    @NonNull
    @Override
    public PhotoUploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= inflater.inflate(R.layout.item_photo_userphoto,parent,false);

        return new PhotoUploadAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoUploadAdapter.ViewHolder holder, int position)
    {
        holder.imageView_Photo.setImageResource(R.drawable.small_plus);

        Bitmap bitmap;
        InputStream inputStream = null;

        try
        {
            inputStream = contentResolver.openInputStream(list.get(position));
            holder.imageView_Photo.setImageBitmap(decodeSampledBitmapFromResource(inputStream, 100, 100));
        }
        catch (FileNotFoundException exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView_Photo;
        PhotoUploadAdapter.ItemClickListener listener_ViewHolder;

        public ViewHolder(@NonNull View itemView, PhotoUploadAdapter.ItemClickListener listener_ViewHolder)
        {
            super(itemView);
            this.listener_ViewHolder=listener_ViewHolder;
            imageView_Photo=itemView.findViewById(R.id.item_photo_userphoto);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener_ViewHolder.onItemClick(getAdapterPosition());
        }
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(InputStream inputStream, int reqWidth, int reqHeight)
    {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }


}
