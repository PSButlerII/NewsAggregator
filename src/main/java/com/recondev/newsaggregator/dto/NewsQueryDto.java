package com.recondev.newsaggregator.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class NewsQueryDto {
    // The query to search for. In case of everything endpoint, the query is optional.
    //  The query is treated as if it is surrounded by wildcards: *query*. For example, q=tesla will search for tesla, teslas, tesla's, etc.
    @NotBlank(message = "Query is mandatory")
    private String query;
    private String searchIn;
    private String domains;
    private LocalDate from;
    private LocalDate to;
    private String language;
    private String sortBy;
    private Integer pageSize;
    private Integer page;
    private Object excludeDomains;


    public NewsQueryDto() {
    }

    public NewsQueryDto(String query, String searchIn, String domains, LocalDate from, LocalDate to, String language, String sortBy, Integer pageSize, Integer page, Object excludeDomains) {
        this.query = query;
        this.searchIn = searchIn;
        this.domains = domains;
        this.from = from;
        this.to = to;
        this.language = language;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
        this.page = page;
        this.excludeDomains = excludeDomains;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchIn() {
        return searchIn;
    }

    public void setSearchIn(String searchIn) {
        this.searchIn = searchIn;
    }

    public String getDomains() {
        return domains;
    }

    public void setDomains(String domains) {
        this.domains = domains;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Object getExcludeDomains() {
        return excludeDomains;
    }

    public void setExcludeDomains(Object excludeDomains) {
        this.excludeDomains = excludeDomains;
    }
}

