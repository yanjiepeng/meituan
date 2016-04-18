package com.example.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class YFImageUtil {
	
	/**
	 *  将字节数组转换为ImageView可调用的Bitmap对象
	 * @param bytes
	 * @param opts
	 * @return
	 */
	
	public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {

	     if (bytes != null)

	        if (opts != null) 
	             return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
	        else

	            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
	     return null;

	}
	
	/** 
     * @param 图片缩放 
     * @param bitmap 对象 
     * @param w 要缩放的宽度 
     * @param h 要缩放的高度 
     * @return newBmp 新 Bitmap对象 
     */  
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        Matrix matrix = new Matrix();  
        float scaleWidth = ((float) w / width);  
        float scaleHeight = ((float) h / height);  
        matrix.postScale(scaleWidth, scaleHeight);  
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,  
                matrix, true);  
        return newBmp;  
    }  
}
