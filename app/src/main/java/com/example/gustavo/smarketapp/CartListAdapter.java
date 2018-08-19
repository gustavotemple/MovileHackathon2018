package com.example.gustavo.smarketapp;

import android.animation.LayoutTransition;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CartListAdapter
        extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private static final String TAG = CartListAdapter.class.getSimpleName();
    private List<Product> dataSet;

    CartListAdapter() {
        this.dataSet = new ArrayList<>();
    }

    void init(ArrayList<Product> result) {

        for (Product product : result) {
            add(product);
        }

        sort();
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout productLayout;
        private final EditText editText;
        private final ImageView image;
        private final ImageView arrowPicture;
        private final ImageView remove;
        private final ImageView add;
        private final ImageView delete;
        private final TextView name;
        private final TextView brand;
        private final TextView price;
        private final TextView details;
        private final TextView showMore;
        private boolean showStates;

        ViewHolder(final View parentView, final RelativeLayout productLayout,
                   final EditText editText, final ImageView image,
                   final ImageView arrowPicture, final ImageView remove,
                   final ImageView add, final ImageView delete, final TextView name,
                   final TextView brand, final TextView price,
                   final TextView details, final TextView showMore) {
            super(parentView);
            this.productLayout = productLayout;
            this.editText = editText;
            this.image = image;
            this.arrowPicture = arrowPicture;
            this.remove = remove;
            this.add = add;
            this.delete = delete;
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.details = details;
            this.showMore = showMore;
            this.showStates = false;

            View.OnClickListener clickShow = v -> {
                ViewHolder.this.showStates = !ViewHolder.this.showStates;

                if (ViewHolder.this.showStates) {
                    ViewHolder.this.productLayout.setLayoutTransition(new LayoutTransition());
                    ViewHolder.this.details.setVisibility(View.VISIBLE);
                    ViewHolder.this.arrowPicture.animate().rotationBy(180f).start();
                    ViewHolder.this.showMore.setText(R.string.hide);
                } else {
                    ViewHolder.this.productLayout.setLayoutTransition(null);
                    ViewHolder.this.details.setVisibility(View.GONE);
                    ViewHolder.this.arrowPicture.animate().rotationBy(-180f).start();
                    ViewHolder.this.showMore.setText(R.string.show_more);
                }
            };

            this.arrowPicture.setOnClickListener(clickShow);
            this.showMore.setOnClickListener(clickShow);
            //this.commandSettings.setOnClickListener(clickCommandSettings);
            this.details.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_cart,
                parent,
                false);
        return new ViewHolder(v,
                v.findViewById(R.id.product_layout),
                v.findViewById(R.id.product_edit),
                v.findViewById(R.id.product_image),
                v.findViewById(R.id.arrow),
                v.findViewById(R.id.product_remove),
                v.findViewById(R.id.product_add),
                v.findViewById(R.id.product_delete),
                v.findViewById(R.id.product_name),
                v.findViewById(R.id.product_brand),
                v.findViewById(R.id.product_price),
                v.findViewById(R.id.product_details),
                v.findViewById(R.id.show_more));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,
                                 int position) {
        final Product product = dataSet.get(position);

        if (product.getName().contains("Morango")) {
            holder.image.setImageResource(R.drawable.strawberry);
        } else if (product.getName().contains("Ovo")) {
            holder.image.setImageResource(R.drawable.egg);
        } else if (product.getName().contains("Tomate")) {
            holder.image.setImageResource(R.drawable.tomato);
        } else if (product.getName().contains("Arroz")) {
            holder.image.setImageResource(R.drawable.rice);
        } else if (product.getName().contains("Batata")) {
            holder.image.setImageResource(R.drawable.potato);
        }

        holder.name.setText(product.getName());
        holder.brand.setText(product.getBrand());
        holder.price.setText(product.getPrice());
        holder.details.setText(product.getDetails());

        holder.remove.setOnClickListener(view -> {
            Log.i(TAG, "Selecting product: " + product.getName());

            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                holder.editText.setText(String.valueOf(product.getQuantity()));
            }

        });

        holder.add.setOnClickListener(view -> {
            Log.i(TAG, "Selecting product: " + product.getName());

            if (product.getQuantity() <= 99) {
                product.setQuantity(product.getQuantity() + 1);
                holder.editText.setText(String.valueOf(product.getQuantity()));
            }

        });

        holder.delete.setOnClickListener(view -> {
            Log.i(TAG, "Selecting product: " + product.getName());

            remove(product);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private void add(Product product) {
        dataSet.add(product);
        notifyItemInserted(dataSet.size() - 1);
    }

    private void remove(final Product product) {
        dataSet.remove(product);

        notifyItemRemoved(dataSet.size() - 1);
        sort();
    }

    private void sort() {
        Log.i(TAG, "sort");

        if (dataSet != null
                && dataSet.size() > 1) {
            Collections.sort(dataSet, getComparator());
        }

        notifyDataSetChanged();
    }

    private Comparator<Product> getComparator() {
        return (product1, product2) -> product1.toString().compareTo(product2.toString());
    }
}

