package com.p4r4d0x.genreclassifier.utils;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p4r4d0x.genreclassifier.R;
import com.p4r4d0x.genreclassifier.rest.classify.MusicGenre;

public class ElementGenre {

    public static boolean fillViewElementGenre(Activity context, View parentIncludeLayout, MusicGenre genre) {
        int backgroundColor = getElementGenreColor(context, genre);
        FrameLayout llGenreLayout;
        TextView tvGenreName;
        ImageView ivGenreColor;
        try {
            llGenreLayout = parentIncludeLayout.findViewById(R.id.element_genre_layout);
            tvGenreName = parentIncludeLayout.findViewById(R.id.element_genre_name);
            ivGenreColor = parentIncludeLayout.findViewById(R.id.iv_color_genre);
            tvGenreName.setText(genre.toString());
            ivGenreColor.setBackgroundColor(backgroundColor);

        } catch (Exception e) {
            return false;
        }

        return true;

    }

    private static int getElementGenreColor(Activity context, MusicGenre genre) {
        switch (genre) {
            case BigRoom:
                return context.getResources().getColor(R.color.colorGenreRed);
            case Breaks:
                return context.getResources().getColor(R.color.colorGenrePink);
            case Dance:
                return context.getResources().getColor(R.color.colorGenrePurple);
            case DeepHouse:
                return context.getResources().getColor(R.color.colorGenreDeepPurple);
            case DrumAndBass:
                return context.getResources().getColor(R.color.colorGenreIndigo);
            case Dubstep:
                return context.getResources().getColor(R.color.colorGenreBlue);
            case ElectroHouse:
                return context.getResources().getColor(R.color.colorGenreLightBlue);
            case ElectronicaDowntempo:
                return context.getResources().getColor(R.color.colorGenreGrey);
            case FunkRAndB:
                return context.getResources().getColor(R.color.colorGenreBrown);
            case FutureHouse:
                return context.getResources().getColor(R.color.colorGenreGreen);
            case GlitchHop:
                return context.getResources().getColor(R.color.colorGenreLightGreen);
            case HardcoreHardTechno:
                return context.getResources().getColor(R.color.colorGenreLime);
            case HardDance:
                return context.getResources().getColor(R.color.colorGenreAmber);
            case HipHop:
                return context.getResources().getColor(R.color.colorGenreAmber);
            case House:
            case IndieDanceNuDusco:
                return context.getResources().getColor(R.color.colorGenreDeepOrange);
            case Minimal:
                return context.getResources().getColor(R.color.colorGenreBrown);
            case ProgresiveHouse:
                return context.getResources().getColor(R.color.colorGenreGrey);
            case PsyTrance:
                return context.getResources().getColor(R.color.colorGenreGrey);
            case ReggaeDub:
                return context.getResources().getColor(R.color.colorGenreBrown);
            case TechHouse:
                return context.getResources().getColor(R.color.colorGenreTeal);
            case Techno:
                return context.getResources().getColor(R.color.colorGenreTeal);
            case Trance:
                return context.getResources().getColor(R.color.colorGenrePink);
            case NONE:
                return context.getResources().getColor(R.color.colorPrimaryMedium);
            default:
                return context.getResources().getColor(R.color.colorPrimaryMedium);

        }
    }
}
