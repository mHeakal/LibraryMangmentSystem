package control;

import java.io.Serializable;
import java.time.LocalDate;

import model.domain.BookCopy;

public class CheckoutRecordEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3603913063423225992L;
	private String ID;
	private LocalDate checkoutDate;
	private BookCopy bookCopy;
	private LocalDate dueDate;

	public CheckoutRecordEntry(BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate) {
		this.bookCopy = bookCopy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isOverDue() {
		if (this.dueDate.isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}
}
