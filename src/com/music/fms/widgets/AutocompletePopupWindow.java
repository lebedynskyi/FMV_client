package com.music.fms.widgets;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.music.fms.R;
import com.music.fms.adapters.AutocompleteAdapter;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 3:10 PM
 */
public class AutocompletePopupWindow extends PopupWindow implements View.OnTouchListener{
    private final ArrayList<String> words;
    private final EditText editText;
    private final Context context;
    private AutocompleteAdapter wordsAdapter;

    public AutocompletePopupWindow(Context context, ArrayList<String> words, EditText editText) {
        super(context);
        this.context = context;
        this.words = words;
        this.editText = editText;
        initPopUp();
    }

    private void initPopUp() {
        View v = LayoutInflater.from(context).inflate(R.layout.autocomplete_window, null, false);
        setContentView(v);
        ListView wordsListView = (ListView) v.findViewById(R.id.words_list);
        wordsAdapter = new AutocompleteAdapter(words, context, wordListener);
        wordsListView.setAdapter(wordsAdapter);

        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(this);
    }

    private AdapterView.OnItemClickListener wordListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String word = words.get(position);
            editText.setTag(1);
            String text = Html.fromHtml(word).toString();
            editText.setText(text);
            editText.setSelection(text.length());
            dismiss();
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            dismiss();
            return true;
        }
        return false;
    }
}
