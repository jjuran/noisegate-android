package com.metamage.noisegate;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;


final public class F
{
	
	static final int fadeDuration = 200;
	
	public static void fadeViewToAlpha( View v, int toAlpha )
	{
		AlphaAnimation anim = new AlphaAnimation( 1 - toAlpha, toAlpha );
		
		anim.setDuration( fadeDuration );
		
		v.setVisibility( toAlpha == 0 ? View.INVISIBLE : View.VISIBLE );
		
		v.startAnimation( anim );
	}
	
	public static void setKeyColor( Button key, int color )
	{
		GradientDrawable background = (GradientDrawable) key.getBackground();
		
		background.setColorFilter( new LightingColorFilter( color, 0 ) );
		
		key.setTextColor( color );
	}
	
}

