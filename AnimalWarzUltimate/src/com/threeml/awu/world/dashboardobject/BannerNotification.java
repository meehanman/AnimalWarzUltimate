package com.threeml.awu.world.dashboardobject;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Create a banner like notification for the user
 * 
 * 
 * @author Mary-Jane Reference:
 *         http://stackoverflow.com/questions/1540272/android
 *         -how-to-overlay-a-bitmap-draw-over-a-bitmap
 */
public class BannerNotification extends OnScreenText {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////

	private Bitmap mBannerImage;
	private boolean mVisible = false;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Create a new BannerNotification object
	 * 
	 * @param x
	 *            X position of text
	 * @param y
	 *            Y position of text
	 * @param gameScreen
	 *            Gamescreen to which player belongs
	 */
	public BannerNotification(float x, float y, GameScreen gameScreen) {
		super(x, y, gameScreen, "0", 150);

	}

	/**
	 * Creates a new Bitmap that is mutable then calls method to create the
	 * bitmap that will represent this object
	 */
	private void createBannerBitmap() {
		mBannerImage = Bitmap
				.createBitmap(
						(int) ((mText.length() * mTextCellwidth) + (mTextCellwidth * 3)),
						mTextCellheight * 3, Bitmap.Config.ARGB_8888);
		mBannerImage.eraseColor(Color.GRAY);
		createNewTextBitmap();
		Canvas canvas = new Canvas(mBannerImage);
		Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(mTextImage,
				(mBannerImage.getWidth() - mTextImage.getWidth()) / 2,
				(mBannerImage.getHeight() - mTextImage.getHeight()) / 2, paint);
	}

	/**
	 * Creates and returns a new Banner bitmap with string text
	 * 
	 * @param text
	 *            The text to appear in the BannerNotification
	 * @return Bitmap
	 */
	public Bitmap getBannerImage() {
		createBannerBitmap();
		return mBannerImage;
	}

	/**
	 * Change the string of text and update bitmap to reflect the changes
	 * 
	 * @param str
	 *            New string
	 */
	public void updateText(String str) {
		mText = str;
		createBannerBitmap();
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Draws the banner if visible set to true
	 * 
	 * @param elapsedTime
	 *            Elapsed time information
	 * @param graphics2D
	 *            Graphics instance
	 * @param layerViewport
	 *            Game layer viewport
	 * @param screenViewport
	 *            Screen viewport
	 */
	@Override
	public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
			LayerViewport layerViewport, ScreenViewport screenViewport) {
		if (isVisible()) {
			try {
				if (GraphicsHelper.getSourceAndScreenRect(this, layerViewport,
						screenViewport, drawSourceRect, drawScreenRect)) {

					// Assigning scaleX and scaleY
					float scaleX = (float) drawScreenRect.width()
							/ (float) drawSourceRect.width();
					float scaleY = (float) drawScreenRect.height()
							/ (float) drawSourceRect.height();

					// Build an appropriate transformation matrix
					drawMatrix.reset();
					drawMatrix.postScale(scaleX, scaleY);
					drawMatrix.postRotate(orientation,
							scaleX * mBitmap.getWidth() / 2.0f, scaleY
									* mBitmap.getHeight() / 2.0f);
					drawMatrix.postTranslate(drawScreenRect.left,
							drawScreenRect.top);

					// Draw the image
					graphics2D.drawBitmap(mBannerImage, drawMatrix, null);
				}
			} catch (Exception e) {
				/*
				 * Turned off this error log because it's always being hit and
				 * I'm not sure why so to save logcat is temporarily switched
				 * off until the problem can be solved Log.e("Text Error",
				 * "BitmapFont draw error : " + e);
				 */
			}
		}
	}

	/**
	 * Returns if banner is set to visible
	 * 
	 * @return visible
	 */
	public boolean isVisible() {

		return mVisible;
	}

	/**
	 * Set if notification is visible
	 * 
	 * @param vis
	 *            Visibility of notification
	 */
	public void setVisible(boolean vis) {
		mVisible = vis;
	}
}
