package com.example.goodnightnote.domian;


import com.example.goodnightnote.R;

/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:构建笔记本模型，封装笔记本的参数

 */
public class Note {
	private String mContent;
	private String mData;
	private String mId;
	private String mTitle;
	private String mType;
	private String mUser;

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public String getmData() {
		return mData;
	}

	public void setmData(String mData) {
		this.mData = mData;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getmUser() {
		return mUser;
	}

	public void setmUser(String mUser) {
		this.mUser = mUser;
	}

}
