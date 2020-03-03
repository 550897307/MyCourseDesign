package com.example.utils.Cache;

import android.graphics.Bitmap;
import android.util.Log;
import java.util.WeakHashMap;
//https://my.oschina.net/ryanhoo/blog/93406
public class MemoryCache {
    private static final String TAG = "MemoryCache";

    //WeakReference Map: key=string, value=Bitmap
    private WeakHashMap<Long, Bitmap> cache = new WeakHashMap<>();

    /**
     * Search the memory cache by a unique key.
     * @param key Should be unique.
     * @return The Bitmap object in memory cache corresponding to specific key.
     * */
    public Bitmap get(long key){
        if(key < 0)
        {
            return null;
        }
        return cache.get(key);
    }

    /**
     * Put a bitmap into cache with a unique key.
     * @param key Should be unique.
     * @param value A bitmap.
     * */
    public void put(long key, Bitmap value){
        if(key >= 0 && value != null){
            cache.put(key, value);
            //Log.i(TAG, "cache bitmap: " + key);
            Log.d(TAG, "size of memory cache: " + cache.size());
        }
    }

    /**
     * clear the memory cache.
     * */
    public void clear() {
        cache.clear();
    }
}