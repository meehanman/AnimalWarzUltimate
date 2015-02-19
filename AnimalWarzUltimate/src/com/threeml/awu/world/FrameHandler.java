package com.threeml.awu.world;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
/**
 * FrameHandler takes in a larger image and returns a smaller subimage
 * 
 * Initialise with a sprite sheet with a number of rows and columns
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
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructor
	// /////////////////////////////////////////////////////////////////////////
	
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
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
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
		
		Log.v("nextFrameTest", "R: "+this.mCurrentRow+" C: "+this.mCurrentColumn);
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
			int width = (int) (this.mFullImage.getWidth() / mColumns);
			
			int height = (int) (this.mFullImage.getHeight() / mRows);
			int srcX = mCurrentColumn * width;
			int srcY = mCurrentRow * height;
			

			return Bitmap.createBitmap(mFullImage, srcX,srcY, width, height);
	}
	

	/**
	 * skips to the next frame of the image
	 */
	public void nextFrameHorizontal(){
		try {			
			if(getColumns() > 0){
				if(getCurrentColumn() < (getColumns() - 1)){
					setFrame(getCurrentRow(), getCurrentColumn() + 1);
				} else {
					setFrame(getCurrentRow(), 0);
				}
				
			}
		}catch(Exception e){
			setFrame(0,0);
		}
	}
	
	/**
	 * skips to the next frame of the image
	 */
	public void nextFrameVertical(){
		try {			
			if(getRows() > 0){
				if(getCurrentRow() < (getRows() - 1)){
					setFrame(getCurrentRow() + 1, getCurrentColumn());
				} else {
					setFrame(0, getCurrentColumn());
				}
				
			}
		}catch(Exception e){
			setFrame(0,0);
		}
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
