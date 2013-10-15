package com.music.fmv.models.dbmodels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 11:00 AM
 */
@DatabaseTable(tableName = "searh_query_cache")
public class SearchQueryCache extends BaseDBModel{
    @DatabaseField()
    private String query;

    @DatabaseField()
    private String queryType;

    public SearchQueryCache(){}

    public SearchQueryCache(String query, ModelType queryType) {
        this.query = query;
        this.queryType = queryType.name();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(ModelType queryType) {
        this.queryType = queryType.name();
    }


    @Override
    public String toString() {
        return "id = " + getId() + ", Query = " +  query;
    }
}
