package Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ltvscatalogue.HomeActivity;
import com.ltvscatalogue.Partdetails;
import com.ltvscatalogue.R;

import java.util.ArrayList;
import java.util.List;

import Model.FullUnitsearch.Fullunitsearchlist;

/**
 * Created by system9 on 10/5/2017.
 */

public class ServicePartadapter extends RecyclerView.Adapter<ServicePartadapter.ViewHolder> {
    List<Fullunitsearchlist> Fullunitsearchlist= new ArrayList<>();
    int count = 1;
    Context context;
    private ArrayList<String> sino = new ArrayList<>();
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ed_sino1, ed_descrip, ed_nooff, ed_partno, ed_view;

        public ViewHolder(View view) {
            super(view);
            ed_sino1 = (TextView) view.findViewById(R.id.sino1);
            ed_descrip = (TextView) view.findViewById(R.id.descrip);

            ed_nooff = (TextView) view.findViewById(R.id.nooff);
            ed_partno = (TextView) view.findViewById(R.id.partno);
            ed_view = (TextView) view.findViewById(R.id.view);

        }
    }

    public ServicePartadapter(Context context,  List<Fullunitsearchlist> Fullunitsearchlist,ArrayList<String> sino
    ) {
        this.context = context;
        this.sino = sino;
        this.Fullunitsearchlist = Fullunitsearchlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.servicepartadapter, viewGroup, false);
        context = view.getContext();

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        viewHolder.ed_sino1.setText(Fullunitsearchlist.get(i).getIllno());
        viewHolder.ed_descrip.setText(Fullunitsearchlist.get(i).getDescription());
        viewHolder.ed_nooff.setText(Fullunitsearchlist.get(i).getMoq());
        viewHolder.ed_partno.setText(Fullunitsearchlist.get(i).getChildparts());
        viewHolder.ed_partno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        Intent i = new Intent(context, Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_partno.getText().toString().trim());
                       context.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText( v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return Fullunitsearchlist.size();
    }



}

