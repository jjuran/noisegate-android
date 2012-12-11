package com.metamage.noisegate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class Key extends Button
{
	
	private boolean dragging    = false;
	private boolean outOfBounds = false;
	
	public Key( Context context, AttributeSet attrs, int defStyle )
	{
		super( context, attrs, defStyle );
	}
	
	public Key( Context context, AttributeSet attrs )
	{
		super( context, attrs );
	}
	
	public boolean isDragging()
	{
		return dragging;
	}
	
	private boolean hitFeedback()
	{
		return true;
	}
	
	private void beginDrag()
	{
		 setBackgroundResource( R.drawable.keypad_key_pressed );
		 
		 F.setKeyColor( this, Data.pressedColor );
	}
	
	private void updateDrag( float x, float y )
	{
		final boolean inside = getBackground().getBounds().contains( (int) x, (int) y );
		
		if ( inside == outOfBounds )
		{
			int color;
			int drawableResource;
			
			if ( inside )
			{
				color = Data.pressedColor;
				
				drawableResource = R.drawable.keypad_key_pressed;
			}
			else
			{
				color = Data.normalColor;
				
				drawableResource = R.drawable.keypad_key_normal;
			}
			
			setBackgroundResource( drawableResource );
			
			F.setKeyColor( this, color );
			
			outOfBounds = !inside;
		}
	}
	
	private void endDrag()
	{
		if ( !outOfBounds )
		{
			setBackgroundResource( R.drawable.keypad_key_normal );
			
			F.setKeyColor( this, Data.normalColor );
			 
		 	performClick();
		}
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		final int action = event.getActionMasked();
		
		switch ( action )
		{
			case MotionEvent.ACTION_DOWN:
				break;
			
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
				if ( dragging )
				{
					break;
				}
				
				// fall through
			
			default:
				return super.onTouchEvent( event );
		}
		
		final float x = event.getX();
		final float y = event.getY();
		
		if ( action == MotionEvent.ACTION_DOWN )
		{
			dragging    = true;
			outOfBounds = false;
			
			beginDrag();
			
			return hitFeedback();
		}
		else  // MOVE or UP
		{
			updateDrag( x, y );
			
			if ( action == MotionEvent.ACTION_UP )
			{
				endDrag();
				
				dragging = false;
			}
		}
		
		return true;
	}
}

