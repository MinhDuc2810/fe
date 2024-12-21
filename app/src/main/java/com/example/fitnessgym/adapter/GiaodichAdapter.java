package com.example.fitnessgym.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessgym.AdminActivity.ClickGiaodichListener;
import com.example.fitnessgym.Giaodich;
import com.example.fitnessgym.R;
import java.util.ArrayList;
import java.util.List;

public class GiaodichAdapter extends RecyclerView.Adapter<GiaodichAdapter.UserViewHolder> implements Filterable {

    private List<Giaodich> giaodichList;
    private  List<Giaodich> giaodichListOld;
    private  com.example.fitnessgym.AdminActivity.ClickGiaodichListener clickGiaodichListener;



    public GiaodichAdapter(List<Giaodich> giaodichList, ClickGiaodichListener clickGiaodichListener) {
        this.giaodichList = giaodichList;
        this.giaodichListOld = giaodichList;
        this.clickGiaodichListener = clickGiaodichListener;
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giaodich, parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Giaodich giaodich = giaodichList.get(position);

        if(giaodich == null){
            return;
        }

        holder.username.setText("Khách hàng : " + giaodich.getUserName());
        holder.userid.setText("Mã khách hàng : " + String.valueOf(giaodich.getUser_id()));
        holder.transactionid.setText("Mã giao dịch : " + String.valueOf(giaodich.getId()));
        holder.packageid.setText("Loại gói : " + giaodich.getType());



        holder.itemView.setOnClickListener(v -> clickGiaodichListener.onClickGiaodich( giaodich));


    }



    @Override
    public int getItemCount() {
        if(giaodichList != null){
            return giaodichList.size();
        }
        return 0;
    }




    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final TextView username ,userid, transactionid, packageid;

        public UserViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            userid = itemView.findViewById(R.id.userid);
            transactionid = itemView.findViewById(R.id.transactionid);
            packageid = itemView.findViewById(R.id.packageid);



        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    giaodichList = giaodichListOld;
                }else {
                    List<Giaodich> List = new ArrayList<>();
                    for (Giaodich giaodich : giaodichListOld){
                        if(String.valueOf(giaodich.getUserName()).contains(strSearch)){
                            List.add(giaodich);
                        }
                    }
                    giaodichList = List;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = giaodichList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                giaodichList = (List<Giaodich>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
