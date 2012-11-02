package com.metamage.noisegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.IOException;


public final class Noisegate extends Activity implements Completion
{
	
	private static final int fadeDuration = 200;
	
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
	
	private void fade( View v, int toAlpha )
	{
		AlphaAnimation anim = new AlphaAnimation( 1 - toAlpha, toAlpha );
		
		anim.setDuration( fadeDuration );
		
		v.setVisibility( toAlpha == 0 ? View.INVISIBLE : View.VISIBLE );
		
		v.startAnimation( anim );
	}
	
	public void onNumericKey( View v )
	{
		if ( code.length() == 0 )
		{
			fade( clearKey, 1 );
			fade( enterKey, 1 );
		}
		
		Button key = (Button) v;
		
		code += key.getText();
	}
	
	public void onClearKey( View v )
	{
		if ( code.length() != 0 )
		{
			fade( clearKey, 0 );
			fade( enterKey, 0 );
			
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

