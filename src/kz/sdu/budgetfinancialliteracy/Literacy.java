package kz.sdu.budgetfinancialliteracy;

public class Literacy {

	private String text;
	private boolean readed;

	public Literacy(String text, boolean readed) {
		this.text = text;
		this.readed = readed;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}
}
