package com.example.mainproject.UtilityClasses;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

//This class is created to override getError method so that the entire EditText background will not be red.
public class CustomTextInputLayout extends TextInputLayout {
    private String TAG="MainActivity";
    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Log.d(TAG,"Drawable State changed");
        clearEditTextColorfilter();


    }
    @Override
    public void setError( CharSequence error) {
        super.setError(error);
        Log.d(TAG,"Set Error overriden");
        clearEditTextColorfilter();

    }

    private void clearEditTextColorfilter() {
        EditText editText = getEditText();

        if (editText != null) {
            Drawable background = editText.getBackground();
            if (background != null)
            {
                    background.clearColorFilter();
                    Log.d(TAG,"Color filter cleared");
            }
        }

    }
}
