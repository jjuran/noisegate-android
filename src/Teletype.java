package com.metamage.noisegate;

import android.widget.TextView;


public final class Teletype
{
	
	private TextView textView;
	
	Teletype( TextView tv )
	{
		textView = tv;
	}
	
	void clear()
	{
		textView.setText( "" );
	}
	
	void setText( int stringId )
	{
		textView.setText( stringId );
	}
	
}

