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
			
			update();
			
			blinkHandler.postDelayed( this, blinkDelay );
		}
	};
	
	Teletype( TextView tv )
	{
		textView = tv;
	}
	
	void update()
	{
		CharSequence newText = text;
		
		if ( cursorState == 1 )
		{
			newText = newText + "\u2588";  // full block
		}
		
		textView.setText( newText );
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
	
	void input( CharSequence chars )
	{
		startBlinking();
		
		text = text + chars;
		
		update();
	}
	
}

