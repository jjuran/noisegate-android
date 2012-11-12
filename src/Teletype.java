package com.metamage.noisegate;

import android.widget.TextView;


public final class Teletype
{
	
	private String text = "";
	
	private TextView textView;
	
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
	
}

