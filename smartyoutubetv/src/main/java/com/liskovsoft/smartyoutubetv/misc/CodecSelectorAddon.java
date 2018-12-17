package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DataSource;
import com.liskovsoft.smartyoutubetv.injectors.WebViewJavaScriptInterface;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodecSelectorAddon {
    private static final String AVC_CODEC = "mp4";
    private static final String VP9_CODEC = "webm";
    private final Context mContext;
    private final WebViewJavaScriptInterface mJavaScriptInterface;
    private final Map<String, String> mCodecs;

    public CodecSelectorAddon(Context context, WebViewJavaScriptInterface javaScriptInterface) {
        mContext = context;
        mJavaScriptInterface = javaScriptInterface;
        mCodecs = new LinkedHashMap<>();
        mCodecs.put("Auto", "");
        mCodecs.put("AVC", VP9_CODEC);
        mCodecs.put("VP9", AVC_CODEC);
    }

    private class CodecSelectorDataSource implements DataSource {

        public CodecSelectorDataSource() {
        }

        @Override
        public Map<String, String> getDialogItems() {
            return mCodecs;
        }

        @Override
        public String getSelected() {
            // get codec from the settings
            return SmartPreferences.instance(mContext).getPreferredCodec();
        }

        @Override
        public void setSelected(String tag) {
            // update settings
            SmartPreferences.instance(mContext).setPreferredCodec(tag);
            // restart app
        }

        @Override
        public String getTitle() {
            return mContext.getString(R.string.codec_selector_title);
        }
    }

    public void run() {
        GenericSelectorDialog.create(mContext, new CodecSelectorDataSource());
    }

    public String getPreferredCodec() {
        return SmartPreferences.instance(mContext).getPreferredCodec();
    }
}