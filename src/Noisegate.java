package com.metamage.noisegate;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public final class Noisegate extends Activity implements Completion
{
	
	private static final int inputDelay   = 2000;
	private static final int fadeDuration = 200;
	
	private static final String urlBase = "http://pony.noisebridge.net/gate/unlock/?key=";
	
	private View liveKeypad;
	private View fakeKeypad;
	
	private Key eraseKey;
	private Key enterKey;
	
	private Teletype tty;
	
	private String code = "";
	
	private void unlockWithKey( CharSequence urlEncodedKey )
	{
		final String urlString = urlBase + urlEncodedKey;
		
		new GetAndDiscardUrlTask( this, urlString ).execute();
	}
	
	public void call( IOException exception )
	{
		fadeSubviews( fakeKeypad, 0 );
		
		tty.stopBlinking();
		
		tty.append( getString( R.string.complete ) );
		
		if ( exception != null )
		{
			tty.setText( getString( R.string.exception ) );
		}
	}
	
	private void fadeSubviews( View v, int toAlpha )
	{
		if ( v instanceof ViewGroup )
		{
			ViewGroup vg = (ViewGroup) v;
			
			final int n = vg.getChildCount();
			
			for ( int i = 0;  i < n;  ++i )
			{
				fadeSubviews( vg.getChildAt( i ), toAlpha );
			}
		}
		else
		{
			F.setKeyColor( (Button) v, getResources().getInteger( R.color.disabled_control ) );
			
			F.fadeViewToAlpha( v, toAlpha );
		}
	}
	
	private void updateText()
	{
		tty.startBlinking();
		
		tty.setText( getString( R.string.input ) + code );
	}
	
	public void onNumericKey( View v )
	{
		if ( code.length() == 0 )
		{
			F.fadeViewToAlpha( eraseKey, 1 );
			F.fadeViewToAlpha( enterKey, 1 );
		}
		
		Button key = (Button) v;
		
		code += key.getText();
		
		updateText();
	}
	
	public void onEraseKey( View v )
	{
		if ( code.length() != 0 )
		{
			code = code.substring( 0, code.length() - 1 );
			
			if ( code.length() == 0 )
			{
				F.fadeViewToAlpha( eraseKey, 0 );
				F.fadeViewToAlpha( enterKey, 0 );
			}
		}
		
		updateText();
	}
	
	public void onEnterKey( View v )
	{
		updateText();
		
		tty.input( "\n\n" );
		
		if ( code.length() != 0 )
		{
			fadeSubviews( fakeKeypad, 1 );
			
			unlockWithKey( code );
		}
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		
		final Resources resources = getResources();
		
		Data.normalColor  = resources.getInteger( R.color.normal_control  );
		Data.pressedColor = resources.getInteger( R.color.pressed_control );
		
		setContentView( R.layout.main );
		
		liveKeypad = findViewById( R.id.live_keypad );
		fakeKeypad = findViewById( R.id.fake_keypad );
		
		eraseKey = (Key) findViewById( R.id.erase );
		enterKey = (Key) findViewById( R.id.enter );
		
		eraseKey.setCounterpart( R.id._X );
		
		final TextView text = (TextView) findViewById( R.id.terminal );
		
		tty = new Teletype( text );
		
		tty.delayInput( inputDelay );
		
		tty.input( getString( R.string.input ) );
	}
	
}

