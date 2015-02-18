package com.threeml.awu.world;

import android.graphics.Bitmap;
import android.util.Log;
/**
 * FrameHandler class, handles large images with small sub-images
 * 
 * @author Mary-Jane
 *
 */
public class FrameHandler {
	
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	/** Bitmap Full Image */
	Bitmap mFullImage;
	/** int No. of rows in image */
	int mRows;
	/** int No. of columns in image */
	int mColumns;
	/** int Current column in use */
	int mCurrentColumn;
	/** int Current row in use */
	int mCurrentRow;
	/** Animation Animation class for use in animated sprites */
	Animation mAnimation;
	
	/**
	 * Creates FrameHandler object
	 * 
	 * @param fullImage
	 * 				Bitmap spritesheet that can be divided into frames
	 * @param rows 
	 * 				Number of rows in the spritesheet
	 * @param columns
	 * 				Number of columns in the spritesheet
	 */
	public FrameHandler(Bitmap fullImage, int rows, int columns){
		this.mFullImage = fullImage;
		this.mRows = rows;
		this.mColumns = columns;
		mCurrentColumn = 0;
		mCurrentRow = 0;
		mAnimation = new Animation(this);
	}
	/**
	 * Enable for sprite animation
	 * 
	 * @param enable
	 * 				Set true to enable animation, or false to use only one frame
	 */
	public void enableAnimation(boolean enable){
		mAnimation.enabled(enable);
	}
	/**
	 * Only call if Animation is enabled, returns null if enabled not specified
	 * @return animation
	 */
	public Animation getAnimation(){
		if(mAnimation.enabled()){
			return mAnimation;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Set the current frame
	 * 
	 * @param r
	 * 				Sets the row
	 * @param c 
	 * 				Sets the column
	 */
	protected void setFrame(int r, int c){
		if(r >= 0 && r <= mRows){
			this.mCurrentRow = r;
		}
		if(c >= 0 && c <= mColumns){
			this.mCurrentColumn = c;
		}
	}
	
	/**
	 * Get sub image using row and column as parameters to calculate the current frame
	 * 
	 * @param r	
	 * 				Row of current frame
	 * @param c 
	 * 				Column of current frame
	 * @return bitmap
	 */
	public Bitmap getFrameImage(){
		//if((c >= 0 && c <= mColumns) && (r >= 0 && r <= mRows)){
			int width = (int) (this.mFullImage.getWidth() / mColumns);
			
			int height = (int) (this.mFullImage.getHeight() / mRows);
			int srcY = mCurrentRow * height;
			int  srcX = mCurrentColumn * width;
			/*Log.v("currentframe", "getImageFrame() called");
			Log.v("currentframe", 	" y : " + srcY +
									" x : " + srcX +
									" width : " + width +
									" height : " + height);*/
			
			return Bitmap.createBitmap(mFullImage, srcY, srcX, width, height);
	}
	/**
	 * Get Full Image
	 * 
	 * @return fullImage
	 */
	public Bitmap getFullImage() {
		return mFullImage;
	}
	
	/**
	 * Set Full Image
	 * @param fullImage
	 */
	public void setFullImage(Bitmap fullImage) {
		mFullImage = fullImage;
	}
	
	/**
	 * Get number of rows in image
	 * @return rows
	 */
	public int getRows() {
		return mRows;
	}
	
	/**
	 * Set Rows
	 * @param rows
	 */
	public void setRows(int rows) {
		mRows = rows;
	}
	
	/**
	 * Get number of columns in image
	 * @return columns
	 */
	public int getColumns() {
		return mColumns;
	}
	
	/**
	 * Set Columns
	 * 
	 * @param columns
	 */
	public void setColumns(int columns) {
		mColumns = columns;
	}
	
	/**
	 * Get Current Column
	 * 
	 * @return currentColumn
	 */
	public int getCurrentColumn() {
		return mCurrentColumn;
	}
	
	/**
	 * Set Current Column
	 * @param currentColumn
	 */
	public void setCurrentColumn(int currentColumn) {
		mCurrentColumn = currentColumn;
	}
	
	/**
	 * Get Current Row
	 * @return currentRow
	 */
	public int getCurrentRow() {
		return mCurrentRow;
	}
	
	/**
	 * Set Current Row
	 * @param currentRow
	 */
	public void setCurrentRow(int currentRow) {
		mCurrentRow = currentRow;
	}
	
	
	
}
