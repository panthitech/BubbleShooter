package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.product.Product;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public final Context mContext;
    public final List<Product> mProductList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.mProductList = productList;
    }

    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_recycler_product, parent, false));
    }

    public void onBindViewHolder(ProductViewHolder viewHolder, int position) {
        final Product product = this.mProductList.get(position);
        viewHolder.mTxtProductDescription.setText(product.getDescription());
        viewHolder.mImageProduct.setImageResource(product.getDrawableId());
        viewHolder.mBtnProduct.setBackgroundResource(product.getButtonId());
        viewHolder.mBtnProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductAdapter.this.showDialog(product);
            }
        });
        UIEffect.createPopUpEffect(viewHolder.itemView, (long) position);
        UIEffect.createPopUpEffect(viewHolder.mBtnProduct, (long) (position + 2));
        UIEffect.createButtonEffect(viewHolder.mBtnProduct);
    }

    public void showDialog(Product product) {
    }

    public int getItemCount() {
        return this.mProductList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton mBtnProduct;
        public final ImageView mImageProduct;
        public final TextView mTxtProductDescription;

        public ProductViewHolder(View view) {
            super(view);
            this.mTxtProductDescription = (TextView) view.findViewById(R.id.txt_product);
            this.mImageProduct = (ImageView) view.findViewById(R.id.image_product);
            this.mBtnProduct = (ImageButton) view.findViewById(R.id.btn_product);
        }
    }
}
