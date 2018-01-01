package com.example.user.surpriseu;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by user on 2017/12/26.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c)
    {
        mContext=c;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ImageView imageview;
        if(convertView==null)
        {
            //獲取屏幕像素相關訊息
            DisplayMetrics dm = new DisplayMetrics();
            System.out.print("dm.widthPixels : " + dm.widthPixels);

            imageview=new ImageView(mContext);



            System.out.print("imageview : " + dm.widthPixels);


            imageview.setLayoutParams(new GridView.LayoutParams(125, 125));
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageview.setPadding(8,8,8,8);
        }
        else
        {
            imageview=(ImageView) convertView;
        }
        imageview.setImageResource(mThumbIds[position]);
        return imageview;
    }

    private Integer[] mThumbIds={//显示的图片数组

            R.drawable.sample_2,R.drawable.sample_3,
            R.drawable.sample_4,R.drawable.sample_5,
            R.drawable.sample_0,R.drawable.sample_1,
            R.drawable.sample_2,R.drawable.sample_3,
            R.drawable.sample_4,R.drawable.sample_5,
            R.drawable.sample_0,R.drawable.sample_1,
            R.drawable.sample_2,R.drawable.sample_3,
            R.drawable.sample_4,R.drawable.sample_5,
    };


}