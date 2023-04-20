package com.example.myapplication.voters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.candidates.Candidate;
import com.example.myapplication.candidates.MyAdapter;
import com.example.myapplication.candidates.MyAdapter2;
import com.example.myapplication.candidates.MyAdapter4;
import com.example.myapplication.candidates.MyAdapter5;
import com.example.myapplication.candidates.candidateDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class candlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter4 myAdapter;
    ArrayList<Candidate> list;
    ImageView image;
    TextView textt;
    String eename= MyAdapter5.data.getEname();
    // String eename="aaa";
    DatabaseReference refer1 = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Elections").child(eename).child("candidates");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candlist);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.item2);
        recyclerView = findViewById(R.id.userList);
        recyclerView.setVisibility(View.INVISIBLE);

        image=findViewById(R.id.imageView21);
        textt=findViewById(R.id.textView22);
        database = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Elections").child(eename).child("candidates");
        printChildrenCount(database);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter4(this,list);
        recyclerView.setAdapter(myAdapter);




//        refer1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    Candidate user = dataSnapshot.getValue(Candidate.class);
//                    list.add(user);
//
//
//                }
//                myAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    private void printChildrenCount(DatabaseReference ref) {
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    long childrenCount = task.getResult().getChildrenCount();
                    Log.d("", "childrenCount: " + childrenCount);
                    if(childrenCount>=1){
                        recyclerView.setVisibility(View.VISIBLE);
//                        image.setVisibility(View.INVISIBLE);
//                        textt.setVisibility(View.INVISIBLE);
                        recycle();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_SHORT).show();
//                        image.setVisibility(View.VISIBLE);
//                        textt.setVisibility(View.VISIBLE);
                    }

                } else {
                    Log.d("", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
    }
    private void recycle(){

        refer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Candidate user = dataSnapshot.getValue(Candidate.class);
                    list.add(user);


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}