package com.music.fmv.adapters;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.music.fmv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 3:27 PM
 */
public class AutocompleteAdapter extends FixedBaseAdapter<String>{
    private AdapterView.OnItemClickListener wordListener;

    public AutocompleteAdapter(List<String> mData, Context context, AdapterView.OnItemClickListener wordListener) {
        super(mData, context);
        this.wordListener = wordListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, String model) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflateView(R.layout.autocmplete_row);
            holder.tv = (TextView) convertView.findViewById(R.id.word_text);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        holder.tv.setText(Html.fromHtml(model));
        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordListener != null) wordListener.onItemClick(null, finalConvertView, position, getItemId(position));
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView tv;
    }
}
