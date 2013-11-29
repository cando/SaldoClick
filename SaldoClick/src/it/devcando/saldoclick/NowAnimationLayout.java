package it.devcando.saldoclick;

import it.devcando.saldoclick.R;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class NowAnimationLayout extends RelativeLayout implements OnGlobalLayoutListener {

	public NowAnimationLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayoutObserver();
	}

	public NowAnimationLayout(Context context) {
		super(context);
		initLayoutObserver();
	}

	private void initLayoutObserver() {
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {
		removeOnGlobalLayoutListener(this, this);

		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;

		boolean inversed = false;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);

			int[] location = new int[2];

			child.getLocationOnScreen(location);

			if (location[1] > heightPx) {
				break;
			}

			if (!inversed) {
				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
						R.anim.slide_up_left));
			} else {
				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
						R.anim.slide_up_right));
			}

			inversed = !inversed;
		}

	}
	
	@SuppressWarnings("deprecation")
	public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
	    if (Build.VERSION.SDK_INT < 16) {
	        v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
	    } else {
	        v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
	    }
	}

}