package nhom9.watchluxury.viewmodel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.List;
import java.util.Random;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Music;
import nhom9.watchluxury.databinding.ItemMusicBinding;

public class MusicAdapter extends BaseAdapter {

    private Context context;
    private List<Music> arrayList;

    private MediaPlayer mediaPlayer;
    private Boolean flag = true;

    public MusicAdapter(Context context, List<Music> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public List<Music> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<Music> arrayList) {
        this.arrayList = arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        private final ItemMusicBinding binding;

        public ViewHolder(ItemMusicBinding binding) {
            this.binding = binding;
        }

        public void bind(Music music) {

            binding.txtName.setText(music.getName());

            binding.ivPlay.setOnClickListener(view -> {
                if (flag) {
                    mediaPlayer = MediaPlayer.create(context, music.getSong());
                    flag = false;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    binding.ivPlay.setImageResource(R.drawable.base_play);
                } else {
                    mediaPlayer.start();
                    binding.ivPlay.setImageResource(R.drawable.base_stop);
                }


            });

            binding.ivStop.setOnClickListener(view -> {
                if (!flag) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    flag = true;
                }
                binding.ivPlay.setImageResource(R.drawable.base_play);

            });
        }
    }

    int[] colors = {Color.rgb(124, 88, 104), Color.rgb(49, 98, 104), Color.rgb(64, 181, 134),
            Color.rgb(208, 124, 134), Color.rgb(53, 177, 134), Color.rgb(205, 208, 26),
            Color.rgb(164, 213, 163) , Color.rgb(101, 165, 110) , Color.rgb(194, 86, 110),
            Color.rgb(33, 147, 237) , Color.rgb(222, 147, 237)
    };

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemMusicBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_music, viewGroup, false);
            viewHolder = new ViewHolder(binding);
            convertView = binding.getRoot();
            convertView.setTag(viewHolder);
            int color = colors[new Random().nextInt(colors.length)];
            convertView.setBackgroundColor(color);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bind(arrayList.get(position));

        return convertView;
    }
}
