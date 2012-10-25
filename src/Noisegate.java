package com.metamage.noisegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.IOException;


public final class Noisegate extends Activity implements Completion
{
	
	private String code = "";
	
	public void call( IOException exception )
	{
		if ( exception != null )
		{
		}
	}
	
	public void onNumericKey( View v )
	{
		Button key = (Button) v;
		
		code += key.getText();
	}
	
	public void onClearKey( View v )
	{
		code = "";
	}
	
	public void onEnterKey( View v )
	{
		if ( code.length() != 0 )
		{
		}
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		
		setContentView( R.layout.main );
	}
	
}

