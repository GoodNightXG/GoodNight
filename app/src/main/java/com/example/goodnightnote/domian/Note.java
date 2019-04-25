package com.example.goodnightnote.domian;


/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:构建笔记本模型，封装笔记本的参数

 */
public class Note {
	public String content;
	public String data;
	public String id;
	public String title;

	public String getContent() {
		return this.content;
	}

	public String getTitle() {
		return this.title;
	}

	public String getdata() {
		return this.data;
	}

	public String getid() {
		return this.id;
	}

	public void setContent(String paramString) {
		this.content = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public void setdata(String paramString) {
		this.data = paramString;
	}

	public void setid(String paramString) {
		this.id = paramString;
	}
}
