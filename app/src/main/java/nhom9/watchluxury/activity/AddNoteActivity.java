package nhom9.watchluxury.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import io.realm.Realm;
import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Note;
import nhom9.watchluxury.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {

    private ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        binding.savebtn.setOnClickListener(view -> {
            String title = binding.titleinput.getText().toString();
            String description = binding.descriptioninput.getText().toString();
            long createdTime = System.currentTimeMillis();

            realm.beginTransaction();
            Note note = realm.createObject(Note.class);
            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedTime(createdTime);
            realm.commitTransaction();
            Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
            finish();

        });


    }
}