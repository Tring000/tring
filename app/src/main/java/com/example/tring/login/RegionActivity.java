package com.example.tring.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    private List<String> list_City;
    private List<String> list_SubRegion;
    private List<String> list_Selected;

    private RecyclerView recyclerView_City;
    private RecyclerView recyclerView_SubRegion;
    private RecyclerView recyclerView_Selected;

    private RegionAdapter adapter_City;
    private RegionAdapter adapter_SubRegion;
    private RegionAdapter adapter_Selected;

    private Button btn_Ok;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        context=this;

        intent=getIntent();
        requestCode=intent.getIntExtra("requestCode",50);



        list_Selected =new ArrayList<>();
        list_City= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_city)));


        recyclerView_City=findViewById(R.id.RegionActivity_RecyclerView_City);
        recyclerView_SubRegion=findViewById(R.id.RegionActivity_RecyclerView_SubRegion);
        recyclerView_Selected =findViewById(R.id.RegionActivity_RecyclerView_Selected);

        recyclerView_City.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_SubRegion.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Selected.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        adapter_City= new RegionAdapter(context, list_City,this,"city");
        recyclerView_City.setAdapter(adapter_City);

        btn_Ok=findViewById(R.id.RegionActivity_Btn_OK);
        btn_Ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(list_Selected.isEmpty())
                {
                    Toast.makeText(context, "Please select your main region ", Toast.LENGTH_SHORT).show();
                }
                if (requestCode==100)
                {
                    intent.putStringArrayListExtra("list", (ArrayList<String>) list_Selected);
                    setResult(101, intent);

                    list_City.clear();
                    list_SubRegion.clear();

                    finish();
                }
                if (requestCode==200)
                {
                    intent.putStringArrayListExtra("list", (ArrayList<String>) list_Selected);
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


        if (listType=="city")
        {
            String id = "array_subregion_"+list_City.get(position);
            int resourceId = context.getResources().getIdentifier(id,"array",context.getPackageName());

            list_SubRegion= new ArrayList<String>(Arrays.asList(getResources().getStringArray(resourceId)));

            adapter_SubRegion=new RegionAdapter(context,list_SubRegion,this,"subregion");
            recyclerView_SubRegion.setAdapter(adapter_SubRegion);

        }
        else if (listType=="subregion" && requestCode==100 )
        {
            if (!list_Selected.contains(list_SubRegion.get(position)) && list_Selected.size()<2)
            {
                list_Selected.add(list_SubRegion.get(position));

                adapter_Selected =new RegionAdapter(context, list_Selected,this,"selected");
                recyclerView_Selected.setAdapter(adapter_Selected);
            }
            else if(!list_Selected.contains(list_SubRegion.get(position)) && list_Selected.size()>=2)
            {
                Toast.makeText(context, "Max:2", Toast.LENGTH_SHORT).show();
            }
            else
            {
                list_Selected.remove(list_SubRegion.get(position));

                adapter_Selected =new RegionAdapter(context, list_Selected,this,"selected");
                recyclerView_Selected.setAdapter(adapter_Selected);
            }
        }
        else if (listType=="subregion" && requestCode == 200)
        {
            if (!list_Selected.contains(list_SubRegion.get(position)) && list_Selected.size()<4)
            {
                list_Selected.add(list_SubRegion.get(position));

                adapter_Selected =new RegionAdapter(context, list_Selected,this,"selected");
                recyclerView_Selected.setAdapter(adapter_Selected);
            }
            else if(!list_Selected.contains(list_SubRegion.get(position)) && list_Selected.size()>=4)
            {
                Toast.makeText(context, "Max:4", Toast.LENGTH_SHORT).show();
            }
            else
            {
                list_Selected.remove(list_SubRegion.get(position));

                adapter_Selected =new RegionAdapter(context, list_Selected,this,"selected");
                recyclerView_Selected.setAdapter(adapter_Selected);
            }
        }
       // Toast.makeText(RegionActivity.this, "item clicked", Toast.LENGTH_SHORT).show();

        //array_subregion_

    }
}