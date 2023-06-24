package com.health.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import RecyclerView.MyAdapterListReservasi;

import Database.DBHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<String> varNama, varTanggal, varDokter, varAntrian;
    DBHandler db;
    MyAdapterListReservasi adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//      Instance Object
        db = new DBHandler(getActivity());
        varNama = new ArrayList<>();
        varTanggal = new ArrayList<>();
        varDokter = new ArrayList<>();
        varAntrian = new ArrayList<>();

//      Get Data From Layout
        recyclerView = view.findViewById(R.id.recyclerViewPendapatan);
        adapter = new MyAdapterListReservasi(getActivity(),varNama,varTanggal,varDokter,varAntrian);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        displayData();

    }

    //  Munculkan semua data dari database kedalam tampilan
    private void displayData(){
        Cursor cursor = db.getDataReservasi();
        if(cursor.getCount()==0){
            Toast.makeText(getActivity(), "Tidak ada Data", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while(cursor.moveToNext()){
                varNama.add(cursor.getString(1));
                varTanggal.add(cursor.getString(4));
                varDokter.add(cursor.getString(5));
                varAntrian.add(cursor.getString(7));
            }
        }
    }
}