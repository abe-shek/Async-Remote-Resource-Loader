package com.example.abhishek.remoteresourceloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.UnsupportedEncodingException;

public class ConvertersUtils {

    public Bitmap byteArrayToBitmap(byte[] input){
        return BitmapFactory.decodeByteArray(input,0,input.length);
    }

    public String byteArrayToUri(byte[] input){
       if(input!=null){
           try {
            return new String(input,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       }
        return null;
    }
}
