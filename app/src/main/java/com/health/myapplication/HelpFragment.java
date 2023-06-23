package com.health.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.DBHandler;
import Model.SlideItem;
import Slider.SlideAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DBHandler db;
    private EditText username, nama, nomor;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String data[];
        Button btnEdit;
        String dataEdit[] = new String[5];

        db = new DBHandler(getActivity());

        username = view.findViewById(R.id.editTextUsernameProfile);
        nama = view.findViewById(R.id.editTextNamaLengkapProfile);
        nomor = view.findViewById(R.id.editTextNoTeleponProfile);
        btnEdit = view.findViewById(R.id.buttonEdit);

        Intent intent = getActivity().getIntent();
        String varUsername = intent.getStringExtra("username");

        data = db.getData(varUsername);

        username.setText(varUsername);
        nama.setText(data[2]);
        nomor.setText(data[3]);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEdit[0] = data[0];
                dataEdit[1] = data[1];
                dataEdit[2] = nama.getText().toString();
                dataEdit[3] = nomor.getText().toString();
                dataEdit[4] = data[4];
                if(dataEdit[2].isEmpty() || dataEdit[3].isEmpty()){
                    Toast.makeText(getActivity(), "Isi form dengan benar", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getActivity(), "Data berhasil diperbaharui", Toast.LENGTH_SHORT).show();
                    db.updateDataById(Integer.parseInt(dataEdit[0]),dataEdit[1],dataEdit[2],dataEdit[3],dataEdit[4]);
                }
            }
        });
    }
}