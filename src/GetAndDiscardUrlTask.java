package com.metamage.noisegate;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


public final class GetAndDiscardUrlTask extends AsyncTask< Void, Integer, Void >
{
	private static boolean busy;
	
	private String itsUrlString;
	
	URL itsUrl;
	
	Completion itsCompletion;
	
	IOException itsSavedException;
	
	public GetAndDiscardUrlTask( Completion completion, String urlString )
	{
		if ( busy )
		{
			cancel( false );  // no need to interrupt, we never enter doInBackground()
			
			return;
		}
		
		busy = true;
		
		itsCompletion = completion;
		
		itsUrlString = urlString;
	}
	
	protected Void doInBackground( Void... v )
	{
		try
		{
			final URL url = new URL( itsUrlString );
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			try
			{
				InputStream input = urlConnection.getInputStream();
				
				try
				{
					String connectedHost = urlConnection.getURL().getHost();
					
					if ( !url.getHost().equals( connectedHost ) )
					{
						throw new ProtocolException( "HTTP redirect to " + connectedHost + " not allowed" );
					}
					
					int contentLength = urlConnection.getContentLength();
				}
				finally
				{
					input.close();
				}
			}
			finally
			{
				urlConnection.disconnect();
			}
		}
		catch ( IOException e )
		{
			itsSavedException = e;
		}
		
		return (Void) null;
	}
	
	protected void onProgressUpdate( Integer... params )
	{
	}
	
	protected void onPostExecute( Void v )
	{
		busy = false;
		
		itsCompletion.call( itsSavedException );
	}
}

