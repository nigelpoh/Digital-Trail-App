package com.example.a1dproject.view;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.a1dproject.R;
import com.example.a1dproject.controller.AttractionActivity;
import com.example.a1dproject.controller.ExhibitScannerActivity;
import com.example.a1dproject.model.RecommendationCardSkeleton;
import com.example.a1dproject.model.RecommendationsAPIResponse;
import com.example.a1dproject.utils.MiscUtils;
import com.example.a1dproject.utils.NetworkUtils;

import java.util.Random;

public class RecommendedCards extends LinearLayout{

    private ProgressBar progressBar;
    private LinearLayout mainContent;
    private HorizontalScrollView mainContentSV;
    private RecommendationsCardAsyncTask asyncTask;

    public RecommendedCards(Context context)
    {
        super(context);
        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.recommendations_container, this, true);

        mainContent = findViewById(R.id.recommendations);
        mainContentSV = findViewById(R.id.recommendations_sv);
        progressBar = findViewById(R.id.progress_bar_recommendations);

        mainContent.setVisibility(View.GONE);
        mainContentSV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        if(NetworkUtils.isNetworkAvailable(context)) {
            asyncTask = new RecommendedCards.RecommendationsCardAsyncTask(context);
            asyncTask.execute();
        }else{
            MiscUtils.displayToast(context, "Not Connected to the Internet!");
            View recommendedCardView = LayoutInflater.from(context).inflate(R.layout.recommended_card, mainContent, false);
            RecommendationCardSkeleton recommendationCard = new RecommendationCardSkeleton();

            // Set up the RecommendationCardSkeleton views
            recommendationCard.setCard(recommendedCardView);
            recommendationCard.setDescription(recommendedCardView.findViewById(R.id.recommended_description));
            recommendationCard.setDistance(recommendedCardView.findViewById(R.id.recommended_distance));
            recommendationCard.setHeader(recommendedCardView.findViewById(R.id.recommended_header));
            recommendationCard.setImage(recommendedCardView.findViewById(R.id.recommended_image));
            recommendedCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, AttractionActivity.class);

                    intent.putExtra("vid", "2d7e8f1g5h6i3j4k");

                    view.getContext().startActivity(intent);
                }
            });
            LayoutParams layout = new LinearLayout.LayoutParams(
                    (int) MiscUtils.dpToPx(getResources(), 170),
                    (int) MiscUtils.dpToPx(getResources(), 220)
            );
            layout.setMargins(10,0,
                    (int) MiscUtils.dpToPx(getResources(), 60), 0
            );


            //Add the content
            recommendationCard.getCard().setLayoutParams(layout);
            recommendationCard.setDescriptionText("Escape the hustle and bustle of the city and relax in the tranquil surroundings of Jurong Lake Gardens.");
            recommendationCard.setDistanceText("No Network");
            recommendationCard.setHeaderText("Jurong Lake Gardens");
            recommendationCard.getImage().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.juronglakegardens));
            mainContent.addView(recommendationCard.getCard());

            progressBar.setVisibility(View.GONE);
            mainContent.setVisibility(View.VISIBLE);
            mainContentSV.setVisibility(View.VISIBLE);
        }
    }
    private class RecommendationsCardAsyncTask extends AsyncTask<Void, Void, RecommendationsAPIResponse> {
        private Context context;

        public RecommendationsCardAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mainContentSV.setVisibility(View.VISIBLE);
            mainContent.setVisibility(View.GONE);

        }

        @Override
        protected RecommendationsAPIResponse doInBackground(Void... voids) {
            return NetworkUtils.getRecommendationsData(context).join();
        }
        @Override
        protected void onPostExecute(RecommendationsAPIResponse recommendationsData) {
            super.onPostExecute(recommendationsData);

            for (int i = 0; i < recommendationsData.getVids().size(); i++) {
                View recommendedCardView = LayoutInflater.from(context).inflate(R.layout.recommended_card, mainContent, false);
                RecommendationCardSkeleton recommendationCard = new RecommendationCardSkeleton();

                // Set up the RecommendationCardSkeleton views
                recommendationCard.setCard(recommendedCardView);
                recommendationCard.setDescription(recommendedCardView.findViewById(R.id.recommended_description));
                recommendationCard.setDistance(recommendedCardView.findViewById(R.id.recommended_distance));
                recommendationCard.setHeader(recommendedCardView.findViewById(R.id.recommended_header));
                recommendationCard.setImage(recommendedCardView.findViewById(R.id.recommended_image));
                int finalI = i;
                recommendedCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, AttractionActivity.class);

                        intent.putExtra("vid", recommendationsData.getVids().get(finalI));

                        view.getContext().startActivity(intent);
                    }
                });
                LayoutParams layout = new LinearLayout.LayoutParams(
                        (int) MiscUtils.dpToPx(getResources(), 170),
                        (int) MiscUtils.dpToPx(getResources(), 220)
                );
                if(i == 0){
                    layout.setMargins(10,0,
                            (int) MiscUtils.dpToPx(getResources(), 60), 0
                    );
                }else if(i == recommendationsData.getVids().size() - 1){
                    layout.setMargins(0,0,
                            (int) MiscUtils.dpToPx(getResources(), 10), 0
                    );
                }else{
                    layout.setMargins(0,0,
                            (int) MiscUtils.dpToPx(getResources(), 60), 0
                    );
                }

                //Add the content
                recommendationCard.getCard().setLayoutParams(layout);
                recommendationCard.setDescriptionText(recommendationsData.getDescriptions().get(i));
                recommendationCard.setDistanceText("~" + String.valueOf(recommendationsData.getDistances().get(i)) + " km Away");
                recommendationCard.setHeaderText(recommendationsData.getHeaders().get(i));
                Glide.with(context).load("https://drive.google.com/uc?id=" + recommendationsData.getImageIds().get(i)).into(recommendationCard.getImage());
                mainContent.addView(recommendationCard.getCard());
            }

            Activity parentActivity = (Activity) context;
            parentActivity.findViewById(R.id.suggest_an_attraction_fab_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Random random = new Random();
                    int chosen_recommendation = random.nextInt(recommendationsData.getVids().size());
                    Intent intent = new Intent(context, AttractionActivity.class);
                    intent.putExtra("vid", recommendationsData.getVids().get(chosen_recommendation));
                    view.getContext().startActivity(intent);
                }
            });

            progressBar.setVisibility(View.GONE);
            mainContent.setVisibility(View.VISIBLE);
            mainContentSV.setVisibility(View.VISIBLE);
        }
    }
}
