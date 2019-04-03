package model.domain;

import control.CheckoutControl;
import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutControl checkoutRecord;

	public LibraryMember(String PersonId, String fname, String lname, String tel, Address add, String memberId) {
		super(PersonId, fname, lname, tel, add);
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", "
				+ getTelephone() + " " + getAddress();
	}

	public CheckoutControl getCheckoutRecord() {
		return checkoutRecord;
	}

	private static final long serialVersionUID = -2226197306790714013L;

	public void setCheckoutRecord(CheckoutControl chRec) {
		checkoutRecord = chRec;

	}

}
