package com.threeml.awu.util;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

/**
 * BitmapFont class (Draw Text)s 
 * @author Dean
 *
 */

public class BitmapFont extends Sprite {
	
	List<Bitmap> BitmapList = new ArrayList<Bitmap>();
	
	public BitmapFont(float x, float y, GameScreen gameScreen, String str) {
		super(x, y, 50.0f, 50.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("Font"), gameScreen);

		//Create a default string
		updateString(str);
	}
	
	//Returns an array of bitmaps to be drawn from the string
	public void updateString(String str){
		char[] letterArray = str.toCharArray();
	
		for(int i=0;i<letterArray.length;i++){
			//Returns a bitmap of the letter
			BitmapList.add(drawLetter(letterArray[i]));	
			break;
		}

	}
	//Return a bitmap of the char
	private Bitmap drawLetter(char c){
		int width = 12;
		int height = 16;
		int locationx = 0,locationy = 0;
		char[] special = {'.', ':',',',';','\'','"','(','!','?',')','+','-','*','/','='}; //Special Chars in order on Bitmap
		
		/**Pixel locations of the different sets in the image...
		*look at the image to understand
		*Lowercase(0,0),(312,16)
		*UpperCase(324,0),(636,16)
		*Num(0,19),(120,35)
		*Special (120,19),(300,35). : , ; ' " ( ! ? ) + - * / =
		*/
		//Logic to get location of letter using ASCII location of chars
		if(c >= 'a' && c <= 'z'){ //97 - 122
			locationx = ((int)c-97)*width;
			locationy = 0; 
		}else if(c >= 'A' && c <= 'Z'){ //65 - 90
			locationx = 324 + (((int)c-65)*width);
			locationy = 0; 
		}else if((int)c >=0 && (int)c <=9){ //48-57
			locationx = ((int)c-48)*width;;
			locationy = 19; 
		}else{
			boolean found = false;
			for(int i = 0;i<special.length;i++){
				if(special[i]==c){
					found = true;
					locationx = 120+(i)*width;
					locationy = 19; 
					break;
				}
			}
			if(!found){ //If not found, output space which is between z and A
				locationx = 324 - width;
				locationy = 0; 
			}
		}
		
		Log.v("bitmap?",mBitmap.getWidth()+"");
		return Bitmap.createBitmap(mBitmap, locationx, locationy, width, height);
	}
	
	public void update(ElapsedTime elapsedTime, float x, float y) {
		
	}
	/**
	 ** Update method that will follow an object like player (for health etc)
	 **	@author Dean 
	 **/
	public void update(ElapsedTime elapsedTime, Vector2 objectAcceleration,
			Vector2 objectVelocity) {
		
		this.acceleration = objectAcceleration;
		this.velocity = objectVelocity;
		super.update(elapsedTime);
		
	}

}
