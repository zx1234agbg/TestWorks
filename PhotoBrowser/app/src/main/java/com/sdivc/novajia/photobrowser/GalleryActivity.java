package com.sdivc.novajia.photobrowser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TabHost;

import com.sdivc.novajia.photobrowser.Utils.ImageSource;

/**
 * Created by Maya on 2018/4/23.
 */

public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int position = 0;
    Gallery gallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        initView();
    }

    private void initView() {
        gallery = (Gallery) findViewById(R.id.mygallery);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        ImageAdapter mImageAdapter = new ImageAdapter(this);
        gallery.setAdapter(mImageAdapter);
        gallery.setSelection(position);
        Animation am = AnimationUtils.loadAnimation(this,R.anim.scale);
        gallery.setAnimation(am);
        gallery.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("picUri", Integer.valueOf(position));
        intent.putExtras(bundle);
        intent.setClass(GalleryActivity.this,ImageDetailActivity.class);
        startActivity(intent);
    }

    private class ImageAdapter extends BaseAdapter{
        private Context mContext;
        private int mPos;

        public ImageAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setOwnposition(int pstn){
            this.mPos = pstn;
        }

        public int getOwnposition(){
            return mPos;
        }

        @Override
        public int getCount() {
            return ImageSource.mThUMBids.length;
        }

        @Override
        public Object getItem(int position) {
            mPos = position;
            return position;
        }

        @Override
        public long getItemId(int position) {
            mPos = position;
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            mPos = position;
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundColor(Color.parseColor("#ff00000000"));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(ImageSource.mThUMBids[position]);
            return imageView;
        }
    }
}
