/* 
 * Copyright (C) 2012 Paul Burke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

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

/**
 * List adapter for Files.
 * 
 * @version 2013-06-25
 * 
 * @author paulburke (ipaulpro)
 * 
 */
public class FileListAdapter extends BaseAdapter {

	private final static int ICON_FOLDER = R.drawable.ic_folder;
	private final static int ICON_FILE = R.drawable.ic_file;

	private List<File> mFiles = new ArrayList<File>();
	private final LayoutInflater mInflater;

	public FileListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public ArrayList<File> getListItems() {
		return (ArrayList<File>) mFiles;
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

		holder.nameView.setText(file.getName());
		holder.iconView.setImageResource(file.isDirectory() ? ICON_FOLDER: ICON_FILE);

		return convertView;
	}

	static class ViewHolder {
		TextView nameView;
		ImageView iconView;
	}
}
