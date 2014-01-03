package com.music.fms.fragments;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.music.fms.R;
import com.music.fms.core.BaseFragment;
import com.music.fms.core.ISearchFragment;
import com.music.fms.models.ModelType;
import com.music.fms.models.SearchQueryCache;
import com.music.fms.tasks.AutocompleterTask;
import com.music.fms.widgets.AutocompletePopupWindow;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/21/13
 * Time: 12:34 PM
 */
public abstract class BaseSearchFragment extends BaseFragment implements ISearchFragment {
    private AutocompleterTask currentAutocompleterTask;
    private AutocompletePopupWindow autocompleteWindow;
    private EditText tv;

    @Override
    public void search(SearchQueryCache model) {
        String query = model.getQuery();

        //To prevent calling the autocompleter
        tv.setTag(new Integer(1));

        tv.setText(query);
        processSearch(query, null);
    }

    protected View createSearchHeader(TextView.OnEditorActionListener searchListener, ModelType autocmpleterType) {
        View v = inflateView(R.layout.search_header);
        tv = ((EditText) v.findViewById(R.id.search_field));
        tv.setOnEditorActionListener(searchListener);
        if (autocmpleterType != null){
            tv.addTextChangedListener(new AutocompleteWatcher(tv, autocmpleterType));
        }
        return v;
    }

    protected abstract void processSearch(String query, final Integer page);

    private class AutocompleteWatcher implements TextWatcher {
        private EditText sourceEditText;
        private ModelType queryType;

        private AutocompleteWatcher(EditText sourceEditText, ModelType queryType) {
            this.sourceEditText = sourceEditText;
            this.queryType = queryType;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*nothing*/}
        @Override public void afterTextChanged(Editable s) {/*nothing*/}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ((sourceEditText.getTag() != null) && (sourceEditText.getTag() instanceof Integer) && (((Integer)sourceEditText.getTag()) == 1) ){
                sourceEditText.setTag( null);
                return;
            }

            if (currentAutocompleterTask != null){
                currentAutocompleterTask.cancel(true);
                currentAutocompleterTask = null;
            }

            if (autocompleteWindow != null){
                autocompleteWindow.dismiss();
                autocompleteWindow = null;
            }

            currentAutocompleterTask = new AutocompleterTask(baseActivity, false, queryType, sourceEditText.getText().toString()){
                @Override
                protected void onPostExecute(ArrayList<String> strings) {
                    super.onPostExecute(strings);
                    if (strings == null || strings.isEmpty() || isCancelled()) return;

                    autocompleteWindow = new AutocompletePopupWindow(context, strings, sourceEditText);
                    autocompleteWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    autocompleteWindow.showAsDropDown(sourceEditText, 5, 5);
                }
            };
            currentAutocompleterTask.execute();
        }
    }
}
