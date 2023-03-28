package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.databinding.ActivityHomePageBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    String[] nameWatch={"Rolex","Casio","Toyota","Guccy","Thụy Sĩ","Ekko","Quả lắc","Samsung"};

    private boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameWatch);
        binding.listMainView.setAdapter(arrayAdapter);

        binding.floatingBtn.setOnClickListener(view -> {
            if(check){
//                    send.show();
//                    lucky.show();
                binding.floatingHome.show();
                binding.floatingFavorite.show();
                binding.floatingCart.show();
                check=false;
            }else{
                binding.floatingHome.hide();
//                    send.hide();
//                    lucky.hide();
                binding.floatingFavorite.hide();
                binding.floatingCart.hide();
                check=true;
            }
        });

        binding.sidebar.setNavigationItemSelectedListener(item -> {
            boolean res = true;
            switch (item.getItemId()) {
                case R.id.logout:
                    TokenManager.deleteTokens();
                    finish();
                    break;
                case R.id.account:
                    Intent i1 = new Intent(this, UserInfoActivity.class);
                    startActivity(i1);
                    break;
                case R.id.about:
                    Intent i2 = new Intent(this, AboutActivity.class);
                    startActivity(i2);
                    break;
                case R.id.setting:
                    Intent i3 = new Intent(this, ProductInfoActivity.class);
                    i3.putExtra("productID", 3);
                    startActivity(i3);
                    break;
                default:
                    res = false;
                    break;
            }
            binding.sidebarLayout.closeDrawer(GravityCompat.START);
            return res;
        });

//        binding.btnLogout.setOnClickListener(view -> {
//            TokenManager.deleteTokens();
//            finish();
//        });
//
//        binding.btnUserInfo.setOnClickListener(view -> {
//            Intent i = new Intent(this, UserInfoActivity.class);
//            startActivity(i);
//        });
    }


}