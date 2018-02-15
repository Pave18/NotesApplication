package com.apalon.notes.adapters;

import android.view.View;

public interface OnRecycleViewItemClickListener<Model> {
    void onItemClick(View view, Model model);
}
