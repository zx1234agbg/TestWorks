package com.sdivc.novajia.photobrowser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.sdivc.novajia.photobrowser.Listener.MultiPointTounchListener;
import com.sdivc.novajia.photobrowser.Utils.ImageSource;

/**
 * Created by Maya on 2018/4/23.
 */

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageinfo);
        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView_showImg);
        Bundle bundle = getIntent().getExtras();
        int uriStr = bundle.getInt("picUri");
        map = BitmapFactory.decodeResource(getResources(), ImageSource.mThUMBids[uriStr]);
        if (map != null){
            imageView.setImageBitmap(map);
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            imageView.setOnTouchListener(new MultiPointTounchListener());
        }
    }
}
