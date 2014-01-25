package com.tihonchik.lenonhonor360.models;

public class BlogEntry {
    private int id;
    private String created;
    private String title;
    private String blog;
    private String blogDate;
    
    public BlogEntry() {}

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
}
