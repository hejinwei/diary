package com.hejinwei.diary.helpmodel;

import com.hejinwei.diary.util.Constants;

public class Page {
	
	private int currentPageNum;//当前页
	
	private int totalPageNum;//总共多少页
	
	private int pageSize;//页面 大小
	
	private int totalCount;//总条数
	
	private int startIndex;//下标
	
	public int getStartIndex() {
        return (getCurrentPageNum()-1)*getPageSize();
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

	private int prePageNum;//上一页
	
	private int nextPageNum;//下一页
	
	public Page() {
		this.pageSize = Constants.DEFAULT_PAGE_SIZE;
	}
	
	public Page(int pageSize) {
	    this.pageSize = pageSize;
	}

	public int getCurrentPageNum() {
		return currentPageNum > 0 ? currentPageNum : 1;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getTotalPageNum() {
		totalPageNum = (getTotalCount() % getPageSize()) == 0? 
				(getTotalCount() / getPageSize()) : (getTotalCount() / getPageSize() + 1);
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPrePageNum() {
		if (currentPageNum <= 1) {
			return 1;
		} else {
			return currentPageNum-1;
		}
	}

	public int getNextPageNum() {
		if (currentPageNum < getTotalPageNum()) {
			return currentPageNum+1;
		} else {
			return currentPageNum;
		}
	}

	@Override
	public String toString()
	{
		return "Page{" +
				"currentPageNum=" + currentPageNum +
				", totalPageNum=" + totalPageNum +
				", pageSize=" + pageSize +
				", totalCount=" + totalCount +
				", startIndex=" + startIndex +
				", prePageNum=" + prePageNum +
				", nextPageNum=" + nextPageNum +
				'}';
	}
}