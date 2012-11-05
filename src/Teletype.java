package com.metamage.noisegate;

import android.os.Handler;
import android.widget.TextView;


public final class Teletype
{
	
	private static final int blinkDelay = 500;
	
	private String text = "";
	
	private TextView textView;
	
	private Handler blinkHandler = new Handler();
	
	private int cursorState = -1;
	
	private Runnable blink = new Runnable()
	{
		public void run()
		{
			if ( cursorState < 0 )
			{
				return;
			}
			
			cursorState = 1 - cursorState;
			
			CharSequence newText = text;
			
			if ( cursorState == 1 )
			{
				newText = newText + "\u2588";
			}
			
			textView.setText( newText );
			
			blinkHandler.postDelayed( this, blinkDelay );
		}
	};
	
	Teletype( TextView tv )
	{
		textView = tv;
	}
	
	void clear()
	{
		text = "";
		
		textView.setText( "" );
	}
	
	void setText( String s )
	{
		text = s;
		
		textView.setText( s );
	}
	
	void startBlinking()
	{
		if ( cursorState < 0 )
		{
			cursorState = 0;
			
			blinkHandler.post( blink );
		}
	}
	
	void stopBlinking()
	{
		cursorState = -1;
	}
	
}

