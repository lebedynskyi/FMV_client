package com.music.fmv.adapters;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.music.fmv.R;

import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 3:27 PM
 */
public class AutocompleteAdapter extends FixedBaseAdapter<String>{
    public AutocompleteAdapter(List<String> mData, Context context) {
        super(mData, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflateView(R.layout.autocmplete_row);
            holder.tv = (TextView) convertView.findViewById(R.id.word_text);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        holder.tv.setText(Html.fromHtml(getItem(position)));
        return convertView;
    }

    private class ViewHolder{
        TextView tv;
    }
}
