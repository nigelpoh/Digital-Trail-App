package com.example.a1dproject.model;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class RecommendationCardSkeleton {
    private View card;
    private ShapeableImageView image;
    private TextView header;
    private TextView description;
    private TextView distance;

    public View getCard(){
        return card;
    }

    public void setCard(View card) {
        this.card = card;
    }

    public ShapeableImageView getImage(){
        return image;
    }

    public void setImage(ShapeableImageView image) {
        this.image = image;
    }
    public void setHeader(TextView header) {
        this.header = header;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public void setDistance(TextView distance) {
        this.distance = distance;
    }

    public void setHeaderText(String header) {
        this.header.setText(header);
    }

    public void setDescriptionText(String description) {
        this.description.setText(description);
    }

    public void setDistanceText(String distance) {
        this.distance.setText(distance);
    }
}