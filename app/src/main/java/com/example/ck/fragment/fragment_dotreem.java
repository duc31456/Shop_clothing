package com.example.ck.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.R;
import com.example.ck.adapter.doBongDa_adapter;
import com.example.ck.adapter.doTreEm_adapter;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_dotreem extends Fragment {
    private RecyclerView recycler_treem;
    private doTreEm_adapter adapter;

    ArrayList<class_product> products_dotreem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dotreem, container, false);


        recycler_treem = view.findViewById(R.id.recycler_dotreem);
        adapter = new doTreEm_adapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycler_treem.setLayoutManager(gridLayoutManager);

        CallApiProductDoTreEm();
        return view;
    }

    private void CallApiProductDoTreEm()
    {
        CallApiUser.callApi.getApiProductDoTreEm().enqueue(new Callback<ArrayList<class_product>>() {
            @Override
            public void onResponse(Call<ArrayList<class_product>> call, Response<ArrayList<class_product>> response) {
                if (response.isSuccessful())
                {
                    products_dotreem = new ArrayList<>();
                    products_dotreem = response.body();
                    adapter.setdata(products_dotreem);
                    recycler_treem.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<class_product>> call, Throwable t) {
                Log.d("BBB",""+t);
            }
        });
    }
}
