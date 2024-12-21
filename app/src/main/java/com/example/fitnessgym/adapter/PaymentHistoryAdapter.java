package com.example.fitnessgym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessgym.PaymentHistory;
import com.example.fitnessgym.R;
import java.util.ArrayList;
import java.util.List;

    public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.UserViewHolder> implements Filterable {

        private List<PaymentHistory> paymentHistoryList;
        private  List<PaymentHistory> paymentHistoriesOld;



        public PaymentHistoryAdapter(List<PaymentHistory> paymentHistoryList) {
            this.paymentHistoryList = paymentHistoryList;
            this.paymentHistoriesOld = paymentHistoryList;
        }


        @NonNull
        @Override
        public PaymentHistoryAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paymenthistory, parent,false);

            return new PaymentHistoryAdapter.UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PaymentHistoryAdapter.UserViewHolder holder, int position) {
            PaymentHistory paymentHistory = paymentHistoryList.get(position);

            holder.transid.setText("Mã thanh toán : " +String.valueOf(paymentHistory.getId()));
            holder.method.setText("Phương thức thanh toán : " +paymentHistory.getPayment_method());
            holder.total.setText("Số tiền : " +paymentHistory.getPrice());
            holder.time.setText("Ngày thanh toán : " +paymentHistory.getTransaction_date());

            if(paymentHistory == null){
                return;
            }

        }



        @Override
        public int getItemCount() {
            if(paymentHistoryList != null){
                return paymentHistoryList.size();
            }
            return 0;
        }




        public static class UserViewHolder extends RecyclerView.ViewHolder{
            private final TextView transid ,method, total, time;;

            public UserViewHolder(View itemView) {
                super(itemView);
                transid = itemView.findViewById(R.id.transid);
                method = itemView.findViewById(R.id.method);
                total = itemView.findViewById(R.id.total);
                time = itemView.findViewById(R.id.time);


            }
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String strSearch = charSequence.toString();
                    if(strSearch.isEmpty()){
                        paymentHistoryList = paymentHistoriesOld;
                    }else {
                        List<PaymentHistory> List = new ArrayList<>();
                        for (PaymentHistory paymentHistory : paymentHistoriesOld){
                            if(String.valueOf(paymentHistory.getId()).contains(strSearch)){
                                List.add(paymentHistory);
                            }
                        }
                        paymentHistoryList = List;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = paymentHistoryList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    paymentHistoryList = (List<PaymentHistory>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

    }

