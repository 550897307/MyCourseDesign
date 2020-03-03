package com.example.utils.Loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.example.mycoursedesign.R;
import com.example.utils.BitmapHelper;
import com.example.utils.Cache.FileCache;
import com.example.utils.Cache.MemoryCache;
import java.io.File;

public class AsyncImageLoader {

    private static final String TAG = "AsyncImageLoader";
    Bitmap default_picture = null;
    //caches
    private MemoryCache memoryCache;
    private FileCache fileCache;

    //Asynchronous task
    private static AsyncImageLoader imageLoader;

    class AsyncImageDownloader extends AsyncTask<Void, Void, Bitmap>{
        private ImageView imageView;
        private long fileName;

        public AsyncImageDownloader(ImageView imageView, long fileName){
            this.imageView = imageView;
            this.fileName = fileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (default_picture != null)
            {
                imageView.setImageBitmap(default_picture);
            }
            else {
                imageView.setImageResource(R.drawable.default_music_playing);
            }
        }

        @Override
        protected Bitmap doInBackground(Void... arg0) {
            Bitmap bitmap = null;
            File file = fileCache.getFile(fileName);
            if(file != null)
                bitmap = BitmapHelper.decodeFile(file, null);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(result != null && imageView != null)
                imageView.setImageBitmap(result);

            //TODO cache the bitmap both in sdcard & memory
            memoryCache.put(fileName, result);// key is a unique token, value is the bitmap

//            fileCache.put(fileName, result);
        }
    }

    private AsyncImageLoader(Context context){
        this.memoryCache = new MemoryCache();
        this.fileCache = new FileCache(context);
        default_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_music_playing);
    }

    public static AsyncImageLoader getInstance(Context context){
        if(imageLoader == null)
            imageLoader = new AsyncImageLoader(context);

        return imageLoader;
    }

    public void displayBitmap(ImageView imageView, Long fileName){
        //no pic for this item
        if(fileName < 0)
            return;

        Bitmap bitmap = getBitmap(fileName);
        //search in cache, if there is no such bitmap, launch downloads
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            Log.v("测试:", "内存中获取!");
        }
        else{
            Log.v("测试:", "文件中获取!");
            new AsyncImageDownloader(imageView, fileName).execute();
        }
    }

    public Bitmap getBitmap(long key){
        Bitmap bitmap = null;
        //1. search memory
        bitmap = memoryCache.get(key);
//
//        //2. search sdcard
//        if(bitmap == null){
//            File file = fileCache.getFile(key);
//            if(file != null)
//                bitmap = BitmapHelper.decodeFile(file, null);
//        }

        return bitmap;
    }

    public void clearCache(){
        if(memoryCache != null)
            memoryCache.clear();
        if(fileCache != null)
            fileCache.clear();
    }
}