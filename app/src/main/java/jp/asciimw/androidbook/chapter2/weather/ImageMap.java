package jp.asciimw.androidbook.chapter2.weather;

import android.graphics.Bitmap;

import java.util.Map;
import java.util.WeakHashMap;

public class ImageMap
{

    private static final Map<String, Bitmap> cache;

    static {
        cache = new WeakHashMap<String, Bitmap>();
    }

    public static Bitmap getImage(String key)
    {
        synchronized (cache) {
            return cache.get(key);
        }
    }

    public static void setImage(String key, Bitmap image)
    {
        synchronized (cache) {
            cache.put(key, image);
        }
    }

    public static void clearImage(String key)
    {
        synchronized (cache) {
            if (cache.containsKey(key)) {
                cache.remove(key);
            }
        }
    }
}
