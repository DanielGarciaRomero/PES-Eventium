package com.eventium.eventium;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.eventium.eventium.TabFragments.RVAdapter;
import com.eventium.eventium.TabFragments.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MyDialogFragmentFollow extends DialogFragment {

    private RecyclerView rv;
    private RVAdapter adapter;
    private List<UserModel> mUserModel;
    ArrayList<Follow> follows;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        follows = new ArrayList<Follow>();

        follows = NavigationDrawerActivity.follows;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_usuarios, container, false);
        //getDialog().setTitle("Eventos");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        rv = (RecyclerView) rootView.findViewById(R.id.recyclerview4);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        mUserModel = new ArrayList<>();
        for (int i = 0; i < follows.size(); ++i) {
            Integer userid = follows.get(i).getFollowed();

            HTTPMethods httpMethods4 = new HTTPMethods(1);
            httpMethods4.setUsername(userid.toString());
            httpMethods4.ejecutarHttpAsyncTask();
            while (!httpMethods4.getFinished());
            Usuario user = httpMethods4.getUser();

            String encodedImage = user.getPic();
            byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap base64BitmapImage = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            mUserModel.add(new UserModel(base64BitmapImage, user.getUsername()));
        }

        adapter= new RVAdapter(false);
        adapter.setRVU(mUserModel);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(NavigationDrawerActivity.contexto, rv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String item = adapter.getItemRVU(position);
                        ((NavigationDrawerActivity)getActivity()).fromAsistentesToMostrarUsuario(item);
                        dismiss();
                        //Toast.makeText(NavigationDrawerActivity.contexto, item, Toast.LENGTH_LONG).show();
                        /*Fragment fragment;
                        if(!item.equals(myUsername)) fragment = new PerfilFragment();
                        else fragment = new MiPerfilFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("user", item);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();*/

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }
}
