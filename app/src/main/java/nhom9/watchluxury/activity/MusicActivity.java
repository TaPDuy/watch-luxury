package nhom9.watchluxury.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Music;
import nhom9.watchluxury.databinding.ActivityMusicBinding;
import nhom9.watchluxury.viewmodel.adapter.MusicAdapter;

public class MusicActivity extends AppCompatActivity {

    private List<Music> arrayList;
    private MusicAdapter adapter;
    private ActivityMusicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_music);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

//        getSupportActionBar().setTitle("Music Entertainment");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<>();
        arrayList.add(new Music("Một con vịt", R.raw.motconvit));
        arrayList.add(new Music("Morning mood", R.raw.morningmood));
        arrayList.add(new Music("Nơi đảo xa ", R.raw.morningmood));
        arrayList.add(new Music("Quốc ca", R.raw.morningmood));
        arrayList.add(new Music("Em của ngày hôm qua", R.raw.motconvit));
        arrayList.add(new Music("Chào buổi sáng", R.raw.motconvit));
        arrayList.add(new Music("Ngày ấy", R.raw.motconvit));
        arrayList.add(new Music("Hay lam", R.raw.motconvit));
        arrayList.add(new Music("Hay lam", R.raw.motconvit));
        arrayList.add(new Music("Hay lam", R.raw.motconvit));
        arrayList.add(new Music("Hay lam", R.raw.motconvit));
        arrayList.add(new Music("Hay lam", R.raw.motconvit));
        arrayList.add(new Music("Hay lam", R.raw.motconvit));

        adapter = new MusicAdapter(this, arrayList);
        binding.ListOfSongs.setAdapter(adapter);
    }
}