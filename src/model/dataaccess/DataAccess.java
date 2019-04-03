package model.dataaccess;

import java.util.HashMap;

import model.domain.Book;
import model.domain.BookCopy;
import model.domain.LibraryMember;
import model.domain.User;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();

	public HashMap<String, User> readUserMap();

	public HashMap<String, LibraryMember> readMemberMap();

//	public void saveNewMember(LibraryMember member);

	public HashMap<String, User> getUsers();

	public HashMap<String, LibraryMember> getLibraryMember();

	public void saveNewMember(LibraryMember libraryMember);

	public void addBook(Book newbook);

	public Book searchBook(String isbn);

	public HashMap<String, Book> getBook();

	public BookCopy getAvailableBookCopy(String isbn);

	public LibraryMember getLibraryMemberById(String id);

	public void setBookCopyAsNotAvailable(String isbn, int copyNum);

	public BookCopy searchCopyNumberByISBN(String isbn, BookCopy newBookCopy);

	public void addBookCopy(String isbn, BookCopy newBookCopy);

}
