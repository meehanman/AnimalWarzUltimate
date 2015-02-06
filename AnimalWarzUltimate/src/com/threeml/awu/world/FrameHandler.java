package com.threeml.awu.world;

import android.graphics.Bitmap;
import android.util.Log;

public class FrameHandler {
	
	/**
	 * Variables
	 */
	Bitmap mFullImage;
	int mRows;
	int mColumns;
	int mCurrentColumn;
	int mCurrentRow;
	Animation mAnimation;
	
	/**
	 * Constructor
	 * @param fullImage	(Bitmap)
	 * @param rows	(int)
	 * @param columns (int)
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
	 * @param enable
	 */
	public void enableAnimation(boolean enable){
		mAnimation.enabled(enable);
	}
	/**
	 * Only call if Animation is enabled
	 * @return
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
	 * @param r (int) Row
	 * @param c (int) Column
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
	 * @param r	(int) Row of current frame
	 * @param c (int) Column of current frame
	 * @return
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
		//}
		//return mFullImage;
	}
	/**
	 * Get Full Image
	 * @return Bitmap
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
	 * Get Rows
	 * @return int
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
	 * Get COlumns
	 * @return int
	 */
	public int getColumns() {
		return mColumns;
	}
	
	/**
	 * Set Columns
	 * @param columns
	 */
	public void setColumns(int columns) {
		mColumns = columns;
	}
	
	/**
	 * Get Current Column
	 * @return int
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
	 * @return int
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
