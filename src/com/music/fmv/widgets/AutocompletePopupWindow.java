package com.music.fmv.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.music.fmv.R;
import com.music.fmv.adapters.AutocompleteAdapter;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 3:10 PM
 */
public class AutocompletePopupWindow extends PopupWindow{
    private final ArrayList<String> words;
    private final EditText editText;
    private final Context context;

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
        wordsListView.setAdapter(new AutocompleteAdapter(words, context));
        wordsListView.setOnItemClickListener(wordListener);
    }

    private AdapterView.OnItemClickListener wordListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String word = words.get(position);
            Toast.makeText(context, word, Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };
}
