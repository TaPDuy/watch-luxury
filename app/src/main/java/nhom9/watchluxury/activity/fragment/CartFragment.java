package nhom9.watchluxury.activity.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import io.reactivex.rxjava3.disposables.Disposable;
import nhom9.watchluxury.R;
import nhom9.watchluxury.activity.ProductInfoActivity;
import nhom9.watchluxury.data.local.CartManager;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.DialogOrderBinding;
import nhom9.watchluxury.databinding.FragmentCartBinding;
import nhom9.watchluxury.event.CartEvent;
import nhom9.watchluxury.event.CartEventBus;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.HomeViewModel;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class CartFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentCartBinding binding;

    private ProductAdapter adapter;
    private Disposable disposable;

    public CartFragment() {
    }

    public static CartFragment newInstance(int page, String title) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding = FragmentCartBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ProductAdapter();
        adapter.enableDiffUtil();
        adapter.registerRenderer(new ViewRenderer<>(
                R.layout.item_cart,
                Product.class,
                (model, finder, payloads) -> {

                    finder.setText(R.id.tv_itemLabel, model.getName());
                    finder.setText(R.id.tv_itemPrice, String.format("%,d", model.getPrice()) + "đ");
                    finder.setOnClickListener(R.id.btn_removeFromCart, () -> viewModel.removeFromCart(model.getId()));

                    APIUtils.loadImage(model.getImagePath(), finder.find(R.id.img_itemThumbnail));

                    finder.setOnClickListener(() -> {
                        Intent i3 = new Intent(getContext(), ProductInfoActivity.class);
                        i3.putExtra("productID", model.getId());
                        startActivity(i3);
                    });
                })
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        binding.rvCart.setAdapter(adapter);
        binding.rvCart.setLayoutManager(layoutManager);

//        cartAdapter.setData(getListCart());
//        ArrayList<CartModel> arrlist = getListCart();

//        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener(){
//            @Override
//            public void onItemClick(int position){
//                arrlist.remove(position);
//                cartAdapter.notifyItemRemoved(position);
//                cartAdapter.setData(arrlist);
//                rvCart.setAdapter(cartAdapter);
//                Toast.makeText(Cart.this, "You have removed a product!", Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.idBuy.setOnClickListener(v -> openDialog());

        initObservers();
    }

//    private void showPrice(){
//        int total = 0 ;
//        for(CartModel cartModel : getListCart()){
//            total += Integer.parseInt(cartModel.getPrice());
//            totalPrice.setText(""+total);
//
//
//        }
//
//    }
    private void initObservers() {
        disposable = CartEventBus.getInstance().getEvents().subscribe(viewModel::onCartEvent);
        viewModel.getCartItems().observe(getViewLifecycleOwner(), adapter::setItems);
    }

    private void openDialog() {

        DialogOrderBinding dialogBinding = DialogOrderBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        dialogBinding.setViewModel(viewModel);

        Window window = dialog.getWindow();
        if (window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setWindowAnimations(R.style.dialog);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        dialogBinding.idCancel.setOnClickListener(view -> dialog.dismiss());
        dialogBinding.idConfirm.setOnClickListener(view -> Toast.makeText(requireContext(), "Mua hang thanh cong", Toast.LENGTH_SHORT).show());

        dialog.show();
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}