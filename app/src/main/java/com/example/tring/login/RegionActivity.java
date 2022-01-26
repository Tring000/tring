package com.example.tring.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tring.R;
import com.example.tring.login.adapter.RegionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionActivity extends AppCompatActivity implements RegionAdapter.ItemClickListener
{
    private final static String TAG="RegionActivity_TAG";

    private Context context;
    private Intent intent;

    private int requestCode;
    private String listType;

    private List<String> userRegionList;
    private List<String> list_City;
    private List<String> list_SubRegion;

    private RecyclerView recyclerView_City;
    private RecyclerView recyclerView_SubRegion;
    private RecyclerView recyclerView_UserRegion;

    private RegionAdapter adapter_City;
    private RegionAdapter adapter_SubRegion;
    private RegionAdapter adapter_UserRegion;

    private Button btn_Ok;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        context=this;

        intent=getIntent();
        requestCode=intent.getIntExtra("requestCode",50);



        userRegionList=new ArrayList<>();
        list_City= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_city)));


        recyclerView_City=findViewById(R.id.RegionActivity_RecyclerView_City);
        recyclerView_SubRegion=findViewById(R.id.RegionActivity_RecyclerView_SubRegion);
        recyclerView_UserRegion=findViewById(R.id.RegionActivity_RecyclerView_UserRegion);

        recyclerView_City.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_SubRegion.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_UserRegion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        adapter_City= new RegionAdapter(context, list_City,this,"city");
        recyclerView_City.setAdapter(adapter_City);

        btn_Ok=findViewById(R.id.RegionActivity_Btn_OK);
        btn_Ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(userRegionList.isEmpty())
                {
                    Toast.makeText(context, "Please select your main region ", Toast.LENGTH_SHORT).show();
                }
                if (listType=="subregion")
                {
                    intent.putStringArrayListExtra("list", (ArrayList<String>) userRegionList);
                    setResult(101, intent);

                    list_City.clear();
                    list_SubRegion.clear();

                    finish();
                }
                if (listType=="destination")
                {
                    intent.putStringArrayListExtra("list", (ArrayList<String>) userRegionList);
                    setResult(201, intent);

                    list_City.clear();
                    list_SubRegion.clear();

                    finish();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, String _listType)
    {
        this.listType=_listType;

        Toast.makeText(context, "onItemClick_"+_listType, Toast.LENGTH_SHORT).show();

        if (listType=="city")
        {
            String id = "array_subregion_"+list_City.get(position);
            int resourceId = context.getResources().getIdentifier(id,"array",context.getPackageName());

            list_SubRegion= new ArrayList<String>(Arrays.asList(getResources().getStringArray(resourceId)));

            if (requestCode==100)
            {
                adapter_SubRegion=new RegionAdapter(context,list_SubRegion,this,"subregion");
                recyclerView_SubRegion.setAdapter(adapter_SubRegion);
            }
            if (requestCode==200)
            {
                adapter_SubRegion=new RegionAdapter(context,list_SubRegion,this,"destination");
                recyclerView_SubRegion.setAdapter(adapter_SubRegion);
            }
        }
        else if (listType=="subregion")
        {
            if (!userRegionList.contains(list_SubRegion.get(position)) && userRegionList.size()<2)
            {
                userRegionList.add(list_SubRegion.get(position));

                adapter_UserRegion=new RegionAdapter(context,userRegionList,this,"userRegion");
                recyclerView_UserRegion.setAdapter(adapter_UserRegion);
            }
            else if(!userRegionList.contains(list_SubRegion.get(position)) &&userRegionList.size()>=2)
            {
                Toast.makeText(context, "Max:2", Toast.LENGTH_SHORT).show();
            }
            else
            {
                userRegionList.remove(list_SubRegion.get(position));

                adapter_UserRegion=new RegionAdapter(context,userRegionList,this,"userRegion");
                recyclerView_UserRegion.setAdapter(adapter_UserRegion);
            }
        }
        else if (listType=="destination")
        {
            if (!userRegionList.contains(list_SubRegion.get(position)) && userRegionList.size()<4)
            {
                userRegionList.add(list_SubRegion.get(position));

                adapter_UserRegion=new RegionAdapter(context,userRegionList,this,"destination");
                recyclerView_UserRegion.setAdapter(adapter_UserRegion);
            }
            else if(!userRegionList.contains(list_SubRegion.get(position)) &&userRegionList.size()>=4)
            {
                Toast.makeText(context, "Max:4", Toast.LENGTH_SHORT).show();
            }
            else
            {
                userRegionList.remove(list_SubRegion.get(position));

                adapter_UserRegion=new RegionAdapter(context,userRegionList,this,"destination");
                recyclerView_UserRegion.setAdapter(adapter_UserRegion);
            }
        }
       // Toast.makeText(RegionActivity.this, "item clicked", Toast.LENGTH_SHORT).show();

        //array_subregion_

    }
}