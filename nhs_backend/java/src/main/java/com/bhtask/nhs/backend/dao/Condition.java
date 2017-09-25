package com.bhtask.nhs.backend.dao;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object that represents a condition page in NHS index.
 *
 * @author samer
 *
 */
public class Condition {

    private final URI url;
    private final String title;
    private final String content;
    private final int hashcode;

    @JsonCreator
    public Condition(@JsonProperty("url") URI url, @JsonProperty("title") String title,
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

    public URI getUrl() {
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
