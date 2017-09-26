package com.bhtask.nhs.backend.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object that represents a condition page in NHS index.
 *
 * @author samer
 *
 */
@Document(indexName = "nhs", type = "conditions")
public class Condition {

    public static final String FIELD__URL = "url";
    public static final String FIELD__TITLE = "title";
    public static final String FIELD__CONTENT = "content";

    @Id
    @Field(index = FieldIndex.no, type = FieldType.String)
    private final String url;

    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private final String title;

    @Field(index = FieldIndex.analyzed, analyzer = "english", type = FieldType.String)
    private final String content;

    private final int hashcode;

    @JsonCreator
    public Condition(@JsonProperty("url") String url, @JsonProperty("title") String title,
	    @JsonProperty("content") String content) {

	if (url == null)
	    throw new IllegalArgumentException("URL cannot be null");

	this.url = url;
	this.title = title;
	this.content = content;
	this.hashcode = url.hashCode();
    }

    public String getTitle() {
	return title;
    }

    public String getUrl() {
	return url;
    }

    public String getContent() {
	return content;
    }

    @Override
    public int hashCode() {
	return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Condition other = (Condition) obj;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Condition [title=");
	builder.append(title);
	builder.append(", url=");
	builder.append(url);
	builder.append("]");
	return builder.toString();
    }

}
