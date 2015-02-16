package com.threeml.awu.world;
/**
 * Animation Class, used in conjunction with frame handler to create illusion of movement
 * 
 * @author Mary-Jane
 *
 */
public class Animation {
	
	/**
	 * Variables
	 */
	private FrameHandler mFrameHandler;
	private boolean mEnabled = false;

	/**
	 * Constructor, Animation is disabled by default
	 * @param frameHandler
	 */
	public Animation(FrameHandler frameHandler){
		this.mFrameHandler = frameHandler;
		mEnabled = false;
	}
	
	/**
	 * skips to the next frame of the image
	 */
	public void nextFrame(){
		/*if(mColumns > 0){
			currentFrame = currentFrame++ % mColumns;
			Log.v("CurrentFrame", currentFrame + "");
		}*/
		try {
			if(mFrameHandler.getColumns() > 0){
				if(mFrameHandler.mCurrentColumn < this.mFrameHandler.getColumns()){
					mFrameHandler.setFrame(mFrameHandler.getCurrentRow(), this.mFrameHandler.getCurrentColumn() + 1);
				} else {
					mFrameHandler.setFrame(mFrameHandler.getCurrentRow(), mFrameHandler.getCurrentColumn());
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
