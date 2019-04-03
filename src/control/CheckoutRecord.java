package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.domain.Person;

public class CheckoutRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087253122565711023L;
	private String ID;
	private Person member;
	private List<CheckoutRecordEntry> checkoutRecordEntry;

	public CheckoutRecord() {
		checkoutRecordEntry = new ArrayList<CheckoutRecordEntry>();
	}

	public CheckoutRecord(String ID, Person member) {
		super();
		this.ID = ID;
		this.member = member;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public Person getMember() {
		return member;
	}

	public void setMember(Person member) {
		this.member = member;
	}

	public void addRecordEntry(CheckoutRecordEntry recordEntry) {
		checkoutRecordEntry.add(recordEntry);
	}

	public List<CheckoutRecordEntry> getRecordEntries() {
		return checkoutRecordEntry;
	}

	public List<CheckoutRecordEntry> getOverDueRecordEntry() {
		List<CheckoutRecordEntry> overDueList = new ArrayList<CheckoutRecordEntry>();
		for (CheckoutRecordEntry recordEntry : this.checkoutRecordEntry) {
			if (recordEntry.isOverDue() && recordEntry.getBookCopy().isAvailable() == false) {
				overDueList.add(recordEntry);
			}
		}
		return overDueList;
	}
}
