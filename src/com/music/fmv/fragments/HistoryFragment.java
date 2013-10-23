package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.music.fmv.R;
import com.music.fmv.adapters.HistorySearchAdapter;
import com.music.fmv.adapters.sectionadapter.SectionAdapter;
import com.music.fmv.adapters.sectionadapter.Sectionizer;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.widgets.RefreshableViewPager;

import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/22/13
 * Time: 12:07 PM
 */
public class HistoryFragment extends BaseFragment implements RefreshableViewPager.Refreshable {
    private HistorySearchAdapter adapter;
    private List<SearchQueryCache> historyItems;
    private SectionAdapter<SearchQueryCache> sectionAdapter;

    private boolean inited = false;

    @Override
    protected View createView(Bundle savedInstanceState) {
        View view = inflateView(R.layout.history_fragment);
        ListView lv = (ListView) view.findViewById(R.id.history_listview);
        historyItems = core.getCacheManager().getHistoryItems();
        adapter = new HistorySearchAdapter(historyItems, baseActivity);
        sectionAdapter = new SectionAdapter<SearchQueryCache>(baseActivity, adapter, R.layout.history_header, R.id.history_header_text, sectionizer);
        lv.setAdapter(sectionAdapter);
        lv.setOnItemClickListener(listListener);
        inited = true;
        return view;
    }

    @Override
    public void refresh() {
        if (!inited) return;
        historyItems.clear();
        historyItems.addAll(core.getCacheManager().getHistoryItems());
        adapter.notifyDataSetChanged();
        sectionAdapter.notifyDataSetChanged();
    }

    public interface HistoryFragmentCallback{
        public void onHistoryClicked(SearchQueryCache model);
    }


    private AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (baseActivity instanceof HistoryFragmentCallback){
                ((HistoryFragmentCallback) baseActivity).onHistoryClicked(adapter.getItem(sectionAdapter.getIndexForPosition(position)));
            }
        }
    };

    private Sectionizer<SearchQueryCache> sectionizer = new Sectionizer<SearchQueryCache>() {
        @Override
        public String getSectionTitleForItem(SearchQueryCache instance) {
            try {
                ModelType md = ModelType.valueOf(instance.getQueryType());
                switch (md){
                    case ALBUM:
                        return getString(R.string.albums) + ":";
                    case SONG:
                        return getString(R.string.songs) +  ":";
                    case ARTIST:
                        return getString(R.string.artist) + ":";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }
    };
}
