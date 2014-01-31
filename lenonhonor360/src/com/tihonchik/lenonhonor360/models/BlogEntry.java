package com.tihonchik.lenonhonor360.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tihonchik.lenonhonor360.util.AppUtils;

public class BlogEntry implements Serializable {
	
    private static final long serialVersionUID = -2163051469151804394L;

	private int id;
	private String created;
	private String title;
	private String blog;
	private String blogDate;
	private List<String> images = new ArrayList<String>();

	public BlogEntry() {
		this.created = AppUtils.getCurrentTimeStamp();
	}

	public BlogEntry(int id) {
		this.id = id;
		this.created = AppUtils.getCurrentTimeStamp();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getBlogDate() {
		return blogDate;
	}

	public void setBlogDate(String blogDate) {
		this.blogDate = blogDate;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
}
