package com.sdivc.novajia.photobrowser;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sdivc.novajia.photobrowser.Utils.ImageSource;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private GridImageAdapter mGridImageAdapter;
    private DisplayMetrics dm;
    private int imageCol = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.myGrid);
        mGridImageAdapter = new GridImageAdapter(this);
        gridView.setAdapter(mGridImageAdapter);
        gridView.setOnItemClickListener(this);
        checkOrientation();
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    private void checkOrientation() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageCol = 5;
        }else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            imageCol = 3;
        }
        gridView.setNumColumns(imageCol);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try{
            checkOrientation();
            gridView.setAdapter(new GridImageAdapter(this));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(GridViewActivity.this,GalleryActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }


    /***
     * Adapter
     */
    public class GridImageAdapter extends BaseAdapter {
        private Context mContext;
        private Drawable btnDrawable;

        public GridImageAdapter(Context mContext) {
            this.mContext = mContext;
            Resources mResources = mContext.getResources();
            this.btnDrawable = mResources.getDrawable(R.drawable.bg);
        }

        @Override
        public int getCount() {
            return ImageSource.mThUMBids.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int space;
            if (convertView == null){
                imageView = new ImageView(mContext);
                if (imageCol == 5){
                    space = dm.heightPixels / imageCol-6;
                    imageView.setLayoutParams(new GridView.LayoutParams(space,space));
                }else {
                    space = dm.widthPixels / imageCol-6;
                    imageView.setLayoutParams(new GridView.LayoutParams(space,space));
                }
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(3,3,3,3);
            }else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(ImageSource.mThUMBids[position]);
            return imageView;
        }
    }
}
