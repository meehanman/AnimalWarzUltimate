package com.threeml.awu.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.SpritesheetHandler;
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

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	/** SpritesheetHandler used to handle the font spritesheet */
	SpritesheetHandler mFrameHandler;
	/** Image used to represent this object */
	protected Bitmap mTextImage;
	/** Width of characters in font spritesheet */
	protected final static int mTextCellwidth = 12;
	/** Height of characters in font spritesheet */
	protected final static int mTextCellheight = 19;
	/** List that holds the individual characters */
	List<Bitmap> BitmapList = new ArrayList<Bitmap>();
	/** Size of font, as it appears on the screen */
	private int mFontSize;
	/** Text displayed */
	protected String mText;
	/** Bitmap font spritesheet */
	private static Bitmap mFont;
	/** Max number of characters allowed in text */
	private final static int MAX_CHARS = 20;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Creates new BitmapFont object
	 * 
	 * @param x
	 *            X position of text
	 * @param y
	 *            Y position of text
	 * @param gameScreen
	 *            Gamescreen to which text belongs
	 * @param str
	 *            Text to display
	 */
	public BitmapFont(float x, float y, GameScreen gameScreen, String str) {
		super(x, y, (MAX_CHARS * mTextCellwidth), mTextCellheight, mFont,
				gameScreen);
		// super(x, y, 200, 20, mFont, gameScreen);
		try {

			mFont = gameScreen.getGame().getAssetManager().getBitmap("Font");
			mFontSize = mTextCellheight;
			// Create a default string
			mFrameHandler = new SpritesheetHandler(mFont, 2, 53);
			mText = buildString(str);
			createNewTextBitmap();

		} catch (Exception e) {
			Log.e("Text Error", "BitmapFont constructor error : " + e);
		}
	}

	/**
	 * Creates new BitmapFont object
	 * 
	 * @param x
	 *            X position of text
	 * @param y
	 *            Y position of text
	 * @param gameScreen
	 *            Gamescreen to which text belongs
	 * @param str
	 *            Text to display
	 * @param fontSize
	 *            Determines size of text on screen
	 */
	public BitmapFont(float x, float y, GameScreen gameScreen, String str,
			int fontSize) {
		super(x, y, (MAX_CHARS * (int) (fontSize * 0.75)), fontSize, mFont,
				gameScreen);
		// super(x, y, 200, 20, mFont, gameScreen);
		try {

			mFont = gameScreen.getGame().getAssetManager().getBitmap("Font");
			mFontSize = fontSize;
			// Create a default string
			mFrameHandler = new SpritesheetHandler(mFont, 2, 53);
			mText = buildString(str);
			createNewTextBitmap();
		} catch (Exception e) {
			Log.e("Text Error", "BitmapFont constructor error : " + e);
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Used the centralise the text and cut text more than 20 characters (MJ)
	 * Temporary solution because I can't get the text to position correctly
	 * above game objects
	 * 
	 * @param str
	 *            String to be displayed
	 * @return String
	 */
	protected String buildString(String str) {
		String buffer = "";
		if (str.length() > MAX_CHARS) {
			str = str.substring(0, 19);
		} else if (str.length() < MAX_CHARS) {
			int difference = (MAX_CHARS - str.length());
			for (int i = 0; i < (difference / 2); i++) {
				buffer += " ";
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(buffer);
		sb.append(str);
		sb.append(buffer);
		return sb.toString();
	}

	/**
	 * Creates a new Bitmap that is mutable then calls method to create the
	 * bitmap that will represent this object
	 */
	protected void createNewTextBitmap() {
		mTextImage = Bitmap.createBitmap(
				(int) (mText.length() * mTextCellwidth), mTextCellheight,
				Bitmap.Config.ARGB_8888);
		getTextAsImage();
	}

	/**
	 * Creates a bitmap of the text
	 * 
	 * @return Bitmap text
	 */
	protected Bitmap getTextAsImage() {
		try {
			updateString();

			Canvas canvas = new Canvas(mTextImage);
			int count = 0;
			for (Bitmap b : BitmapList) {
				canvas.drawBitmap(b, (int) (count * mTextCellwidth), 0, null);
				count++;
			}
			// mTextImage.getWidth() + ", " + mTextImage.getHeight());
		} catch (Exception e) {
			Log.e("Text Error", "BitmapFont getTextAsImage error : " + e);
		}
		return mTextImage;
	}

	/**
	 * Creates an array of bitmaps to be drawn from the string
	 */
	public void updateString() {
		try {
			BitmapList.clear();
			for (char c : mText.toCharArray()) {
				// Returns a bitmap of the letter
				BitmapList.add(getLetterBitmap(c));

			}
		} catch (Exception e) {
			Log.e("Text Error", "BitmapFont updateString error : " + e);
		}

	}

	/**
	 * Creates a bitmap of an individual character
	 * 
	 * @param ch
	 *            Character to create bitmap of
	 * @return Bitmap
	 */
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
				c = ((int) ch - 65) + 27;
				r = 0;
			} else if (ch >= '0' && ch <= '9') { // 48-57
				if (ch == '0') {
					c = 9;
				} else {
					c = (int) ch - 49;
				}
				r = 1;
			} else if (ch >= 33 && ch <= 64) {
				r = 1;
				switch (ch) {
				case 46:
					c = 10;
					break;
				case 58:
					c = 11;
					break;
				case 44:
					c = 12;
					break;
				case 59:
					c = 13;
					break;
				case 39:
					c = 15;
					break;
				case 34:
					c = 17;
					break;
				case 40:
					c = 19;
					break;
				case 33:
					c = 20;
					break;
				case 63:
					c = 21;
					break;
				case 41:
					c = 22;
					break;
				case 43:
					c = 24;
					break;
				case 45:
					c = 25;
					break;
				case 42:
					c = 26;
					break;
				case 47:
					c = 27;
					break;
				case 61:
					c = 28;
					break;
				default:
					c = 26;
					r = 0;
					break;
				}

			} else {
				boolean found = false;
				/*
				 * for(int i = 0;i<special.length;i++){ if(special[i]==ch){
				 * found = true; c = 120+(i)*textCellwidth; r = 1; break; } }
				 */
				if (!found) { // If not found, output space which is between z
								// and A
					c = 26;
					r = 0;
				}

			}
			mFrameHandler.setFrame(r, c);
		} catch (Exception e) {
			Log.e("Text Error", "BitmapFont getLetterBitmap error : " + e);
		}

		return mFrameHandler.getFrameImage();
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

				// Draw the image
				graphics2D.drawBitmap(mTextImage, drawMatrix, null);
			}
		} catch (Exception e) {
			// Turned off this error log because it's always being hit and I'm
			// not sure why
			// so to save logcat is temporarily switched off until the problem
			// can be solved
			// Log.e("Text Error", "BitmapFont draw error : " + e);
		}
	}

	/**
	 * Change the string of text and update bitmap to reflect the changes
	 * 
	 * @param str
	 *            New string
	 */
	public void updateText(String str) {
		mText = buildString(str);
		createNewTextBitmap();
	}

}
