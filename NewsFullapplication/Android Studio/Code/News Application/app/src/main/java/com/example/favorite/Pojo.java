package com.example.favorite;

public class Pojo {
	
	private int id;
	private String  CatId;
	private String  CId;
	private String CategoryName;
 	private String NewsHeading;
	private String NewsImage;
	private String NewsDesc;
	private String NewsDate;
	 
	
	public Pojo()
	{
		
	}
	
	public Pojo(String CatId)
	{
		this.CatId=CatId;
	}
	
	public Pojo(String catid,String cid,String categoryname,String newsheading,String newsimage,String newsdesc,String newsdate)
	{
		this.CatId=catid;
		this.CId=cid;
		this.CategoryName=categoryname;
 		this.NewsHeading=newsheading;
		this.NewsImage=newsimage;
		this.NewsDesc=newsdesc;
		this.NewsDate=newsdate;
		 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getCatId() {
		return CatId;
	}

	public void setCatId(String catid) {
		this.CatId = catid;
	}
	
	public String getCId() {
		return CId;
	}

	public void setCId(String cid) {
		this.CId = cid;
	}
	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	 
	public String getNewsHeading() {
		return NewsHeading;
	}

	public void setNewsHeading(String newsheading) {
		this.NewsHeading = newsheading;
	}
	
	public String getNewsImage() {
		return NewsImage;
	}

	public void setNewsImage(String newsimage) {
		this.NewsImage = newsimage;
	}
	
	 
	public String getNewsDesc() {
		return NewsDesc;
	}

	public void setNewsDesc(String newsdesc) {
		this.NewsDesc = newsdesc;
	}
	
	public String getNewsDate() {
		return NewsDate;
	}

	public void setNewsDate(String newsdate) {
		this.NewsDate = newsdate;
	}

}
