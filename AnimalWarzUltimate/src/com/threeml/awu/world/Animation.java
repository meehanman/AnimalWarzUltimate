package com.threeml.awu.world;

import android.util.Log;

/**
 * Animation Class, used in conjunction with frame handler to create illusion of movement
 * 
 * 
 * @author Mary-Jane
 *
 */
public class Animation {
	
	// /////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////
	
	private FrameHandler mFrameHandler;
	private boolean mEnabled = false;

	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates Animation object, animation is disabled by default
	 * @param frameHandler
	 */
	public Animation(FrameHandler frameHandler){
		this.mFrameHandler = frameHandler;
		mEnabled = false;
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////
	
	/**
	 * skips to the next frame of the image
	 */
	public void nextFrameHorizontal(){
		try {			
			if(mFrameHandler.getColumns() > 0){
				if(mFrameHandler.getCurrentColumn() < (this.mFrameHandler.getColumns() - 1)){
					mFrameHandler.setFrame(mFrameHandler.getCurrentRow(), this.mFrameHandler.getCurrentColumn() + 1);
				} else {
					mFrameHandler.setFrame(mFrameHandler.getCurrentRow(), 0);
				}
				
			}
		}catch(Exception e){
			mFrameHandler.setFrame(0,0);
		}
	}
	
	/**
	 * skips to the next frame of the image
	 */
	public void nextFrameVertical(){
		try {			
			if(mFrameHandler.getRows() > 0){
				if(mFrameHandler.getCurrentRow() < (this.mFrameHandler.getRows() - 1)){
					mFrameHandler.setFrame(mFrameHandler.getCurrentRow() + 1, this.mFrameHandler.getCurrentColumn());
				} else {
					mFrameHandler.setFrame(0, mFrameHandler.getCurrentColumn());
				}
				
			}
		}catch(Exception e){
			mFrameHandler.setFrame(0,0);
		}
	}
	
	/**
	 * Returns if Animation is enabled
	 * @return
	 */
	public boolean enabled(){
		return mEnabled;
	}
	/**
	 * Set whether Animation is enabled
	 * @param enable
	 */
	public void enabled(boolean enable){
		mEnabled = enable;
	}
}
