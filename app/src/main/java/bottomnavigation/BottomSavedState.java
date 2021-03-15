package bottomnavigation;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

class BottomSavedState extends View.BaseSavedState {
    int selectedPosition;

    BottomSavedState(Parcelable superState) {
        super(superState);
    }

    BottomSavedState(Parcel source) {
        super(source);
        selectedPosition = source.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(selectedPosition);
    }

    public static final Creator<BottomSavedState> CREATOR =
            new Creator<BottomSavedState>() {
                @Override
                public BottomSavedState createFromParcel(Parcel in) {
                    return new BottomSavedState(in);
                }

                @Override
                public BottomSavedState[] newArray(int size) {
                    return new BottomSavedState[size];
                }
            };
}