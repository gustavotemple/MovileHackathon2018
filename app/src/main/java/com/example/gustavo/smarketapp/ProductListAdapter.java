package com.example.gustavo.smarketapp;

import android.animation.LayoutTransition;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductListAdapter
        extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>
        implements Filterable {

    private static final String TAG = ProductListAdapter.class.getSimpleName();
    private ArrayList<Product> dataSetOriginal;
    private ArrayList<Product> dataSetFilter;
    private ArrayList<Product> cart;
    private ShoppingCart shoppingCart;

    ProductListAdapter(final ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        this.dataSetOriginal = new ArrayList<>();
        this.dataSetFilter = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    void init() {
        add(new Product("Morango", "Oba Morango", "1.99", "A média de preço é 2.99", 1));
        add(new Product("Batata", "Batata Feliz", "5.99", "A média de preço é 6.99", 1));
        add(new Product("Arroz", "Happy Rice", "2.99", "A média de preço é 3.99", 1));
        add(new Product("Ovo", "Uhul Egg", "8.99", "A média de preço é 9.99", 1));
        add(new Product("Tomate", "Hey Apple", "12.99", "A média de preço é 13.99", 1));

        sort();
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout productLayout;
        private final RelativeLayout showLayout;
        private final ImageView image;
        private final ImageView arrowPicture;
        private final ImageView cart;
        private final TextView name;
        private final TextView brand;
        private final TextView price;
        private final TextView details;
        private final TextView showMore;
        private boolean showStates;

        ViewHolder(final View parentView, final RelativeLayout productLayout,
                   final RelativeLayout showLayout, final ImageView image,
                   final ImageView arrowPicture, final ImageView cart, final TextView name,
                   final TextView brand, final TextView price,
                   final TextView details, final TextView showMore) {
            super(parentView);
            this.productLayout = productLayout;
            this.showLayout = showLayout;
            this.image = image;
            this.arrowPicture = arrowPicture;
            this.cart = cart;
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
            this.showLayout.setOnClickListener(clickShow);
            //this.commandSettings.setOnClickListener(clickCommandSettings);
            this.details.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_product,
                parent,
                false);
        return new ViewHolder(v,
                v.findViewById(R.id.product_layout),
                v.findViewById(R.id.show_layout),
                v.findViewById(R.id.product_image),
                v.findViewById(R.id.arrow),
                v.findViewById(R.id.product_cart),
                v.findViewById(R.id.product_name),
                v.findViewById(R.id.product_brand),
                v.findViewById(R.id.product_price),
                v.findViewById(R.id.product_details),
                v.findViewById(R.id.show_more));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = dataSetFilter.get(position);

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

        holder.cart.setOnClickListener(view -> {
            Log.i(TAG, "Selecting product: " + product.getName());
            shoppingCart.count();
            remove(product);
            cart.add(product);
        });

    }

    @Override
    public int getItemCount() {
        return dataSetFilter.size();
    }

    private void add(Product product) {
        dataSetOriginal.add(product);
        dataSetFilter.add(product);
        notifyItemInserted(dataSetFilter.size() - 1);
    }

    private void remove(final Product product) {
        dataSetOriginal.remove(product);
        dataSetFilter.remove(product);

        notifyItemRemoved(dataSetFilter.size() - 1);
        sort();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                final FilterResults results = new FilterResults();
                final List<Product> dataSetFilter = new ArrayList<>();

                if (constraint.length() == 0) {
                    dataSetFilter.addAll(dataSetOriginal);
                } else {
                    for (final Product product : dataSetOriginal) {
                        if (StringUtils.containsIgnoreCase(product.getName(),
                                constraint.toString())) {
                            dataSetFilter.add(product);
                        }
                    }
                }

                results.values = dataSetFilter;
                results.count = dataSetFilter.size();
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(final CharSequence constraint,
                                          final FilterResults results) {
                if (results.values instanceof List) {
                    dataSetFilter = ((ArrayList<Product>) results.values);
                    sort();
                }
            }
        };
    }

    private void sort() {
        Log.i(TAG, "sort");

        if (dataSetFilter != null
                && dataSetFilter.size() > 1) {
            Collections.sort(dataSetFilter, getComparator());
        }

        notifyDataSetChanged();
    }

    private Comparator<Product> getComparator() {
        return (product1, product2) -> product1.toString().compareTo(product2.toString());
    }
}

