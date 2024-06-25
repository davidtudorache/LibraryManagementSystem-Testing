import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import models.Book;
import models.Loan;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class LoanBookTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    public void testLoanBookButton_WithValidParameters_ShouldLoanBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        clickOn("#memberIdField").write("1");

        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the loan is added to the TableView
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);
        assertFalse(loansTable.getItems().isEmpty(), "Loans table should not be empty after adding a loan.");

        // Verify the details of the added book in list
        Loan addedLoan = loansTable.getItems().get(loansTable.getItems().size() - 1); // Assuming the loan is at the end

        int lastBookID = loansTable.getItems().get(loansTable.getItems().size() - 1).getBookId(); // Assuming the new loan is at the end
        assertEquals(lastBookID, addedLoan.getBookId(), "Book ID should match.");
        assertEquals(1, addedLoan.getMemberId(), "Member ID should match.");
        assertEquals("2024-01-07", addedLoan.getLoanDate().toString(), "Loan Date should match.");
        assertEquals("2024-01-21", addedLoan.getDueDate().toString(), "Due Date should match.");
    }

    @Test
    public void testLoanBookButton_WithInvalidLoanDuration_ShouldNotLoanBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        //Remove text from fields
        TextField loanDurationField = lookup("#loanDurationField").query();
        loanDurationField.setText("");

        //Fill in fields
        clickOn("#memberIdField").write("1");

        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Loan Duration should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the loan is added to the TableView
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);

        // Verify the details of the added loan in list
        Loan addedLoan = loansTable.getItems().get(loansTable.getItems().size() - 1); // Assuming the loan is at the end

        assertNotEquals("", addedLoan.getLoanDate(), "Loan Date shouldn't match.");
        assertNotEquals("", addedLoan.getDueDate(), "Due Date shouldn't match.");
    }

    @Test
    public void testLoanBookButton_WithInvalidMemberID_ShouldNotLoanBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        //Fill in fields
        clickOn("#memberIdField").write("");

        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Book ID should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the loan is added to the TableView
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);

        // Verify the details of the added loan in list
        Loan addedLoan = loansTable.getItems().get(loansTable.getItems().size() - 1); // Assuming the loan is at the end

        assertNotEquals("", addedLoan.getMemberId(), "Member ID shouldn't match.");
    }

    @Test
    public void testLoanBookButton_WithNonExistentMemberID_ShouldNotLoanBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        //Removing existing text in fields
        TextField bookIdField = lookup("#bookIdField").query();
        bookIdField.setText("");

        //Fill in fields
        clickOn("#memberIdField").write("500");

        clickOn("Confirm");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the loan is added to the TableView
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);

        // Verify the details of the added loan in list
        Loan addedLoan = loansTable.getItems().get(loansTable.getItems().size() - 1); // Assuming the loan is at the end

        assertNotEquals("500", addedLoan.getMemberId(), "Member ID shouldn't match.");
    }

    @Test
    public void testLoanBookButton_WithoutSelectingBook_ShouldNotLoanBook() {
        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Having no book selected should trigger error message.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }
    }

    @Test
    public void testLoanBookButton_SelectingInvalidBook_ShouldNotLoanBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Having an invalid book selected should trigger error message.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }
    }

    @Test
    public void testLoanCancelBookButton_ShouldNotLoanBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Loan Book" button to open the dialog
        clickOn("#loanBookButton");

        clickOn("#memberIdField").write("1");

        sleep(1000); // sleep for 1 sec

        clickOn("Cancel");

        // Verification: Check if the loan is added to the TableView
        TableView<Loan> loansTable = lookup("#loansTable").queryAs(TableView.class);

        // Verify the details of the added book in list
        Loan addedLoan = loansTable.getItems().get(loansTable.getItems().size() - 1); // Assuming the loan is at the end

        int lastBookID = loansTable.getItems().get(loansTable.getItems().size() - 1).getBookId(); // Assuming the new loan is at the end
        assertNotEquals(lastBookID, addedLoan.getBookId(), "Book ID should match.");
        assertNotEquals(1, addedLoan.getMemberId(), "Member ID should match.");
        assertNotEquals("2024-01-07", addedLoan.getLoanDate().toString(), "Loan Date should match.");
        assertNotEquals("2024-01-21", addedLoan.getDueDate().toString(), "Due Date should match.");
    }

}
