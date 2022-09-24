package com.example.assignment_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpecialsFragment extends Fragment
{
    public SpecialsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_specials, ui, false);
    }
}