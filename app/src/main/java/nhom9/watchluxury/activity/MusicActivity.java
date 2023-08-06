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
        arrayList.add(new Music("Baroque No.0", R.raw.baroque0));
        arrayList.add(new Music("Baroque No.1", R.raw.baroque1));
        arrayList.add(new Music("Baroque No.2", R.raw.baroque2));
        arrayList.add(new Music("Baroque No.3", R.raw.baroque3));
        arrayList.add(new Music("Baroque No.4", R.raw.baroque4));
        arrayList.add(new Music("Baroque No.5", R.raw.baroque5));
        arrayList.add(new Music("Một con vịt", R.raw.motconvit));
        arrayList.add(new Music("Morning mood", R.raw.morningmood));
        arrayList.add(new Music("Walk", R.raw.walk));
        arrayList.add(new Music("Ema Skye", R.raw.emaskye));
        arrayList.add(new Music("Midsummer", R.raw.midsummer));

        adapter = new MusicAdapter(this, arrayList);
        binding.ListOfSongs.setAdapter(adapter);
    }
}