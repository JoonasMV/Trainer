package com.example.trainer.ui;

import androidx.recyclerview.widget.RecyclerView;

public abstract class UpdatableAdapter<T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {

    public abstract void update(T data);
}
