package com.example.mycoursedesign;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.Cache.FileCache;
import com.example.utils.Loader.AsyncImageLoader;
import com.example.utils.MyMusicListUtil;
import com.example.utils.Song_messageUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

import static com.example.utils.DataInfoCache.loadListCache;
import static com.example.utils.DataInfoCache.saveListCache;

public class MyMusicActivity extends AppCompatActivity {
    ArrayList<Song_messageUtil> arrayList_message;
    MyMusicListAdapter myMusicListAdapter;
    Bitmap default_picture;
    int current_play = -1;
    int old_position = -1;
    AsyncImageLoader imageLoader;
    MediaPlayer mediaPlayer;

    Animation animation;
    FloatingActionButton fab;
//    FloatingActionButton ffff = new FloatingActionButton(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        Intent intent = getIntent();
        String message = intent.getStringExtra("id_textView_title");
        final TextView textView_title = findViewById(R.id.id_textView_title);
        textView_title.setText(message);

        imageLoader = AsyncImageLoader.getInstance(this);
        mediaPlayer = new MediaPlayer();
        default_picture = BitmapFactory.decodeResource(getResources(), R.drawable.default_music_playing);
//        fab按钮初始化
        animation = AnimationUtils.loadAnimation(this, R.anim.my_anim);
        animation.setInterpolator(new LinearInterpolator());
        fab = findViewById(R.id.id_fab_playing);

        arrayList_message = loadListCache(this,"我的缓存"); /* 获取 */
        if (arrayList_message == null || arrayList_message.size() == 0)
        {
            //        加载本地音乐数据
            loadLocalMusicData();
            saveListCache(this, arrayList_message,"我的缓存");   /*  存储 */
        }
        myMusicListAdapter = new MyMusicListAdapter();
        ListView listView = findViewById(R.id.id_listView_song);
        listView.setAdapter(myMusicListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                current_play = position;
                Song_messageUtil current = arrayList_message.get(position);
                stopMusic();
//                重置播放器
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(current.getPath());
                    playMusic();
                    myMusicListAdapter.notifyDataSetChanged();
                    old_position = current_play;
                    if (current.getStatus() == 1)
                    {
                        imageLoader.displayBitmap(fab, current.getId());
                    }
                    else {
                        fab.setImageBitmap(default_picture);
                    }
                    fab.startAnimation(animation);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: 实现长按弹出对话框 显示歌曲全部信息
                return true;
            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (flag)
//                {
//                    fab.startAnimation(animation);
//                    flag = false;
//                }
//                else
//                {
//                    fab.clearAnimation();
//                    flag = true;
//                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    private void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying())
        {
            try {
                mediaPlayer.prepare();
                mediaPlayer.start();

                //更新数据源 并通知ListView刷新
                arrayList_message.get(current_play).setPlaying(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopMusic() {

        if (mediaPlayer != null)
        {
            mediaPlayer.pause();
            mediaPlayer.stop();

            fab.clearAnimation();
            //更新数据源 并通知ListView刷新
            if (old_position != -1)
            {
                arrayList_message.get(old_position).setPlaying(false);
            }
        }
    }

    private void loadLocalMusicData() {
//        准备用于保存音乐封面到本地的存储器
        FileCache fileCache = new FileCache(this);
        fileCache.clear();
//        1 获取ContentResolver对象
        ContentResolver resolver = getContentResolver();
//        2 获取本地音乐存储的URI地址
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        3 开始查询地址
        Cursor cursor = null;
//        4 遍历Cursor

        long id = 0;
        try {
            cursor = resolver.query(uri, null, null, null, null);
            while (cursor.moveToNext())
            {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                String time = sdf.format(new Date(duration));
                int status = -1;
                Bitmap bitmap_song = setArtwork(path);
                if (bitmap_song != null)
                {
//                    保存至本地File中
                    fileCache.put(id, bitmap_song);
                    status = 1;
                }
                else {
                    status = 0;
                }
//          5 将一行中的数据封装到对象中
                Song_messageUtil song_messageUtil = new Song_messageUtil(id, status, title, singer, album, path, time);
                arrayList_message.add(song_messageUtil);

                id++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
//        myMusicListAdapter.notifyDataSetChanged();
    }

    public void OnClick_FindLocalMusic(View view) {
        //TODO: 查找歌曲
    }

    public void OnClick_More(View view) {
        //TODO: 点击标题栏的 更多按钮，弹出对话框 进行歌曲扫描、缓存清理等功能
    }

    public void OnClick_Back(View view) {
        this.finish();
    }

    private class MyMusicListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList_message.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList_message.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            final Song_messageUtil song_messageUtil = (Song_messageUtil) getItem(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.songs_messages_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.title = convertView.findViewById(R.id.id_textView_songTitle);
                viewHolder.signer = convertView.findViewById(R.id.id_textView_singer);
                viewHolder.album = convertView.findViewById(R.id.id_textView_album);
                viewHolder.song = convertView.findViewById(R.id.id_imageView_song);
                viewHolder.status = convertView.findViewById(R.id.id_imageView_playing);

                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(song_messageUtil.getTitle());
            viewHolder.signer.setText(song_messageUtil.getSinger());
            viewHolder.album.setText(" | " + song_messageUtil.getAlbum());
            switch (song_messageUtil.getStatus())
            {
                case Song_messageUtil.PRE_FIND:
                case Song_messageUtil.NO:
                    //使用默认图片
                    viewHolder.song.setImageBitmap(default_picture);
                    break;
                case Song_messageUtil.YES:
                    //异步请求 二级缓冲
                    imageLoader.displayBitmap(viewHolder.song, song_messageUtil.getId());
                    break;
            }
            if (song_messageUtil.isPlaying())
            {
                viewHolder.status.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.status.setVisibility(View.GONE);
            }

            return convertView;
        }

        private class ViewHolder {
            TextView title, signer, album;
            ImageView status, song;
        }
    }


    public Bitmap setArtwork(String url) {
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        Bitmap bitmap = null;
        try
        {
            mediaMetadataRetriever.setDataSource(url);
            byte[] picture = mediaMetadataRetriever.getEmbeddedPicture();
            if (picture != null && picture.length > 0)
            {
                bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }
}
