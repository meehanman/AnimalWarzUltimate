package com.threeml.awu.world.dashboardobject;

import android.graphics.Bitmap;

import com.threeml.awu.engine.ElapsedTime;
import com.threeml.awu.engine.graphics.IGraphics2D;
import com.threeml.awu.util.BitmapFont;
import com.threeml.awu.util.GraphicsHelper;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.LayerViewport;
import com.threeml.awu.world.ScreenViewport;

/**
 * Used for text not attached to game objects
 * 
 * @author Mary-Jane
 * 
 */
public class OnScreenText extends BitmapFont {

	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	private boolean mVisible;

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Creates new on screen text object
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
	public OnScreenText(float x, float y, GameScreen gameScreen, String str,
			int fontSize) {
		super(x, y, gameScreen, str, fontSize);
		mVisible = true;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Overrides the BitmapFont buildString method to take de-centralise text
	 * 
	 * @return string
	 */
	@Override
	protected String buildString(String str) {
		return str;
	}

	/**
	 * Set if notification is visible
	 * 
	 * @param vis
	 *            Visibility of notification
	 */
	public void setVisible(boolean vis) {
		this.mVisible = vis;
	}

	/**
	 * Returns if text is set to visible
	 * 
	 * @return visible
	 */
	public boolean isVisible() {
		return mVisible;
	}

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
					graphics2D.drawBitmap(mTextImage, drawMatrix, null);
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Get the bitmap text image
	 * 
	 * @return text image
	 */
	public Bitmap getTextImage() {
		return mTextImage;
	}
}
