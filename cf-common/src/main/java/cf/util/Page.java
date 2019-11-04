package cf.util;

import java.util.List;

public class Page<T> {
	private int pageno;
	private int pagesize;
	private int totalno;
	private int totalsize;
	private List<T> data;

	public Page(int pageno, int pagesize) {
		if (pageno < 1) {
			this.pageno = 1;
		} else {
			this.pageno = pageno;
		}
		if (pagesize < 1) {
			this.pagesize = 10;
		} else {
			this.pagesize = pagesize;
		}
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalno() {
		return totalno;
	}

	public void setTotalno(int totalno) {
		this.totalno = totalno;
	}

	public int getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
		this.totalno = totalsize % pagesize == 0 ? totalsize / pagesize : totalsize / pagesize + 1;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getStartIndex() {
		return (pageno - 1) * pagesize;
	}
}
