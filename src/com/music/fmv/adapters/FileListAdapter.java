package com.music.fmv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fmv.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends BaseAdapter {

	private final static int ICON_FOLDER = R.drawable.ic_folder;
	private final static int ICON_FILE = R.drawable.ic_file;

	private List<File> mFiles = new ArrayList<File>();
	private final LayoutInflater mInflater;

	public FileListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setListItems(List<File> files) {
		this.mFiles = files;
		notifyDataSetChanged();
	}

	@Override
    public int getCount() {
		return mFiles.size();
	}

	public void add(File file) {
		mFiles.add(file);
		notifyDataSetChanged();
	}

	public void clear() {
		mFiles.clear();
		notifyDataSetChanged();
	}

	@Override
    public Object getItem(int position) {
		return mFiles.get(position);
	}

	@Override
    public long getItemId(int position) {
		return position;
	}

    @Override
    public boolean isEnabled(int position) {
        final File file = mFiles.get(position);
        return file.isDirectory();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
            convertView = mInflater.inflate(R.layout.file, parent, false);
			holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.file_name);
            holder.iconView = (ImageView) convertView.findViewById(R.id.file_icon);
            convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final File file = mFiles.get(position);

        if (!file.isDirectory()){
            holder.iconView.setEnabled(false);
            holder.nameView.setEnabled(false);
        } else {
            holder.iconView.setEnabled(true);
            holder.nameView.setEnabled(true);
        }

		holder.nameView.setText(file.getName());
		holder.iconView.setImageResource(file.isDirectory() ? ICON_FOLDER: ICON_FILE);

		return convertView;
	}

	static class ViewHolder {
		TextView nameView;
		ImageView iconView;
	}
}
