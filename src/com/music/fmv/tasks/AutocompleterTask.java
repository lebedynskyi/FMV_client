package com.music.fmv.tasks;

import android.content.Context;
import android.widget.EditText;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.widgets.AutocompletePopupWindow;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 3:08 PM
 */

public class AutocompleterTask extends BaseAsyncTask<ArrayList<String>> {
    private EditText editText;
    private SearchQueryCache.QUERY_TYPE queryType;

    public AutocompleterTask(Context context, boolean showDialog, EditText editText, SearchQueryCache.QUERY_TYPE queryType) {
        super(context, showDialog);
        this.editText = editText;
        this.queryType = queryType;
    }

    @Override
    protected ArrayList<String> doInBackground(Object... params) {
        //TODO select words and show poup window;
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        if (strings == null || strings.size() == 0 || isCancelled()) return;

        AutocompletePopupWindow window = new AutocompletePopupWindow(context, strings, editText);
        window.showAsDropDown(editText);
    }
}
