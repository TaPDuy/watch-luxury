package nhom9.watchluxury.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.google.android.material.appbar.AppBarLayout;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.ActivityOrdersBinding;
import nhom9.watchluxury.databinding.DialogOrderBinding;
import nhom9.watchluxury.databinding.DialogOrderInfoBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.util.DateUtils;
import nhom9.watchluxury.viewmodel.OrdersViewModel;
import nhom9.watchluxury.viewmodel.adapter.OrderAdapter;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class OrdersActivity extends AppCompatActivity {

    private ActivityOrdersBinding binding;
    private OrdersViewModel viewModel;

    private RendererRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        binding.topBar.setNavigationOnClickListener(view -> finish());
        binding.ablTopBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout layout, int vOff) {
                if (scrollRange == -1) {
                    scrollRange = layout.getTotalScrollRange();
                }
                if (scrollRange + vOff == 0) {
                    binding.cblTopBar.setTitle(binding.tvHeader.getText());
                    isShow = true;
                } else if (isShow) {
                    binding.cblTopBar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        adapter = new OrderAdapter();
        adapter.registerRenderer(new ViewRenderer<>(
                R.layout.item_order,
                Order.class,
                (model, finder, payloads) -> {
                    finder.setText(R.id.tv_itemName, model.getName().isEmpty() ? "" : model.getName());
                    finder.setText(R.id.tv_itemTotal, String.format("%,d", model.getTotal()) + "đ");
                    finder.setText(R.id.tv_itemDate, DateUtils.toSimpleString(model.getTimeCreated()));
                    finder.setOnClickListener(() -> showOrderInfo(model));
                }
        ));

        binding.rvOrderList.setAdapter(adapter);
        binding.rvOrderList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        initObserver();
        viewModel.loadOrders();
    }

    private void initObserver() {
        viewModel.getOrders().observe(this, adapter::setItems);
    }

    private void showOrderInfo(Order order) {

        DialogOrderInfoBinding dialogBinding = DialogOrderInfoBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.setViewModel(viewModel);
        dialogBinding.setOrder(order);

        Adapter a = new Adapter();
        dialogBinding.rvProductList.setAdapter(a);
        dialogBinding.rvProductList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        a.setItems(order.getProducts());

        Window window = dialog.getWindow();
        if (window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setWindowAnimations(R.style.dialog);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);
        dialog.show();
    }

    private class Adapter extends ProductAdapter {

        public Adapter() {
            super();
            registerRenderer(new ViewRenderer<>(
                    R.layout.item_product_extra_small,
                    Product.class,
                    (model, finder, payloads) -> {
                        finder.setText(R.id.tv_itemLabel, model.getName());
                        finder.setText(R.id.tv_itemPrice, String.format("%,d", model.getPrice()) + "đ");
                        APIUtils.loadImage(model.getImagePath(), finder.find(R.id.img_itemThumbnail));
                        finder.setOnClickListener(() -> {
                            Intent i = new Intent(OrdersActivity.this, ProductInfoActivity.class);
                            i.putExtra("productID", model.getId());
                            startActivity(i);
                        });
                    }
            ));
        }
    }
}