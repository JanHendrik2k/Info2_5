package prak5;

public class Element {

	private String ger, eng;
	private Element prev;
	private Element next;

	public Element() {
	}

	public String getGer() {
		return ger;
	}


	public void setGer(String ger) {
		this.ger = ger;
	}


	public String getEng() {
		return eng;
	}


	public void setEng(String eng) {
		this.eng = eng;
	}


	public Element getPrev() {
		return prev;
	}

	public void setPrev(Element prev) {
		this.prev = prev;
	}

	public Element getNext() {
		return next;
	}

	public void setNext(Element next) {
		this.next = next;
	}
}
