package wolfsoft.toptalov;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import wolfsoft.toptalov.BeanClass;
import wolfsoft.toptalov.R;
import wolfsoft.toptalov.listViewAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    int number = 1;


    private int image;
    private String title;
    private String description;
    private String price;


    public int[] IMAGE = {R.drawable.a, R.drawable.b, R.drawable.shop};
    public String[] TITLE = {"Recommended Projects", "Search Projects", "My Projects"};
    public String[] DESCRIPTION = {"", "", ""};


    private ArrayList<BeanClass> beanClassArrayList;
    private listViewAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);
        beanClassArrayList = new ArrayList<BeanClass>();


        for (int i = 0; i < TITLE.length; i++) {

            BeanClass beanClass = new BeanClass(IMAGE[i], TITLE[i], DESCRIPTION[i]);
            beanClassArrayList.add(beanClass);

        }
        listViewAdapter = new listViewAdapter(MainActivity.this, beanClassArrayList);
        listview.setAdapter(listViewAdapter);
    }
}



