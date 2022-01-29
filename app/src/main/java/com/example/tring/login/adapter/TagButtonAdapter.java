package com.example.tring.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tring.R;

import java.util.List;

public class TagButtonAdapter extends RecyclerView.Adapter<TagButtonAdapter.ViewHolder>
{
    private List<String> list;
    private LayoutInflater inflater;
    public ItemClickListener listener;
    private String listType;

    public interface ItemClickListener
    {
        void onItemClick(int position, String listType);
    }

    public TagButtonAdapter(Context context, List<String> list, TagButtonAdapter.ItemClickListener listener, String listType)
    {
        this.inflater=LayoutInflater.from(context);
        this.list = list;
        this.listener=listener;
        this.listType = listType;
    }

    @NonNull
    @Override
    public TagButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= inflater.inflate(R.layout.item_region_tagbutton,parent,false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TagButtonAdapter.ViewHolder holder, int position)
    {
        holder.textView_tag.setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView textView_tag;
        private TagButtonAdapter.ItemClickListener listener_ViewHolder;


        public ViewHolder(@NonNull View itemView, TagButtonAdapter.ItemClickListener listener_ViewHolder)
        {
            super(itemView);

            this.listener_ViewHolder=listener_ViewHolder;
            textView_tag=itemView.findViewById(R.id.item_region_tag);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view)
        {
            listener_ViewHolder.onItemClick(getAdapterPosition(),listType);
        }
    }
}
