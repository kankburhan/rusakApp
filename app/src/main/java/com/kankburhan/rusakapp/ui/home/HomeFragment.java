package com.kankburhan.rusakapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kankburhan.rusakapp.R;
import com.kankburhan.rusakapp.databinding.FragmentHomeBinding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences mSettings = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        String contentProvUri = mSettings.getString("content-provider", "No content provider");

        Toast.makeText(getActivity(), contentProvUri, Toast.LENGTH_SHORT).show();

        TextView txResult = view.findViewById(R.id.txtResult);

        try {
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(getActivity().getContentResolver().openInputStream(Uri.parse(contentProvUri))));
            while (true) {
                String line1 = reader1.readLine();
                if (line1 != null) {
                    txResult.append(line1);
                } else {
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}