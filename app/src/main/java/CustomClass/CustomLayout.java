package CustomClass;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.health.myapplication.R;

public class CustomLayout {
    public void passwordToggle(EditText editText, ImageView imageView) {
        imageView.setOnClickListener(view -> {
            if (editText.getTransformationMethod() == null) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            } else {
                editText.setTransformationMethod(null);
                imageView.setImageResource(R.drawable.ic_baseline_visibility_24);
            }
        });
    }
}
