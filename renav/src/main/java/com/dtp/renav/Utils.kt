package com.dtp.renav

import android.content.res.Resources

/**
 * Created by ner on 9/18/17.
 */
fun dpToPx(dp: Int) = Math.round(dp * (Resources.getSystem().displayMetrics.densityDpi / 160f))
fun spToPx(sp: Int) = sp * Resources.getSystem().displayMetrics.scaledDensity