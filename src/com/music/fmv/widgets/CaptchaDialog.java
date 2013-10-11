package com.music.fmv.widgets;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.music.fmv.R;
import com.music.fmv.core.Core;
import com.music.fmv.models.notdbmodels.Captcha;
import com.music.fmv.utils.ViewUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * User: vitaliylebedinskiy
 * Date: 8/13/13
 * Time: 10:25 AM
 */
public class CaptchaDialog extends DialogFragment {
    private View mainView;
    private Button cancel;
    private Button apply;
    private EditText captchaField;
    private ImageView captchaImage;

    private CaptchaCallBack captchaCallBack;
    private Captcha captcha;

    public CaptchaDialog() {
        setStyle(STYLE_NO_TITLE, 0);
    }

    public void setCaptchaCallBack(CaptchaCallBack captchaCallBack) {
        this.captchaCallBack = captchaCallBack;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.captcha_dialog, container, false);
        cancel = (Button) mainView.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(buttonListener);
        apply.setOnClickListener(buttonListener);
        apply = (Button) mainView.findViewById(R.id.apply_btn);
        captchaImage = (ImageView) mainView.findViewById(R.id.captcha_image);
        captchaField = (EditText) mainView.findViewById(R.id.captcha_field);
        showCaptcha();
        return mainView;
    }

    private void showCaptcha() {
        if (captcha == null) return;
        ImageLoader.getInstance().displayImage(captcha.getUrl(), captchaImage, Core.getInstance(getActivity()).getNotCachedOptions());
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_btn:
                    if (captchaCallBack != null) captchaCallBack.captchaCanceled();
                    break;
                case R.id.apply_btn:
                    if (TextUtils.isEmpty(captchaField.getText())) {
                        captchaField.setError(getActivity().getString(R.string.field_cannot_be_empty));
                        return;
                    }
                    if (captchaCallBack != null) {
                        captchaCallBack.captchaEntered(captchaField.getText().toString());
                    }
            }
            ViewUtils.hideSoftKeyboard(getActivity());
            try {
                dismiss();
            } catch (Exception ignored) {
            }
        }
    };

    public static interface CaptchaCallBack {
        public void captchaEntered(String captcha);

        public void captchaCanceled();
    }
}
