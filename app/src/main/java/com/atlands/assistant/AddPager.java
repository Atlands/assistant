package com.atlands.assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.atlands.assistant.db.Contentlist;
import com.atlands.assistant.db.Onelevel;
import com.atlands.assistant.db.Twolevel;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AddPager extends AppCompatActivity {

    private Spinner spinner1, spinner2, spinner3;
    private TextView textView;
    private EditText title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pager);
        initView();

        List<String> item1 = new ArrayList<>();
        item1.add(getResources().getString(R.string.navigation1));
        item1.add(getResources().getString(R.string.navigation2));
        item1.add(getResources().getString(R.string.navigation3));

        List<String> item2=new ArrayList<>();
        List<Onelevel> onelevels=LitePal.select("name").where("cid = ?",1+"").find(Onelevel.class);
        for (Onelevel onelevel:onelevels){
            item2.add(onelevel.getName());
        }

        List<String> item3=new ArrayList<>();
        List<Twolevel>twolevels=LitePal.select("name").where("oid = ?","").find(Twolevel.class);
        ArrayAdapter<String> itemAdapter1 = new ArrayAdapter<String>(this, R.layout.item_addpager, item1);
        ArrayAdapter<String> itemAdapter2 = new ArrayAdapter<String>(this, R.layout.item_addpager, item2);
        spinner1.setAdapter(itemAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        textView = findViewById(R.id.miaosu);
    }
}
