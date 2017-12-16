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

/**
 * Created by system9 on 10/5/2017.
 */

public class Productadapter extends BaseAdapter {

    private Context mContext;
   List<String> categories;
    ArrayList<String> imagelist;
    ArrayList<String> prodname;
    String Producname;
    private LayoutInflater layoutInflator;
    public Productadapter(Context context,List<String> categories) {
        //mContext = c;
        this.categories=categories;
        this.mContext=context;

    }

    public int getCount() {
        return categories.size();
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
            prodname= new ArrayList<String>();
            imagelist = new ArrayList<String>();
            imagelist.add("startermotor.jpg");
            imagelist.add("alternator.jpg");
            imagelist.add("wipermotor.jpg");

            imagelist.add("distributor.jpg");
            imagelist.add("camsensor.jpg");
            imagelist.add("ignitioncoil.jpg");

            imagelist.add("blowermotor.jpg");
            imagelist.add("bulb.jpg");
            imagelist.add("horn.jpg");

            imagelist.add("fanmotor.jpg");
            imagelist.add("headlamp.jpg");
            imagelist.add("filter.jpg");





            // if it's not recycled, initialize some attributes
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.marutiadpt, parent, false);
            TextView grid_text=(TextView)convertView.findViewById(R.id.grid_text);
            ImageView grid_image=(ImageView)convertView.findViewById(R.id.grid_image);
//            for ( int i = 0; i < categories.size(); i++ ){
//                Producname= categories.get(i).getProdName();
//                prodname.add(Producname);
//            }
//            Set<String> hs = new HashSet<>();
//            hs.addAll(prodname);
//            prodname.clear();
//            prodname.addAll(hs);
            grid_text.setText(categories.get(position));
            //grid_image.setLayoutParams(news GridView.LayoutParams(85, 85));
            grid_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            grid_image.setPadding(8, 8, 8, 8);
//            final String imageUri = "http://lucastvs-catalog.in/Admin/Uploads/proicon/"+imagelist.get(position);
            final String imageUri = "file:///android_asset/"+imagelist.get(position);


            Picasso.with(mContext).load(imageUri).into(grid_image);
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