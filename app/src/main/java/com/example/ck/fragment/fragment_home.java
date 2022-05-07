package com.example.ck.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.MainActivity;
import com.example.ck.R;
import com.example.ck.adapter.home_adapter;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.login_activity;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_home extends Fragment {
    private RecyclerView recyclerView;
    private home_adapter adapter;
    ViewFlipper flipper;

    ArrayList<class_product> products;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        flipper = view.findViewById(R.id.view_flipper);

        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        CallApiProduct();
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new home_adapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        CallApiProduct();
        return view;
    }


    private void CallApiProduct()
    {
        CallApiUser.callApi.getApiProduct().enqueue(new Callback<ArrayList<class_product>>() {
            @Override
            public void onResponse(Call<ArrayList<class_product>> call, Response<ArrayList<class_product>> response) {
                if (response.isSuccessful())
                {
                    products = new ArrayList<>();
                    products = response.body();
                    adapter.setdata(products);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<class_product>> call, Throwable t) {
                Log.d("BBB",""+t);
            }
        });
    }
}
