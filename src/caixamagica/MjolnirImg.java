package caixamagica;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
/**
 * Transform a raw image data into a drawable ready for use in an Android Activity
 * @author Leu
 * 
 */
public class MjolnirImg {
	private Drawable img; 
	
	/**
	 * 
	 * @param rawImageData complete byte array of an image file
	 */
	public MjolnirImg(byte[] rawImageData)
	{
		img = getDrawableFromByteArray(rawImageData);		
	}
	/**
	 * Transform a raw image data into a drawable with device's right DPI and scale ready for use in an Android Activity
	 * @param contexto
	 * @param ArrayDeBytes
	 */
	public MjolnirImg(Context contexto, byte[] ArrayDeBytes)
	{
		img = getDrawableFromByteArray(contexto, ArrayDeBytes);
	}
	
	@SuppressWarnings("deprecation")
	private BitmapDrawable getDrawableFromByteArray(byte[] b){
		Log.i("Debug","Tamanho do byteArray = " + b.length);
		return new BitmapDrawable(BitmapFactory.decodeByteArray(b, 0, b.length));
	}
	
	private BitmapDrawable getDrawableFromByteArray(Context ctx, byte[] b){
		Log.i("Debug","Tamanho do byteArray = " + b.length);
		return new BitmapDrawable(ctx.getResources(),BitmapFactory.decodeByteArray(b, 0, b.length));
	}
	/**
	 * 
	 * @return return the drawable converted from byte array
	 */
	public Drawable getDrawable()
	{
		return img;
	}
	
	
	
	
	
	
	
	

}
