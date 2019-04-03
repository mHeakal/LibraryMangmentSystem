package model.dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.LibraryMember;
import model.domain.User;
import utility.Utility;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\model\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// implement: other save operations
	@Override
	public void saveNewMember(LibraryMember libraryMember) {
		HashMap<String, LibraryMember> memberMap = readLibraryMembersMap();
		if (memberMap == null) {
			memberMap = new HashMap<String, LibraryMember>();
		}
		String memberID = libraryMember.getMemberId();
		memberMap.put(memberID, libraryMember);
		saveToStorage(StorageType.MEMBERS, memberMap);

	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup
	// static void loadMemberMap(List<LibraryMember> memberList) {

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	@Override
	public HashMap<String, User> getUsers() {
		return readUserMap();
	}

	@Override
	public HashMap<String, LibraryMember> getLibraryMember() {
		return readLibraryMembersMap();
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readLibraryMembersMap() {
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	@Override
	public void addBook(Book book) {
		HashMap<String, Book> bookMap = readBookMap();
		if (bookMap == null) {
			bookMap = new HashMap<String, Book>();
		}
		String isbn = book.getIsbn();
		bookMap.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookMap);
		System.out.println(bookMap.get(isbn));
	}

	@Override
	public Book searchBook(String isbn) {
		HashMap<String, Book> booksMap = readBookMap();
		Book b = booksMap.get(isbn);
		return b;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBookMap() {
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@Override
	public HashMap<String, Book> getBook() {
		return readBookMap();
	}

	@Override
	public BookCopy getAvailableBookCopy(String isbn) {
		HashMap<String, Book> books = getBook();
		Book book = books.get(isbn);
		BookCopy bc = null;
		if (book != null) {
			BookCopy[] bookCopyList = book.getCopies();
			if (bookCopyList != null)
				for (BookCopy b : bookCopyList) {
					if (b.isAvailable()) {
						return b;
					}
				}
		}
		return bc;// book.getCopies()[book.getCopies().length - 1];
	}

	@Override
	public LibraryMember getLibraryMemberById(String id) {
		LibraryMember libraryMember = null;
		HashMap<String, LibraryMember> libraryMembers = getLibraryMember();
		libraryMember = libraryMembers.get(id);
		return libraryMember;
	}

	@Override
	public void setBookCopyAsNotAvailable(String isbn, int copyNumber) {
		HashMap<String, Book> bookMap = getBook();
		for (BookCopy bc : bookMap.get(isbn).getCopies()) {
			if (bc.getCopyNum() == copyNumber) {
				bc.setAvailability(false);
				break;
			}
		}
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	@Override
	public void addBookCopy(String isbn, BookCopy newBookCopy) {
		HashMap<String, Book> bookMap = getBook();
		bookMap.get(isbn).addBookCopy(newBookCopy);
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	@Override
	public BookCopy searchCopyNumberByISBN(String isbn, BookCopy newBookCopy) {
		HashMap<String, Book> bookMap = getBook();
		BookCopy[] lstBookCopy = bookMap.get(isbn).getCopies();
		if (lstBookCopy == null)
			Utility.showAlerMessage("Information Dialog", "Add New Book Copy Error",
					"ISBN does not exist. Please input correct ISBN!", AlertType.ERROR);
		else
			for (BookCopy bookCopyItem : lstBookCopy) {
				if (bookCopyItem.getCopyNum() == newBookCopy.getCopyNum()) {
					return newBookCopy;
				}

			}
		return null;
	}
}
