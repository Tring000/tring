package com.example.tring.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.tring.R;

public class TripDestinationActivity extends AppCompatActivity
{

    private RecyclerView recyclerView_Country;
    private RecyclerView recyclerView_City;
    private RecyclerView recyclerView_Borough;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_destination);
    }
}