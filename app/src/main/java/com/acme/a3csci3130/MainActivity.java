package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Main activity class used to view the listview of Business'
 * that exist in Database.
 */
public class MainActivity extends Activity {


    private ListView businessListView;
    private FirebaseListAdapter<Business> firebaseAdapter;

    /**
     * Creates Listview and initializes onClick event firingthe detail view.
     * Also initializes the reference to Firebase, and Create Business button.
     * @param savedInstanceState Current Instance of app that calls super onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("business");

        //Get the reference to the UI contents
        businessListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
        firebaseAdapter = new FirebaseListAdapter<Business>(this, Business.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Business model, int position) {
                TextView businessName = (TextView)v.findViewById(android.R.id.text1);
                businessName.setText(model.name);
            }
        };
        businessListView.setAdapter(firebaseAdapter);
        businessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is call everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Business person = (Business) firebaseAdapter.getItem(position);
                showDetailView(person);
            }
        });
    }

    /**
     * Action taken when user clicked Create Business button, opens the
     * Create Business Activity.
     * @param v Current view of activity.
     */
    public void createBusinessButton(View v)
    {
        Intent intent = new Intent(this, CreateBusinessAcitivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param person Currently clicked business that user wishes to view in
     *               Detail Activity.
     */
    private void showDetailView(Business person)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Business", person);
        startActivity(intent);
    }



}
