package com.example.tring.login.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tring.R;

import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder>
{
    private List<String> list;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private String listType;

    public interface ItemClickListener
    {
        void onItemClick(int position, String listType);
    }

    public RegionAdapter(Context context, List<String> list, ItemClickListener listener, String listType)
    {
        Log.d("RegionAdapter_TAG",list.toString());
        this.inflater=LayoutInflater.from(context);
        this.list = list;
        this.listener=listener;
        this.listType = listType;
    }

    @NonNull
    @Override
    public RegionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=null;
        if (listType=="city")
        {
             view = inflater
                    .inflate(R.layout.list_region_city,parent,false);

        }
        else if (listType=="subregion")
        {
             view = inflater
                    .inflate(R.layout.list_region_subregion,parent,false);

        }
        else if (listType=="userRegion"|| listType=="destination")
        {
            view = inflater
                    .inflate(R.layout.list_region_regionuser,parent,false);

        }
        else if (listType=="userRegionInfo" )
        {
            view = inflater
                    .inflate(R.layout.list_regioninfo_regionuser,parent,false);
        }
        else
        {
            Log.d("RegionAdapter_TAG","listType Error");
        }
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionAdapter.ViewHolder holder, int position)
    {
        holder.getTextView_region().setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView textView_region;
        private ItemClickListener listener_ViewHolder;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener_ViewHolder)
        {
            super(itemView);

            this.listener_ViewHolder=listener_ViewHolder;
            if (listType=="city")
                textView_region=itemView.findViewById(R.id.list_item_region_city);
            if (listType=="subregion")
                textView_region=itemView.findViewById(R.id.list_item_region_subregion);
            if (listType=="userRegion"|| listType=="destination")
                textView_region=itemView.findViewById(R.id.list_item_region_regionuser);
            if (listType=="userRegionInfo")
                textView_region=itemView.findViewById(R.id.list_item_regionuser_regionuser);
            //if (listType=="destination")
              //  textView_region=itemView.findViewById(R.id.list_item_regionuser_regionuser);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener_ViewHolder.onItemClick(getAdapterPosition(),listType);
        }

        public TextView getTextView_region()
        {
            return textView_region;
        }
    }
}
