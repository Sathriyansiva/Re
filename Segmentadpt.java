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

import Model.Segments.Segmentlist;

/**
 * Created by system9 on 9/25/2017.
 */

public class Segmentadpt extends BaseAdapter {

    private Context mContext;
    //List<Segmentlist> segmentlist;
    List<String> segmentlist;
    ArrayList<String> imagelist;
    Integer[] imageId = {R.drawable.twowheeler, R.drawable.threewheeler, R.drawable.passangercar, R.drawable.utilityvehicle,
            R.drawable.lightcommercial, R.drawable.heavycommercial, R.drawable.tractors,
            R.drawable.gensets, R.drawable.engines, R.drawable.marine, R.drawable.defense, R.drawable.locomotive};
    private LayoutInflater layoutInflator;

    public Segmentadpt(Context context, List<String> segmentlist) {
        //mContext = c;
        this.segmentlist = segmentlist;

        this.mContext = context;

    }

    public int getCount() {
        return segmentlist.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.segmentadpt, parent, false);
            TextView grid_text = (TextView) convertView.findViewById(R.id.grid_text);
            ImageView grid_image = (ImageView) convertView.findViewById(R.id.grid_image);
            grid_text.setText(segmentlist.get(position));
            //grid_image.setLayoutParams(news GridView.LayoutParams(85, 85));
            grid_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            grid_image.setPadding(8, 8, 8, 8);
            grid_image.setImageResource(imageId[position]);
//            final String imageUri = "http://lucastvs-catalog.in/Admin/Uploads/App_search/"+imagelist.get(position);
//            Picasso.with(mContext).load(imageUri).into(grid_image);
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