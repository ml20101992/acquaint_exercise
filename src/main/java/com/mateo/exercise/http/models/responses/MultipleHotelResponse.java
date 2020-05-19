package com.mateo.exercise.http.models.responses;

import com.mateo.exercise.data.models.HotelModel;

import java.util.List;

public class MultipleHotelResponse {
    private List<HotelModel> data;
    private int page;
    private int pageTotal;

    public MultipleHotelResponse() {

    }

    public MultipleHotelResponse(List<HotelModel> data, int page, int pageTotal) {
        this.data = data;
        this.page = page;
        this.pageTotal = pageTotal;
    }

    public List<HotelModel> getData() {
        return data;
    }

    public void setData(List<HotelModel> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}
