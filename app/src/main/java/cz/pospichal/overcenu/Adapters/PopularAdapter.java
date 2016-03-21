package cz.pospichal.overcenu.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cz.pospichal.overcenu.Data.PopularData;
import cz.pospichal.overcenu.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<PopularData> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView product;
        TextView adress;
        FancyButton btn_cart;

        public DataObjectHolder(View itemView) {
            super(itemView);
            product = (TextView) itemView.findViewById(R.id.product);
            adress = (TextView) itemView.findViewById(R.id.firm);
            btn_cart = (FancyButton) itemView.findViewById(R.id.btn_addToCart);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public PopularAdapter(ArrayList<PopularData> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_popular, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.product.setText(mDataset.get(position).getmFirm());
        holder.adress.setText(mDataset.get(position).getmAdress());
        holder.btn_cart.setText((String.valueOf(mDataset.get(position).getmPrice())) + " Kč");
    }

    public void addItem(PopularData dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}