package com.metamage.noisegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.IOException;


public final class Noisegate extends Activity implements Completion
{
	
	private static final String urlBase = "http://pony.noisebridge.net/gate/unlock/?key=";
	
	private View clearKey;
	private View enterKey;
	
	private String code = "";
	
	private void unlockWithKey( CharSequence urlEncodedKey )
	{
		final String urlString = urlBase + urlEncodedKey;
		
		new GetAndDiscardUrlTask( this, urlString ).execute();
	}
	
	public void call( IOException exception )
	{
		if ( exception != null )
		{
		}
	}
	
	public void onNumericKey( View v )
	{
		if ( code.length() == 0 )
		{
			clearKey.setVisibility( View.VISIBLE );
			enterKey.setVisibility( View.VISIBLE );
		}
		
		Button key = (Button) v;
		
		code += key.getText();
	}
	
	public void onClearKey( View v )
	{
		if ( code.length() != 0 )
		{
			clearKey.setVisibility( View.INVISIBLE );
			enterKey.setVisibility( View.INVISIBLE );
			
			code = "";
		}
	}
	
	public void onEnterKey( View v )
	{
		if ( code.length() != 0 )
		{
			unlockWithKey( code );
		}
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		
		setContentView( R.layout.main );
		
		clearKey = findViewById( R.id.clear );
		enterKey = findViewById( R.id.enter );
	}
	
}

