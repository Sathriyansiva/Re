package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltvscatalogue.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.OEcustomers.OEcustomerlist;

/**
 * Created by system9 on 9/23/2017.
 */

public class Applicationadpt extends BaseAdapter {

    private Context mContext;
    List<OEcustomerlist> oecustlist;

    private LayoutInflater layoutInflator;
    public Applicationadpt(Context context,List<OEcustomerlist> oecustlist) {
        //mContext = c;
        this.oecustlist=oecustlist;
        this.mContext=context;

    }

    public int getCount() {
        return oecustlist.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a news ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        convertView = null;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicationadpt, parent, false);
            TextView grid_text=(TextView)convertView.findViewById(R.id.grid_text);
            ImageView grid_image=(ImageView)convertView.findViewById(R.id.grid_image);
            grid_text.setText(oecustlist.get(position).getOemCustomer());
            //grid_image.setLayoutParams(news GridView.LayoutParams(85, 85));
            grid_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            grid_image.setPadding(8, 8, 8, 8);
            final String imageUri = "http://lucastvs-catalog.in/Admin/Uploads/Oems/"+oecustlist.get(position).getOemImage();
            Picasso.with(mContext).load(imageUri).into(grid_image);

//            Picasso.with(mContext).load("file:///android_asset/"+imagelist.get(position)).resize(75,75).into(grid_image);
        } /*else {
                imageView = (ImageView) convertView;
            }*/


        return convertView;
    }

    // references to our images
        /*private Integer[] mThumbIds = {
                R.drawable.sample_2
        };*/
}