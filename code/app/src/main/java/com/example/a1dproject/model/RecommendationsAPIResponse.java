package com.example.a1dproject.model;

import java.util.List;

public class RecommendationsAPIResponse {
    private List<String> vids;
    private List<String> imageIds;
    private List<String> descriptions;
    private List<String> headers;
    private List<Integer> distances;
    private Integer status;
    private String error_message;

    public void setVids(List<String> vids) {
        this.vids = vids;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setDistances(List<Integer> distances) {
        this.distances = distances;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setErrorMessage(String error_message) {
        this.error_message = error_message;
    }

    public List<String> getVids() {
        return vids;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<Integer> getDistances() {
        return distances;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return error_message;
    }
}
