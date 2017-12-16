package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltvscatalogue.HomeActivity;
import com.ltvscatalogue.Partdetails;
import com.ltvscatalogue.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import Model.AppPartlist.AppPArtDetails;

/**
 * Created by system9 on 9/25/2017.
 */

public class Partadpt extends RecyclerView.Adapter<Partadpt.ViewHolder> {
    List<AppPArtDetails> Partdetails = new ArrayList<>();

    int count = 1;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ed_engine, ed_starter, ed_wipersys, ed_wipermo, ed_distib, ed_alter, ed_wipping, ed_rearwiper, ed_ignitioncoil, ed_blowermotor, ed_camscan;

        public ViewHolder(View view) {
            super(view);
            ed_engine = (TextView) view.findViewById(R.id.edt_engn);
            ed_starter = (TextView) view.findViewById(R.id.stater);

            ed_wipersys = (TextView) view.findViewById(R.id.wiper_system);
            ed_wipermo = (TextView) view.findViewById(R.id.wipermotor);
            ed_distib = (TextView) view.findViewById(R.id.distributor);

            ed_alter = (TextView) view.findViewById(R.id.alter);
            ed_wipping = (TextView) view.findViewById(R.id.wipping);
            ed_rearwiper = (TextView) view.findViewById(R.id.rearwiper);
            ed_ignitioncoil = (TextView) view.findViewById(R.id.ignitioncoil);
            ed_blowermotor = (TextView) view.findViewById(R.id.blowermotor);
            ed_camscan = (TextView) view.findViewById(R.id.camscanner);

        }
    }

    public Partadpt(Context context, List<AppPArtDetails> Partdetails
    ) {
        this.context = context;
        this.Partdetails = Partdetails;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.partadpthori, viewGroup, false);
        context = view.getContext();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        viewHolder.ed_engine.setText(Partdetails.get(i).getEngine());
        viewHolder.ed_starter.setText(Partdetails.get(i).getStarterMotor());
        viewHolder.ed_wipersys.setText(Partdetails.get(i).getWipermotorSystem());
        viewHolder.ed_wipermo.setText(Partdetails.get(i).getWipermotor());
        viewHolder.ed_distib.setText(Partdetails.get(i).getDistributor());

        viewHolder.ed_alter.setText(Partdetails.get(i).getAlternator());
        viewHolder.ed_wipping.setText(Partdetails.get(i).getWipingsystem());
        viewHolder.ed_rearwiper.setText(Partdetails.get(i).getRearwiper());
        viewHolder.ed_ignitioncoil.setText(Partdetails.get(i).getIgnitionCoil());
        viewHolder.ed_blowermotor.setText(Partdetails.get(i).getBlowerMotor());
        viewHolder.ed_camscan.setText(Partdetails.get(i).getCamSensor());


        if (!viewHolder.ed_starter.getText().toString().equals("")) {

            viewHolder.ed_starter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_starter.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_wipersys.getText().toString().equals("")) {

            viewHolder.ed_wipersys.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_wipersys.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_wipermo.getText().toString().equals("")) {
            viewHolder.ed_wipermo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_wipermo.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_distib.getText().toString().equals("")
                ) {
            viewHolder.ed_distib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_distib.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_alter.getText().toString().equals("")
                ) {
            viewHolder.ed_alter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_alter.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_wipping.getText().toString().equals("")) {
            viewHolder.ed_wipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_wipping.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_rearwiper.getText().toString().equals("")) {
            viewHolder.ed_rearwiper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_rearwiper.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_ignitioncoil.getText().toString().equals("")) {
            viewHolder.ed_ignitioncoil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_ignitioncoil.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_wipping.getText().toString().equals("") || !viewHolder.ed_ignitioncoil.getText().toString().equals("") || !viewHolder.ed_blowermotor.getText().toString().equals("")) {
            viewHolder.ed_blowermotor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_blowermotor.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!viewHolder.ed_camscan.getText().toString().equals("")) {
            viewHolder.ed_camscan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(context, com.ltvscatalogue.Partdetails.class);
                        i.putExtra("Partnumber", viewHolder.ed_camscan.getText().toString().trim());
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
//        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return Partdetails.size();
    }


}

