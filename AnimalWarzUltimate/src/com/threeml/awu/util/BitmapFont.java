package com.threeml.awu.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.world.FrameHandler;
import com.threeml.awu.world.GameObject;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;
import com.threeml.awu.world.Sprite;

/**
 * BitmapFont class Draw Text on Screen
 * 
 * @author Dean & Mary-Jane
 * 
 * 
 */

public class BitmapFont extends Sprite {

	FrameHandler mFrameHandler;
	private Bitmap mTextImage;
	private final int mTextCellwidth = 12;
	private final int mTextCellheight = 18;
	List<Bitmap> BitmapList = new ArrayList<Bitmap>();
	private int mFontSize;
	private String mText;
	private static Bitmap mFont;

	public BitmapFont(float x, float y, GameScreen gameScreen, String str,
			int fontSize) {
		// super(x, y, (str.toCharArray().length * mTextCellwidth),
		// mTextCellheight, mFont, gameScreen);
		super(x, y, 200, 20, mFont, gameScreen);
		try {

			mFont = gameScreen.getGame().getAssetManager().getBitmap("Font");
			mFontSize = fontSize;
			// Create a default string
			mFrameHandler = new FrameHandler(mFont, 2, 53);
			mText = str;
			mTextImage = Bitmap.createBitmap(mText.length() * mTextCellwidth,
					mTextCellheight, Bitmap.Config.ARGB_8888);
			mTextImage = getTextAsImage();
		} catch (Exception e) {
			// Log.e("Text Error", "BitmapFont constructor error : " + e);
		}
	}

	private Bitmap getTextAsImage() {
		try {
			try {

				updateString();
			} catch (Exception e) {
				Log.v("Text Test", "Image error : " + e);
			}
			Canvas canvas = new Canvas(mTextImage);
			int count = 0;
			for (Bitmap b : BitmapList) {
				canvas.drawBitmap(b, (count * mTextCellwidth), 0, null);
				count++;
			}
			// Log.v("Text Test", "After | Image size : " +
			// mTextImage.getWidth() + ", " + mTextImage.getHeight());
		} catch (Exception e) {
			// Log.e("Text Error", "BitmapFont getTextAsImage error : " + e);
		}
		return mTextImage;
	}

	// Returns an array of bitmaps to be drawn from the string
	public void updateString() {
		try {
			// Log.v("Text Test", mText);

			for (char c : mText.toCharArray()) {
				// Returns a bitmap of the letter
				BitmapList.add(getLetterBitmap(c));

			}
		} catch (Exception e) {
			// Log.e("Text Error", "BitmapFont updateString error : " + e);
		}

	}

	private Bitmap getLetterBitmap(char ch) {
		try {
			int r = 0, c = 0;

			char[] special = { '.', ':', ',', ';', '\'', '"', '(', '!', '?',
					')', '+', '-', '*', '/', '=' }; // Special Chars in order on
													// Bitmap

			/**
			 * Pixel locations of the different sets in the image... look at the
			 * image to understand Lowercase(0,0),(312,16)
			 * UpperCase(324,0),(636,16) Num(0,19),(120,35) Special
			 * (120,19),(300,35). : , ; ' " ( ! ? ) + - * / =
			 */
			// Logic to get location of letter using ASCII location of chars
			if (ch >= 'a' && ch <= 'z') { // 97 - 122
				c = (int) ch - 97;
				r = 0;
			} else if (ch >= 'A' && ch <= 'Z') { // 65 - 90
				c = (int) ch - 65;
				r = 0;
			} else if (ch >= '0' && ch <= '9') { // 48-57
				if(ch == '0'){
					c = 9;
				}else {
					c = (int) ch - 49;
				}
				r = 1;
			} else {
				boolean found = false;
				/*
				 * for(int i = 0;i<special.length;i++){ if(special[i]==ch){
				 * found = true; c = 120+(i)*textCellwidth; r = 1; break; } }
				 */
				if (!found) { // If not found, output space which is between z
								// and A
					c = 0;
					r = 0;
				}

			}
			mFrameHandler.setFrame(r, c);
			// Log.v("Image Test", "Char : " + ch + " |Col : " + c + " |Row : "
			// + r);
		} catch (Exception e) {
			// Log.e("Text Error", "BitmapFont getLetterBitmap error : " + e);
		}

		return mFrameHandler.getFrameImage();
	}

	/**
	 ** Update method that will follow an object like player (for health etc)
	 ** 
	 * @author Dean
	 **/
	public void update(ElapsedTime elapsedTime, GameObject GameObj) {

		this.position = GameObj.position;
		this.setY(GameObj.getBound().y + 5f);

		super.update(elapsedTime);

	}

	/**
	 * @author Dean
	 * 
	 *         Override Draw method used to display text on screen from the
	 *         custom bitmaps created by drawLetter.
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		try {
			if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
					screenViewport, drawSourceRect, drawScreenRect)) {

				float scaleX = (float) drawScreenRect.width()
						/ (float) drawSourceRect.width();
				float scaleY = (float) drawScreenRect.height()
						/ (float) drawSourceRect.height();

				// Build an appropriate transformation matrix
				drawMatrix.reset();
				drawMatrix.postScale(scaleX, scaleY);
				drawMatrix.postRotate(orientation, scaleX * mBitmap.getWidth()
						/ 2.0f, scaleY * mBitmap.getHeight() / 2.0f);
				drawMatrix.postTranslate(drawScreenRect.left,
						drawScreenRect.top);

				float BoundXPos = this.getBound().x;
				// Draw the image
				graphics2D.drawBitmap(getTextAsImage(), drawMatrix, null);

				// Reset the draw location for the images
				this.getBound().x = BoundXPos;
			}
		} catch (Exception e) {
			// Log.e("Text Error", "BitmapFont draw error : " + e);
		}
	}

}
