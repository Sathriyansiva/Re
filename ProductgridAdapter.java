package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltvscatalogue.HomeActivity;
import com.ltvscatalogue.Partdetails;
import com.ltvscatalogue.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Productsearch.Productfilterlist;

/**
 * Created by system9 on 10/6/2017.
 */

public class ProductgridAdapter extends RecyclerView.Adapter<ProductgridAdapter.ViewHolder> {
    List<Productfilterlist> Producfilter = new ArrayList<>();
    Context context;


    private ArrayList<String> sino = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView edpartno, ed_type, ed_voltage, ed_rating, tv_view, tv_sino;

        public ViewHolder(View view) {
            super(view);
            edpartno = (TextView) view.findViewById(R.id.partno);

            ed_type = (TextView) view.findViewById(R.id.type);
            ed_voltage = (TextView) view.findViewById(R.id.voltage);
            ed_rating = (TextView) view.findViewById(R.id.ratings);
            tv_view = (TextView) view.findViewById(R.id.view);
            tv_sino = (TextView) view.findViewById(R.id.sino1);

        }
    }

    public ProductgridAdapter(Context context, List<Productfilterlist> Producfilter,ArrayList<String> sino
    ) {
        this.context = context;
        this.sino = sino;
        this.Producfilter = Producfilter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productgridadapter, viewGroup, false);
        context = view.getContext();

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.tv_sino.setText(sino.get(i));
        viewHolder.edpartno.setText(Producfilter.get(i).getPartNo());
        viewHolder.ed_type.setText(Producfilter.get(i).getProType());
        viewHolder.ed_voltage.setText(Producfilter.get(i).getPartVolt());
        viewHolder.ed_rating.setText(Producfilter.get(i).getPartOutputrng());
        viewHolder.edpartno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(context, Partdetails.class);
                    i.putExtra("Partnumber", viewHolder.edpartno.getText().toString().trim());
                   context.startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return Producfilter.size();
    }


}