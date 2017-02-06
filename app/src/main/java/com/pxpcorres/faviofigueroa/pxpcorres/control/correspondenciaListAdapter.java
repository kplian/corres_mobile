package com.pxpcorres.faviofigueroa.pxpcorres.control;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pxpcorres.faviofigueroa.pxpcorres.R;

import java.util.List;

/**
 * Created by faviofigueroa on 5/22/16.
 */
public class correspondenciaListAdapter extends BaseAdapter {

    private Context context;
    private List<correspondencia> mCorrespondenciaList;

    public correspondenciaListAdapter(Context context, List<correspondencia> mCorrespondenciaList) {
        this.context = context;
        this.mCorrespondenciaList = mCorrespondenciaList;
    }

    @Override
    public int getCount() {
        return mCorrespondenciaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCorrespondenciaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.list_correspondencia,null);

        TextView numero = (TextView)v.findViewById(R.id.textViewNumero);
        TextView documento = (TextView)v.findViewById(R.id.textViewDocumento);
        TextView remitente = (TextView)v.findViewById(R.id.textViewRemitente);
        TextView cargo_remitente = (TextView)v.findViewById(R.id.textViewCargoRemitente);

        ImageView iconoCorrespondencia = (ImageView) v.findViewById(R.id.imageViewIconoCorrespondencia);

        Log.d("icono",mCorrespondenciaList.get(position).getEstado());
        if (mCorrespondenciaList.get(position).getEstado().equals("pendiente_recibido")){
            iconoCorrespondencia.setImageResource(R.mipmap.pendiente_recibido);
        }else if (mCorrespondenciaList.get(position).getEstado().equals("recibido")){
            iconoCorrespondencia.setImageResource(R.mipmap.recibido);
        }else if (mCorrespondenciaList.get(position).getEstado().equals("recibido_derivacion")){
            iconoCorrespondencia.setImageResource(R.mipmap.recibido_derivacion);
        }




        numero.setText(mCorrespondenciaList.get(position).getNumero());
        documento.setText(mCorrespondenciaList.get(position).getDocumento());
        remitente.setText(mCorrespondenciaList.get(position).getRemitente());
        cargo_remitente.setText(mCorrespondenciaList.get(position).getCargo_remitente());

        v.setTag(mCorrespondenciaList.get(position).getId_correspondencia());

        return v;
    }
}
