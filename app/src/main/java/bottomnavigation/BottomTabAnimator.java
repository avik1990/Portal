package bottomnavigation;

import androidx.annotation.NonNull;
import android.view.View;

class BottomTabAnimator {

    private static final int ANIMATION_DURATION = 200;

    static void animateTranslationY(@NonNull final View view, int to) {
        view.animate()
                .translationY(to)
                .setDuration(ANIMATION_DURATION);
    }
}
