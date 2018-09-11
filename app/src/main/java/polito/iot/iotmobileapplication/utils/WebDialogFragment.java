package polito.iot.iotmobileapplication.utils;

/**
 * Created by user on 10/09/2018.
 */

    import android.app.Dialog;
    import android.app.DialogFragment;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.ViewGroup.LayoutParams;
    import android.webkit.WebView;
    import android.webkit.WebViewClient;
/** A DialogFragment that shows a web view. */
public class WebDialogFragment extends android.support.v4.app.DialogFragment {
    private static final String TAG = "WebDialogFragment";
    private static final String URL = "URL";
    private WebView mWebView;
    /**
     * Create a new WebDialogFragment to show a particular web page.
     *
     * @param url The URL of the content to show.
     */
    public static WebDialogFragment newInstance(
            String url) {
        WebDialogFragment f = new WebDialogFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWebView = new WebView(getActivity());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        String url = getArguments().getString(URL);
        mWebView.loadUrl(url);
        Log.d(TAG, "Loading web content from " + url);
        return mWebView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Ensure the dialog is fullscreen, even if the webview doesn't have its content yet.
        getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

}