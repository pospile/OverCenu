package cz.pospichal.overcenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cz.pospichal.overcenu.Adapters.PopularAdapter;
import cz.pospichal.overcenu.Data.PopularData;


public class PopularFragment extends Fragment implements PopularAdapter.MyClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;

    public PopularFragment() {
        // Required empty public constructor
    }


    public static PopularFragment newInstance() {
        PopularFragment fragment = new PopularFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View returnable = inflater.inflate(R.layout.fragment_popular, container, false);


        mRecyclerView = (RecyclerView) returnable.findViewById(R.id.popular);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new StaggeredGridLayoutManager(1,1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<PopularData> data = new ArrayList<PopularData>();
        data.add(new PopularData("tatranka", "Globus", new String[0], 6, 0));
        data.add(new PopularData("mléko", "Tesco", new String[0], 20, 0));
        data.add(new PopularData("máslo", "Billa", new String[0], 25, 0));
        data.add(new PopularData("rohlík", "Penny", new String[0], 2, 0));

        mAdapter = new PopularAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


        return returnable;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
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

    @Override
    public void onItemClick(int position, View v) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
