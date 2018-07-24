package kz.sdu.budgetfinancialliteracy;

public class ForDB {

	private int money;
	private int forwhat;
	private int type;
	private int account;
	private int day;
	private int month;
	private int year;

	public ForDB(int money, int forwhat, int type, int account, int day,
			int month, int year) {
		this.money = money;
		this.forwhat = forwhat;
		this.type = type;
		this.account = account;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getForwhat() {
		return forwhat;
	}

	public void setForwhat(int forwhat) {
		this.forwhat = forwhat;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
