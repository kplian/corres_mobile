package com.pxpcorres.faviofigueroa.pxpcorres.fragments;

import com.pxpcorres.faviofigueroa.pxpcorres.control.ControlCorrespondencia;
import com.pxpcorres.faviofigueroa.pxpcorres.control.correspondencia;
import com.pxpcorres.faviofigueroa.pxpcorres.control.correspondenciaListAdapter;


import android.content.Context;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pxpcorres.faviofigueroa.pxpcorres.R;
import com.pxpcorres.faviofigueroa.pxpcorres.pxpservice_android.PxpEncryption;
import com.pxpcorres.faviofigueroa.pxpcorres.pxpservice_android.PxpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CorrespondenciaRecibidaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CorrespondenciaRecibidaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private correspondenciaListAdapter adapter;
    private List<correspondencia> mCorrespondenciaList;


    ListView listViewCorrespondencia;



    private ControlCorrespondencia mcorrespondencia;



    private PxpService pxpService = new PxpService();
    public Context contexGlobal;


    public CorrespondenciaRecibidaFragment() {
        // Required empty public constructor
    }


    String usuario;
    String pwd;
    String host;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        pwd = getArguments().getString("pwd");
        usuario = getArguments().getString("usuario");
        host = getArguments().getString("host");
        Log.d("strtext", pwd);
        Log.d("usuario", usuario);
        Log.d("host", host);


        final View view = inflater.inflate(R.layout.fragment_correspondencia_recibida, container, false);






        listViewCorrespondencia = (ListView)view.findViewById(R.id.listViewCorrespondencia);
        mCorrespondenciaList = new ArrayList<>();



//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));
//        mCorrespondenciaList.add(new correspondencia(1,"cob/asd/asdasd","carta","favio figuer","asdads","sad"));





        try {

            //Log.d("pwd de sqlite",pwd);


//            p {"start":"0","limit":"50","sort":"id_correspondencia","dir":"DESC","interface":"emitida"}
//            x../../sis_correspondencia/control/Correspondencia/listarCorrespondenciaRecibida
            pxpService.setUsuario(usuario);
            pxpService.setPwd(pwd);
            pxpService.dominioDeServicio(host, "correspondencia", "Correspondencia", "listarCorrespondenciaRecibida");
            RequestBody formBody = new FormBody.Builder()
                    .add("start", "0")
                    .add("limit", "50")
                    .add("sort", "id_correspondencia")
                    .add("dir", "desc")
                    .add("interface", "emitida")
                    .build();
            pxpService.setParametros(formBody);
            Call call = pxpService.iniciar(getContext(),false);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("error", String.valueOf(call));
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //Log.e(TAG,response.body().string());
                        String jsonData = response.body().string();
                        //Log.d("json", jsonData);
                        if (response.isSuccessful()) {
                            Log.d("json", jsonData);

                            // mcorrespondencia = getCurrentDetails(jsonData);

                            JSONObject json_pxp = new JSONObject(jsonData);
                            JSONArray datos = json_pxp.getJSONArray("datos");

                            for (int i = 0; i < datos.length(); i++) {
                                //Log.d("Type", datos.getString(i));

                                Address current = new Address(Locale.getDefault());
                                JSONObject item = datos.getJSONObject(i);
                                Log.d("item", String.valueOf(item));

                                mCorrespondenciaList.add(new correspondencia(item.getInt("id_correspondencia"),item.getString("numero"),item.getString("desc_documento"),item.getString("desc_funcionario"),item.getString("desc_uo"),item.getString("estado")));


                            }



                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {




                                    adapter = new correspondenciaListAdapter(getContext().getApplicationContext(),mCorrespondenciaList);
                                    listViewCorrespondencia.setAdapter(adapter);




                                }
                            });







                        } else {
                            pxpService.setMensaje(pxpService.ExisteError(jsonData));

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), pxpService.getMensaje(), Toast.LENGTH_LONG).show();
                                }
                            });


                        }

                    } catch (IOException e) {
                        Log.e("", "ERROR", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }




            });


        } catch (Exception e) {
            e.printStackTrace();
        }





        listViewCorrespondencia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   Log.d("id", view.getTag().toString());
               }
         });


        listViewCorrespondencia.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                this.currentScrollState = scrollState;
                this.isScrollCompleted();

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;

            }


            private void isScrollCompleted() {
                Log.d("12","12");
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /** To do code here*/
                Log.d("asd","sad");



                }
            }

        });


        return view;


    }


    public void listarCorrespondencia(){


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
