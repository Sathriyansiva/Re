package Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ltvscatalogue.R;

import java.util.ArrayList;
import java.util.List;

import Model.Dealetstatefilter.Dealerstatedetails;

/**
 * Created by system9 on 10/6/2017.
 */

public class Dealeradapter extends RecyclerView.Adapter<Dealeradapter.ViewHolder> {
    List<Dealerstatedetails> statefilter = new ArrayList<>();

    int count = 1;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ed_company, ed_name, ed_delercode, ed_phone, ed_email, ed_address;

        public ViewHolder(View view) {
            super(view);
            ed_company = (TextView) view.findViewById(R.id.shopname);
            ed_name = (TextView) view.findViewById(R.id.edt_name);

            ed_delercode = (TextView) view.findViewById(R.id.del_code);
            ed_phone = (TextView) view.findViewById(R.id.phone);
            ed_email = (TextView) view.findViewById(R.id.email);
            ed_address = (TextView) view.findViewById(R.id.del_address);
        }
    }

    public Dealeradapter(Context context, List<Dealerstatedetails> statefilter
    ) {
        this.context = context;
        this.statefilter = statefilter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dealeradapter, viewGroup, false);
        context = view.getContext();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        viewHolder.ed_company.setText(statefilter.get(i).getDealername());
        viewHolder.ed_name.setText(statefilter.get(i).getContactperson());
        viewHolder.ed_delercode.setText(statefilter.get(i).getDealerid());
        viewHolder.ed_phone.setText(statefilter.get(i).getMobileno());
        viewHolder.ed_email.setText(statefilter.get(i).getEmailId());
        viewHolder.ed_address.setText(statefilter.get(i).getAddress() + statefilter.get(i).getAddress1());

//        viewHolder.ed_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:"+ viewHolder.ed_phone.getText().toString().trim()));
//                context.startActivity(callIntent);
//            }
//        });
        viewHolder.ed_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return statefilter.size();
    }


}


