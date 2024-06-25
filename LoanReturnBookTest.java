import daos.BookDAO;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import models.Book;
import models.Loan;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoanReturnBookTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    public void testReturnLoanBookButton_WithValidParameters_ShouldReturnLoanedBook() {
        //Simulate switching to the "Loan Manager" tab
        clickOn("Loan Manager");
        // Simulate selecting last member in the list
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(loansTable.getItems().get(0).getBookId() -1).query()); //Gets first loan

        // Simulate clicking the "Return Book" button to open the dialog
        clickOn("#returnBookButton");

        sleep(1000); // sleep for 1 sec

        //Enter sample information into the text field
        // Retrieve the DatePicker control
        DatePicker returnDateField = lookup("#returnDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> returnDateField.setValue(LocalDate.of(2024, 01, 01)));

        // Click the "Save" button to save the new book
        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is available to be loaned again
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after returning a book.");

        // Reload to update the table (Defect Log ID: 037)
        BookDAO bookDAO = new BookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        booksTable.getItems().setAll(allBooks);

        // Verify the details of the returned book
        Book returnedBook = booksTable.getItems().get(0); // Assuming the returned book is the first in the list

        assertEquals("Available", returnedBook.getAvailabilityStatus(), "Book should be available.");

        // Verify the details of the loan
        Loan returnedLoan = loansTable.getItems().get(0); // Assuming the returned book is the first in the list

        assertEquals("2024-01-01", returnedLoan.getReturnDate(), "Book should have return date.");

    }

    @Test
    public void testReturnLoanBookButton_WithEmptyReturnDate_ShouldNotReturnLoanedBook() {
        //Simulate switching to the "Loan Manager" tab
        clickOn("Loan Manager");
        // Simulate selecting last member in the list
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(loansTable.getItems().get(0).getBookId() -1).query()); //Gets first loan

        // Simulate clicking the "Return Book" button to open the dialog
        clickOn("#returnBookButton");

        sleep(1000); // sleep for 1 sec

        //Enter sample information into the text field
        // Retrieve the DatePicker control
        DatePicker returnDateField = lookup("#returnDateField").queryAs(DatePicker.class);
        returnDateField.setValue(null); //set Return Date to be empty

        // Click the "Save" button to save the new book
        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is available to be loaned again
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after returning a book.");

        // Reload to update the table (Defect Log ID: 037)
        BookDAO bookDAO = new BookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        booksTable.getItems().setAll(allBooks);

        // Verify the details of the returned book
        Book returnedBook = booksTable.getItems().get(0); // Assuming the returned book is the first in the list

        assertEquals("On Loan", returnedBook.getAvailabilityStatus(), "Book should still be on loan.");
    }

    @Test
    public void testReturnLoanBookButton_WithInvalidReturnDate_ShouldNotReturnLoanedBook() {
        //Simulate switching to the "Loan Manager" tab
        clickOn("Loan Manager");
        // Simulate selecting last member in the list
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(loansTable.getItems().get(0).getBookId() -1).query()); //Gets first loan

        // Simulate clicking the "Return Book" button to open the dialog
        clickOn("#returnBookButton");

        sleep(1000); // sleep for 1 sec

        //Enter sample information into the text field
        // Retrieve the DatePicker control
        DatePicker returnDateField = lookup("#returnDateField").queryAs(DatePicker.class);
        returnDateField.setValue(null); //set Return Date to be empty
        clickOn("#returnDateField").write("invalidVal");

        // Click the "Save" button to save the new book
        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is available to be loaned again
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after returning a book.");

        // Reload to update the table (Defect Log ID: 037)
        BookDAO bookDAO = new BookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        booksTable.getItems().setAll(allBooks);

        // Verify the details of the returned book
        Book returnedBook = booksTable.getItems().get(0); // Assuming the returned book is the first in the list

        assertEquals("On Loan", returnedBook.getAvailabilityStatus(), "Book should still be on loan.");
    }

    @Test
    public void testReturnLoanBookButton_WithPastReturnDate_ShouldNotReturnLoanedBook() {
        //Simulate switching to the "Loan Manager" tab
        clickOn("Loan Manager");
        // Simulate selecting last member in the list
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(loansTable.getItems().get(0).getBookId() -1).query()); //Gets first loan

        // Simulate clicking the "Return Book" button to open the dialog
        clickOn("#returnBookButton");

        sleep(1000); // sleep for 1 sec

        //Enter sample information into the text field
        // Retrieve the DatePicker control
        DatePicker returnDateField = lookup("#returnDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> returnDateField.setValue(LocalDate.of(2022, 11, 13)));

        // Click the "Save" button to save the new book
        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is available to be loaned again
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after returning a book.");

        // Reload to update the table (Defect Log ID: 037)
        BookDAO bookDAO = new BookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        booksTable.getItems().setAll(allBooks);

        // Verify the details of the returned book
        Book returnedBook = booksTable.getItems().get(0); // Assuming the returned book is the first in the list

        assertEquals("On Loan", returnedBook.getAvailabilityStatus(), "Book should still be on loan.");
    }

    @Test
    public void testReturnLoanBookButton_WithoutSelectingALoan_ShouldNotReturnLoanedBook() {
        //Simulate switching to the "Loan Manager" tab
        clickOn("Loan Manager");

        // Simulate clicking the "Return Book" button to open the dialog
        clickOn("#returnBookButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Not selecting a loan should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is available to be loaned again
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        // Reload to update the table (Defect Log ID: 037)
        BookDAO bookDAO = new BookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        booksTable.getItems().setAll(allBooks);

        // Verify the details of the returned book
        Book returnedBook = booksTable.getItems().get(0); // Assuming the returned book is the first in the list

        assertEquals("On Loan", returnedBook.getAvailabilityStatus(), "Book should still be on loan.");
    }
}
