package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.models.SearchQueryCache;

import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/18/13
 * Time: 10:50 AM
 */
public class HistorySearchAdapter extends FixedBaseAdapter<SearchQueryCache>{
    public HistorySearchAdapter(List<SearchQueryCache> mData, Context context) {
        super(mData, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, SearchQueryCache model) {
        Holder h;
        if (convertView == null){
            convertView = inflateView(R.layout.query_history_row, parent);
            h = new Holder();
            h.query = (TextView) convertView.findViewById(R.id.query);
            convertView.setTag(h);
        }else h = (Holder) convertView.getTag();
        h.query.setText(model.getQuery());
        return convertView;
    }

    private class Holder{
        TextView query;
    }
}
