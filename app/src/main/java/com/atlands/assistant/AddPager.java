package com.atlands.assistant;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.atlands.assistant.db.Contentlist;
import com.atlands.assistant.db.Onelevel;
import com.atlands.assistant.db.Twolevel;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddPager extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;
    private Spinner spinner1, spinner2, spinner3;
    private ImageView imageView1, imageView2, imageView3;
    private EditText title, url;
    private Button done;

    private String select;
    private int selectitem1, selectitem2, selectitem3;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pager);
        initView();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black);
        }

        textView.setText(getResources().getString(R.string.drawer2));

        select = getResources().getString(R.string.select);
        final List<String> item1 = new ArrayList<>();
        item1.add(select);
        item1.add(getResources().getString(R.string.navigation1));
        item1.add(getResources().getString(R.string.navigation2));
        item1.add(getResources().getString(R.string.navigation3));
        final List<String> item2 = new ArrayList<>();
        item2.add(select);
        ArrayAdapter<String> itemAdapter1 = new ArrayAdapter<>(this, R.layout.item_addpager, item1);
        spinner1.setAdapter(itemAdapter1);
        spinner1.setSelection(0);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectitem1 = 0;
                selectitem2 = 0;
                selectitem3 = 0;
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                if (!item1.get(position).equals(select)) {
                    selectitem1 = position;
                    final List<Onelevel> onelevels = LitePal.select("name").where("cid = ?", selectitem1 + "").find(Onelevel.class);
                    for (Onelevel onelevel : onelevels) {
                        item2.add(onelevel.getName());
                    }
                    ArrayAdapter<String> itemAdapter2 = new ArrayAdapter<String>(AddPager.this, R.layout.item_addpager, item2);
                    spinner2.setAdapter(itemAdapter2);
                    spinner2.setSelection(0);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectitem2 = 0;
                            selectitem3 = 0;
                            spinner3.setSelection(0);
                            if (!item2.get(position).equals(select)) {
                                final List<String> item3 = new ArrayList<>();
                                item3.add(select);
                                List<Onelevel> onelevels1 = LitePal.select("id").where("name = ?", item2.get(position)).find(Onelevel.class);
                                selectitem2 = onelevels1.get(0).getId();
                                List<Twolevel> twolevels = LitePal.select("name").where("oid = ?", selectitem2 + "").find(Twolevel.class);
                                for (Twolevel twolevel : twolevels) {
                                    item3.add(twolevel.getName());
                                }
                                ArrayAdapter<String> itemAdapter3 = new ArrayAdapter<String>(AddPager.this, R.layout.item_addpager, item3);
                                spinner3.setAdapter(itemAdapter3);
                                spinner3.setSelection(0);
                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        List<Twolevel> twolevels1 = LitePal.select("id").where("name = ?", item3.get(position)).find(Twolevel.class);
                                        if (twolevels1.size() > 0) {
                                            selectitem3 = twolevels1.get(0).getId();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner1.getId() != 0) {
                    Contentlist contentlist = new Contentlist();
                    contentlist.setName(title.getText().toString());
                    contentlist.setLink(url.getText().toString());
                    contentlist.setCid(selectitem1);
                    contentlist.setOid(selectitem2);
                    contentlist.setWid(selectitem3);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        textView=findViewById(R.id.bar_title);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        imageView1 = findViewById(R.id.iv_next1);
        imageView2 = findViewById(R.id.iv_next2);
        imageView3 = findViewById(R.id.iv_next3);
        title = findViewById(R.id.et_title);
        url = findViewById(R.id.et_url);
        done = findViewById(R.id.done);
    }
}
