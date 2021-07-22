package com.example.v_school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_7 extends AppCompatActivity {
    private RecyclerView rvList;
    private List<Notification> noTiList = new ArrayList<>();
    private NotificationAdapter noTiAdapter;
    DatabaseReference myRef;
    FirebaseDatabase rootNode;
    EditText inputSearch;
    ImageButton btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thông báo");
        setContentView(R.layout.activity_7);
        rvList = findViewById(R.id.recyclerview);

        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("notification");


        for (int i = 1; i < 11; i++) {
            Notification notification = new Notification();
            notification.setId(i);
            notification.setTopic("Thong bao nghi hoc " + i);
            notification.setDay("10/10/2021");
            notification.setIdFrom("nghiadnp");
            notification.setIdTo("khanhln");
            if (i % 2 == 0) notification.setIsRead(1);
            notification.setMessage("qwdqdbqwdiqwudhqwdiqduiqwhd");
            noTiList.add(notification);
        }
        rvList.setHasFixedSize(true);

        inputSearch = (EditText) findViewById(R.id.edtSearch);
        btnSearch = (ImageButton) findViewById(R.id.imageButton);

        NotificationAdapter notificationAdapter = new NotificationAdapter(noTiList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_7.this);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(notificationAdapter);

        notificationAdapter.setMyOnClickItemListener(new MyOnClickItemListener() {
            @Override
            public void onClick(Notification notification) {
                Intent intent = new Intent(Activity_7.this, ChiTietThongBao.class);
                intent.putExtra("tenthongbao", notification.getTopic());
                intent.putExtra("ngay", notification.getDay());
                intent.putExtra("noidung", notification.getMessage());
                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCHR = inputSearch.getText().toString();
                if (inputSearch.getText().toString().length() > 0) {
                    ArrayList<Notification> listNew = new ArrayList<>();
                    for (int l = 0; l < noTiList.size(); l++) {
                        String topic = noTiList.get(l).getTopic().toLowerCase();
                        if (topic.contains(strCHR.toLowerCase())) {
                            listNew.add(noTiList.get(l));
                        }
                    }

                    NotificationAdapter notificationAdapter = new NotificationAdapter(listNew);
                    rvList.setAdapter(notificationAdapter);
                } else {

                    NotificationAdapter notificationAdapter = new NotificationAdapter(noTiList);
                    rvList.setAdapter(notificationAdapter);
                }
            }
        });

    }
}