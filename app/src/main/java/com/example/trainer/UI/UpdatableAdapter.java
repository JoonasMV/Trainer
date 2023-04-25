package com.example.trainer.UI;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public abstract class UpdatableAdapter<T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {

    public abstract void update(T data);
}
