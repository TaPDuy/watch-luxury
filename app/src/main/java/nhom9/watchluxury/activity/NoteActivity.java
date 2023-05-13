package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Note;
import nhom9.watchluxury.databinding.ActivityNoteBinding;
import nhom9.watchluxury.viewmodel.adapter.NoteAdapter;

public class NoteActivity extends AppCompatActivity {

    private ActivityNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        binding.addnewnotebtn.setOnClickListener(v -> startActivity(new Intent(this ,AddNoteActivity.class)));

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> notesList = realm.where(Note.class).findAll().sort("createdTime", Sort.DESCENDING);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter myAdapter = new NoteAdapter(getApplicationContext(),notesList);
        binding.recyclerview.setAdapter(myAdapter);

        notesList.addChangeListener(notes -> myAdapter.notifyDataSetChanged());
    }
}