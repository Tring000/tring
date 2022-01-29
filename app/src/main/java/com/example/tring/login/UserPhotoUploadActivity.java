package com.example.tring.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tring.MainActivity;
import com.example.tring.R;
import com.example.tring.login.adapter.PhotoUploadAdapter;

public class UserPhotoUploadActivity extends AppCompatActivity implements PhotoUploadAdapter.ItemClickListener
{

    private Context context;
    private RecyclerView recyclerView;
    private PhotoUploadAdapter adapter;

    private EditText selfIntroduction;
    private Button btn_Ok;

    private Uri[] uriArray = new Uri[6];
    private String nullUri="-";
    private int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photo_upload);

        context=this;
        for (int i =0; i<6;i++)
            uriArray[i]=Uri.parse(nullUri);

        recyclerView=findViewById(R.id.UserPhotoUploadActivity_RecyclerView_PhotoUpload);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        adapter = new PhotoUploadAdapter(context, getContentResolver(),getResources(), uriArray,this);
        recyclerView.setAdapter(adapter);

        selfIntroduction=findViewById(R.id.UserPhotoUploadActivity_Edit_SelfIntroduction);
        btn_Ok=findViewById(R.id.UserPhotoUploadActivity_Btn_OK);
        btn_Ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(context, MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            Uri uri = data.getData();
            if (count<6)
                uriArray[count++] = uri;
            else
                Toast.makeText(context, "최대 6장까지만 등록가능합니다!", Toast.LENGTH_SHORT).show();

            adapter= new PhotoUploadAdapter(context, getContentResolver(),getResources(), uriArray, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(int position)
    {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),0);
    }
}