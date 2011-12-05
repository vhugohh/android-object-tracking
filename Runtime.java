package com.muohio.soccerRobot;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.SurfaceHolder;

class Runtime extends CameraRun {
    private Mat mYuv;
    private Mat mRgba;
    private Mat mGraySubmat;
    private Mat mIntermediateMat;
    private String command = "Foward";
   
    private int move = 0, start=0;
    

    public Runtime(Context context) {
        super(context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        super.surfaceChanged(_holder, format, width, height);

        synchronized (this) {
            // initialize Mats before usage
            mYuv = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
            mGraySubmat = mYuv.submat(0, getFrameHeight(), 0, getFrameWidth());

            mRgba = new Mat();
            mIntermediateMat = new Mat();
        }
    }

    @Override
    protected Bitmap processFrame(byte[] data) {
        mYuv.put(0, 0, data);
        
        switch (RuntimeJava.viewMode) {
        case RuntimeJava.VIEW_MODE_GRAY:
            Imgproc.cvtColor(mGraySubmat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
            break;
        case RuntimeJava.VIEW_MODE_RGBA:
            Imgproc.cvtColor(mYuv, mRgba, Imgproc.COLOR_YUV420sp2RGB, 4);
            if(move==0){
            	command = "Stopped";
            	SoundJava.stopSound();
            	SoundJava.playSound(5, this.getContext());
            }
            else if(move==1){
            	command = "Forward";
            	SoundJava.stopSound();
            	SoundJava.playSound(1, this.getContext());
            }
            else if(move==2){
            	SoundJava.stopSound();
            	SoundJava.playSound(2, this.getContext());
            	command = "Left";
            }
            else if(move==3){
            	SoundJava.stopSound();
            	SoundJava.playSound(3, this.getContext());
            	command = "Right";
            }
            else if(move==4){
            	SoundJava.stopSound();
            	SoundJava.playSound(4, this.getContext());
            	command = "Back";
            }
            Core.putText(mRgba, command, new Point(10, 50), 2/* CV_FONT_HERSHEY_COMPLEX */, 1, new Scalar(255, 0, 0, 255), 3);
            break;
        case RuntimeJava.VIEW_MODE_CANNY:
            Imgproc.Canny(mGraySubmat, mIntermediateMat, 80, 100);
            Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2BGRA, 4);
            break;
        }

        Bitmap bmp = Bitmap.createBitmap(getFrameWidth(), getFrameHeight(), Bitmap.Config.ARGB_8888);
        
        move = 0;
        
        if (Utils.matToBitmap(mRgba, bmp)){
        	int w = bmp.getWidth();
            int h = bmp.getHeight();
        	for(int i=0; i<h;i++){
        		int myR = Color.red(bmp.getPixel(0,i));
    			int myG = Color.green(bmp.getPixel(0,i));
    			int myB = Color.blue(bmp.getPixel(0,i));
    			if(		myR<= RuntimeJava.r+40  && 
        				myR>= RuntimeJava.r-40 &&
        				myG<= RuntimeJava.g+40  && 
        				myG>= RuntimeJava.g-40 &&
        				myB<= RuntimeJava.b+20  && 
        				myB>= RuntimeJava.b-20){
        		move = 2;
        		break;}
    		
    			myR = Color.red(bmp.getPixel(w-1,i));
    			myG = Color.green(bmp.getPixel(w-1,i));
    			myB = Color.blue(bmp.getPixel(w-1,i));
    			if(		myR<= RuntimeJava.r+40  && 
        				myR>= RuntimeJava.r-40 &&
        				myG<= RuntimeJava.g+40  && 
        				myG>= RuntimeJava.g-40 &&
        				myB<= RuntimeJava.b+20  && 
        				myB>= RuntimeJava.b-20){
        		move = 3;
        		break;}
    			
    			
        	}
        	
        	if(move==0){
        		
        	
        	
        	for(int i=0; i<w;i=i+2){
        		if(move==1)
        			break;
            	for(int k = 0; k<h; k=k+2){
            		//for(int j=0; j<1; j++){
            			//Color in = new Color();
            			int myR = Color.red(bmp.getPixel(i, k));
            			int myG = Color.green(bmp.getPixel(i, k));
            			int myB = Color.blue(bmp.getPixel(i, k));
            		
            		if(		myR<= RuntimeJava.r+40  && 
            				myR>= RuntimeJava.r-40 &&
            				myG<= RuntimeJava.g+40  && 
            				myG>= RuntimeJava.g-40 &&
            				myB<= RuntimeJava.b+20  && 
            				myB>= RuntimeJava.b-20){
            			
            		
            			//bmp.setPixel(i, k, Color.MAGENTA);
            			move = 1;
            			   			
            		}
            		}
            	}
        	if(move==0)move=2;
            }
        	
        	//}
        	return bmp;

        }
        bmp.recycle();
        return null;
    }

    @Override
    public void run() {
        super.run();

        synchronized (this) {
            // Explicitly deallocate Mats
            if (mYuv != null)
                mYuv.release();
            if (mRgba != null)
                mRgba.release();
            if (mGraySubmat != null)
                mGraySubmat.release();
            if (mIntermediateMat != null)
                mIntermediateMat.release();

            mYuv = null;
            mRgba = null;
            mGraySubmat = null;
            mIntermediateMat = null;
        }
    }
}
