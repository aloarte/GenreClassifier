package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p4r4d0x.clasificadormusical.MainActivity;
import com.p4r4d0x.clasificadormusical.MusicGenres;
import com.p4r4d0x.clasificadormusical.R;

public class ResultFragment extends Fragment {

    private MusicGenres resultSongGenre = MusicGenres.NONE;

    private MainActivity parentActivity;


    private Button backButton;
    private TextView tvSongGenre;

    ResultFragment self = this;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_result, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {
        tvSongGenre = inflatedView.findViewById(R.id.tvSongGenre);
        backButton = inflatedView.findViewById(R.id.btnReclassifyAudio);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.doFragmentMain();

            }
        });

        if(resultSongGenre!=MusicGenres.NONE){
            tvSongGenre.setText(resultSongGenre.toString());
            changeColorByGenre(resultSongGenre,inflatedView);

        }
    }


    private void changeColorByGenre(MusicGenres resultSongGenre, View inflatedView) {
        LinearLayout llColored= inflatedView.findViewById(R.id.ll_square_genre);
        Button btnReclassifyAudio = inflatedView.findViewById(R.id.btnReclassifyAudio);

        switch(resultSongGenre.toString()){
            case "BigRoom":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreRed));
                break;
            case "Breaks":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenrePink));
                break;
            case "Dance":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenrePurple));
                break;
            case "DeepHouse":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreDeepPurple));
                break;
            case "DrumAndBass":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreIndigo));
                break;
            case "Dubstep":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreBlue));
                break;
            case "ElectroHouse":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreLightBlue));
                break;
            case "ElectronicaDowntempo":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreGrey));
                break;
            case "FunkRAndB":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreBrown));
                break;
            case "FutureHouse":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreGreen));
                break;
            case "GlitchHop":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreLightGreen));
                break;
            case "HardcoreHardTechno":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreLime));
                break;
            case "HardDance":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreAmber));
                break;
            case "HipHop":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreAmber));
                break;
            case "House":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreOrange));
                break;
            case "IndieDanceNuDusco":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreDeepOrange));
                break;
            case "Minimal":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreBrown));
                break;
            case "ProgresiveHouse":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreGrey));
                break;
            case "PsyTrance":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreGrey));
                break;
            case "ReggaeDub":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreBrown));
                break;
            case "TechHouse":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreTeal));
                break;
            case "Techno":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenreTeal));
                break;
            case "Trance":
                colorElements(llColored,btnReclassifyAudio,
                        getActivity().getResources().getColor(R.color.colorGenrePink));
                break;
            default:
                break;

        }


    }

    private void colorElements(LinearLayout llColored,Button btnReclassifyAudio, int colorPrimary) {
        llColored.setBackgroundColor(colorPrimary);
        btnReclassifyAudio.setBackgroundColor(colorPrimary);
    }

    public MainActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    public void setGenre(MusicGenres songGenre){
        this.resultSongGenre = songGenre;
    }

}
