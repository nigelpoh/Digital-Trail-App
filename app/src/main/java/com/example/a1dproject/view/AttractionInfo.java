package com.example.a1dproject.view;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.a1dproject.R;
import com.example.a1dproject.controller.AttractionActivity;
import com.example.a1dproject.controller.ExhibitScannerActivity;
import com.example.a1dproject.controller.HomeActivity;
import com.example.a1dproject.model.AttractionsAPIResponse;
import com.example.a1dproject.model.RecommendationCardSkeleton;
import com.example.a1dproject.model.RecommendationsAPIResponse;
import com.example.a1dproject.utils.MiscUtils;
import com.example.a1dproject.utils.NetworkUtils;
import com.example.a1dproject.utils.UIUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class AttractionInfo extends LinearLayout{

    private ProgressBar progressBar;
    private androidx.coordinatorlayout.widget.CoordinatorLayout mainContent;
    private AttractionsInfoAsyncTask asyncTask;
    private Context context;
    private Activity activity;

    public AttractionInfo(Context context)
    {
        super(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    public void init(String vid)
    {
        mainContent = this.activity.findViewById(R.id.attraction_main);
        progressBar = this.activity.findViewById(R.id.progress_bar_attraction);

        mainContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if(NetworkUtils.isNetworkAvailable(context) && vid != "2d7e8f1g5h6i3j4k") {
            asyncTask = new AttractionInfo.AttractionsInfoAsyncTask(this.context, vid);
            asyncTask.execute();
        }else if(vid.equals("2d7e8f1g5h6i3j4k")){
            MiscUtils.displayToast(context, "No network connection! Please refresh app.");
            TextView header = mainContent.findViewById(R.id.attraction_header);
            TextView description = mainContent.findViewById(R.id.attraction_short_description);
            TextView suitable = mainContent.findViewById(R.id.attraction_most_suitable);
            TextView whats_there = mainContent.findViewById(R.id.attraction_whats_there);
            TextView recommendations = mainContent.findViewById(R.id.attraction_recommendations);
            ImageView background = mainContent.findViewById(R.id.attraction_image);
            TextView location_header = mainContent.findViewById(R.id.attraction_location).findViewById(R.id.location_header);
            TextView location_area = mainContent.findViewById(R.id.attraction_location).findViewById(R.id.location_area);

            header.setText(context.getResources().getString(R.string.offline_location));
            description.setText(context.getResources().getString(R.string.offline_description));
            suitable.setText(context.getResources().getString(R.string.offline_suitable));
            whats_there.setText(context.getResources().getString(R.string.offline_whatsThere));
            recommendations.setText(context.getResources().getString(R.string.offline_recommendations));
            background.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.juronglakegardens));
            location_header.setText(context.getResources().getString(R.string.offline_locality));
            location_area.setText(context.getResources().getString(R.string.offline_region));

            Activity parentActivity = (Activity) context;
            parentActivity.findViewById(R.id.go_to_location_fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri gmmIntentUri = Uri.parse("google.navigation:q=1.3473,103.7251");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    view.getContext().startActivity(mapIntent);
                }
            });

            progressBar.setVisibility(View.GONE);
            mainContent.setVisibility(View.VISIBLE);
        }else{
            MiscUtils.displayToast(context, "Not connected to the Internet!");
            context.startActivity(new Intent(context, HomeActivity.class));
        }
    }
    private class AttractionsInfoAsyncTask extends AsyncTask<Void, Void, AttractionsAPIResponse> {
        private Context context;
        private String vid;

        public AttractionsInfoAsyncTask(Context context, String vid) {
            this.context = context;
            this.vid = vid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mainContent.setVisibility(View.GONE);

        }

        @Override
        protected AttractionsAPIResponse doInBackground(Void... voids) {
            return NetworkUtils.getAttractionInfo(context, vid).join();
        }
        @Override
        protected void onPostExecute(AttractionsAPIResponse attractionData) {
            super.onPostExecute(attractionData);

            TextView header = mainContent.findViewById(R.id.attraction_header);
            TextView description = mainContent.findViewById(R.id.attraction_short_description);
            TextView suitable = mainContent.findViewById(R.id.attraction_most_suitable);
            TextView whats_there = mainContent.findViewById(R.id.attraction_whats_there);
            TextView recommendations = mainContent.findViewById(R.id.attraction_recommendations);
            ImageView background = mainContent.findViewById(R.id.attraction_image);
            TextView location_header = mainContent.findViewById(R.id.attraction_location).findViewById(R.id.location_header);
            TextView location_area = mainContent.findViewById(R.id.attraction_location).findViewById(R.id.location_area);

            header.setText(attractionData.getInfo().getTitle());
            description.setText(attractionData.getInfo().getDescription());
            suitable.setText(attractionData.getInfo().getSuitable());
            whats_there.setText(attractionData.getInfo().getMoreInfo());
            recommendations.setText(attractionData.getInfo().getRecommendations());
            Glide.with(context).load("https://drive.google.com/uc?id=" + attractionData.getInfo().getImageId()).into(background);
            location_header.setText(attractionData.getInfo().getLocation().getPlace());
            location_area.setText(attractionData.getInfo().getLocation().getRegion());

            Activity parentActivity = (Activity) context;
            parentActivity.findViewById(R.id.go_to_location_fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + String.valueOf(attractionData.getInfo().getLocation().getCoordinates().getLat()) + "," + String.valueOf(attractionData.getInfo().getLocation().getCoordinates().getLng()));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    view.getContext().startActivity(mapIntent);
                }
            });

            progressBar.setVisibility(View.GONE);
            mainContent.setVisibility(View.VISIBLE);
        }
    }
}
