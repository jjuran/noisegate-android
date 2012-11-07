package com.metamage.noisegate;

import android.os.Handler;
import android.widget.TextView;


public final class Teletype
{
	
	private static final int blinkDelay = 500;
	
	private String text = "";
	
	private int mark = -1;
	
	private int inputDelay = 0;
	
	private TextView textView;
	
	private Handler inputHandler = new Handler();
	private Handler blinkHandler = new Handler();
	
	private int cursorState = -1;
	
	private Runnable input = new Runnable()
	{
		public void run()
		{
			if ( mark < 0 )
			{
				return;
			}
			
			int delay = inputDelay;
			
			inputDelay = 0;
			
			if ( delay == 0 )
			{
				final boolean newline = text.charAt( mark ) == '\n';
				
				delay = newline ? 1000 : 50;
				
				++mark;
				
				update();
			}
			
			inputHandler.postDelayed( this, delay );
		}
	};
	
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
		
		if ( mark >= text.length() )
		{
			mark = -1;
		}
		
		if ( mark >= 0 )
		{
			newText = text.subSequence( 0, mark );
		}
		
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
		
		mark = -1;
	}
	
	void append( CharSequence chars )
	{
		text = text + chars;
		
		mark = -1;
		
		update();
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
	
	void startInput()
	{
		if ( mark < 0 )
		{
			mark = text.length();
			
			inputHandler.post( input );
		}
	}
	
	void stopInput()
	{
		mark = -1;
		
		update();
	}
	
	void delayInput( int delay )
	{
		inputDelay = delay;
	}
	
	void input( CharSequence chars )
	{
		startBlinking();
		startInput();
		
		text = text + chars;
		
		update();
	}
	
}

