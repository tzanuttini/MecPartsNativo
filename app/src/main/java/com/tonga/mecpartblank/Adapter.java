package com.tonga.mecpartblank;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Context mContext;
    private List<Maquina> maquinas;

    public Adapter(Context mContext, List<Maquina> maquinas) {
        this.mContext = mContext;
        this.maquinas = maquinas;
    }

    @Override
    public int getCount() {
        return maquinas.size();
    }

    @Override
    public Object getItem(int position) {
        return maquinas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.items_maquinas,null);
        Button btnMaquina = (Button) v.findViewById(R.id.btnMaquina);
        btnMaquina.setText(maquinas.get(position).getNombre());
        v.setTag(maquinas.get(position).getId());
        return v;
    }
}
