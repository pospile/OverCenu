package cz.pospichal.overcenu;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanFragment extends Fragment implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private boolean flash = false;

    private OnFragmentInteractionListener mListener;

    ArrayList array = new ArrayList();

    public ScanFragment() {
        // Required empty public constructor
    }

    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity());
        mScannerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mScannerView.setFlash(!flash);
                flash = !flash;
                return false;
            }
        });
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setAutoFocus(true);
    }

    @Override
    public void handleResult(final Result rawResult) {
        /*
        Toast.makeText(getActivity(), "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
        */



        array.add(array.size(), rawResult.getContents());

        final ArrayList<String> returnable = new ArrayList<String>();
        new MaterialDialog.Builder(getActivity())
                .title("Vyhledat produkt")
                .items(array)
                .icon(getResources().getDrawable(R.drawable.ic_barcode))
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        for(int i = 0; i < which.length; i++)
                        {
                            returnable.add(i, array.get(which[i]).toString());
                        }
                        Log.e("select", returnable.toString());
                        onScanDone(returnable);
                        return true;
                    }
                })
                .widgetColor(getResources().getColor(R.color.colorPrimary))
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mScannerView.resumeCameraPreview(ScanFragment.this);
                    }
                })
                .positiveText("Vyhledat produkty")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.e("THINGS", returnable.toString());
                    }
                })
                /*
                .neutralText("Pokračovat ve skenu")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mScannerView.resumeCameraPreview(ScanFragment.this);
                    }
                })
                */
                .show();


        //onScanDone(rawResult.getContents());

        /*
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanFragment.this);
            }
        }, 2000);
        */
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onScanDone(ArrayList<String> list) {
        if (mListener != null) {
            if (list.size() != 0)
            {
                mListener.onFragmentInteraction(list);
                mScannerView.stopCamera();
            }
            else
            {
                mScannerView.resumeCameraPreview(ScanFragment.this);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(List<String> search);
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
        mScannerView.stopCamera();
    }
}