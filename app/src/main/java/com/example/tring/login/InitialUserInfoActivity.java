package com.example.tring.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tring.R;
import com.example.tring.firebase.FireStoreCallback;
import com.example.tring.firebase.FireStoreService;
import com.example.tring.login.adapter.RegionAdapter;
import com.example.tring.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialUserInfoActivity extends AppCompatActivity implements View.OnClickListener, RegionAdapter.ItemClickListener
{
    private static final String TAG = "InitialUserInfo_TAG";

    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private User user;

    private TextView textView;


    private EditText edit_Nickname;
    private Button btn_Nickname;
    private boolean nicknameOrNot;

    private Spinner spinner_Gender;
    private ArrayAdapter<CharSequence> adapter_Gender;
    //private Spinner spinner_MainRegion;
    //private Spinner spinner_SubRegion;

    private List<String> userRegionList= Arrays.asList("+");
    private List<String> destinationList= Arrays.asList("+");
    private RecyclerView recyclerView_userRegion;
    private RecyclerView recyclerView_Destination;

    private RegionAdapter adapter_userRegion;
    private RegionAdapter adapter_Destination;

    private Button btn_Complete;




    //private ArrayAdapter<CharSequence> adapter_MainRegion;
    //private ArrayAdapter<CharSequence> adapter_SubRegion;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_user_info);

        context = this;
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        user=User.getInstance();


        textView=findViewById(R.id.InitialUserInfoActivity_test);
        textView.setText(firebaseUser.getEmail());

        user.setId(firebaseUser.getEmail());

        //edit_Name=findViewById(R.id.InitialUserInfoActivity_Edit_Name);
        edit_Nickname=findViewById(R.id.InitialUserInfoActivity_Edit_NickName);
        btn_Nickname=findViewById(R.id.InitialUserInfoActivity_Btn_NickName);
        btn_Complete=findViewById(R.id.InitialUserInfoActivity_Btn_Complete);

        btn_Nickname.setOnClickListener(this);
        btn_Complete.setOnClickListener(this);
        setSpinner();

        recyclerView_userRegion= findViewById(R.id.InitialUserInfoActivity_RecyclerView_UserRegion);
        recyclerView_userRegion.setLayoutManager(new GridLayoutManager(context,4));
        adapter_userRegion=new RegionAdapter(context, userRegionList,this,"userRegionInfo");
        recyclerView_userRegion.setAdapter(adapter_userRegion);

        recyclerView_Destination= findViewById(R.id.InitialUserInfoActivity_RecyclerView_Destination);
        recyclerView_Destination.setLayoutManager(new GridLayoutManager(context,4));
        adapter_Destination=new RegionAdapter(context, destinationList,this,"destination");
        recyclerView_Destination.setAdapter(adapter_Destination);
    }


    private void setSpinner()
    {
        spinner_Gender=findViewById(R.id.InitialUserInfoActivity_Spinner_Gender);

        adapter_Gender= ArrayAdapter.createFromResource(context,R.array.spinner_gender,R.layout.support_simple_spinner_dropdown_item);
        adapter_Gender.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_Gender.setAdapter(adapter_Gender);

        spinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Toast.makeText(context, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.InitialUserInfoActivity_Btn_NickName:
                FireStoreService.Auth.checkNickname(context, edit_Nickname.getText().toString(), new FireStoreCallback()
                {
                    @Override
                    public void callback(Object object)
                    {
                        nicknameOrNot=Boolean.valueOf(object.toString());
                        Log.d(TAG,String.valueOf(edit_Nickname.getText().toString()+nicknameOrNot));
                    }
                });
                break;
            case R.id.InitialUserInfoActivity_Btn_Complete:
                if(nicknameOrNot==true)
                    Toast.makeText(context,"이미 존재하는 닉네임을 사용하였습니다. 닉네임을 바꿔주세요!", Toast.LENGTH_SHORT).show();
                else

                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==101)
        {
            userRegionList=data.getStringArrayListExtra("list");
            userRegionList.add("+");

            adapter_userRegion=new RegionAdapter(context, userRegionList,this,"userRegionInfo");
            recyclerView_userRegion.setAdapter(adapter_userRegion);

            //Toast.makeText(context, userRegionList.toString(), Toast.LENGTH_LONG).show();
        }
        if(requestCode==200 && resultCode==201)
        {

            destinationList=data.getStringArrayListExtra("list");
            destinationList.add("+");

            adapter_Destination=new RegionAdapter(context, destinationList,this,"destination");
            recyclerView_Destination.setAdapter(adapter_Destination);

        }
    }

    @Override
    public void onItemClick(int position, String listType)
    {
        if(listType=="userRegionInfo" && position==userRegionList.size()-1) // for last item(must be button)
        {
            startActivityForResult(new Intent(context, RegionActivity.class).putExtra("requestCode",100),100);
            Toast.makeText(context, "userRegionInfo_"+listType, Toast.LENGTH_SHORT).show();
        }
        if(listType=="destination" && position==destinationList.size()-1) // for last item(must be button)
        {
            startActivityForResult(new Intent(context, RegionActivity.class).putExtra("requestCode",200),200);
            Toast.makeText(context, "destination_"+listType, Toast.LENGTH_SHORT).show();
        }
    }
}