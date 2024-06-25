import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Book;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


class BookDeleteTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    public void testDeleteBookButton_ShouldDeleteBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Delete Book" button to open the dialog
        clickOn("#deleteBookButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Prompt not opened correctly.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
            if (lookup("OK").tryQuery().orElse(null) == null) {
                // "OK" node not found, then
                System.err.println("Prompt not opened correctly.");
            } else {
                // "OK" node found, proceed with clicking it
                clickOn("OK");
            }
        }

        //Defect ID: 015
        sleep(1000);
        clickOn("OK");

        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the last book is at the end
        //Verification: Check if the book is deleted from the TableView
        if(booksTable.getItems().isEmpty()) {
            assertTrue(booksTable.getItems().isEmpty(), "Book deleted.");
        } else {
            // Verify the details of the last book don't match
            assertNotEquals("title", lastBook.getTitle(), "Title shouldn't match.");
            assertNotEquals("firstName", lastBook.getAuthorFirstName(), "Author First Name shouldn't match.");
            assertNotEquals("surname", lastBook.getAuthorSurname(), "Author Surname shouldn't match.");
            assertNotEquals("978-0453289174", lastBook.getISBN(), "ISBN should match.");
            assertNotEquals("2000-01-01", lastBook.getPublishDate().toString(), "Publish Date shouldn't match.");
            assertNotEquals("FICTION", lastBook.getGenre(), "Genre shouldn't match.");
            assertNotEquals("publisherName", lastBook.getPublisherName(), "Publisher name shouldn't match.");
            assertNotEquals("AVAILABLE", lastBook.getAvailabilityStatus(), "Availability Status shouldn't match.");
            assertNotEquals("NEW", lastBook.getCondition(), "Condition shouldn't match.");
        }
    }

    @Test
    public void testCancelDeleteBookButton_ShouldNotDeleteBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Delete Book" button to open the dialog
        clickOn("#deleteBookButton");

        sleep(1000); // sleep for 1 sec

        // Click the "Cancel" button to cancel process
        clickOn("Cancel");

        sleep(1000);

        // Click the "Cancel" again (Defect ID: 015)
        clickOn("Cancel");

        // Verify the details of the last book
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        assertEquals("title", lastBook.getTitle(), "Title should match.");
        assertEquals("firstName", lastBook.getAuthorFirstName(), "Author First Name should match.");
        assertEquals("surname", lastBook.getAuthorSurname(), "Author Surname should match.");
        assertEquals("978-0453289174", lastBook.getISBN(), "ISBN should match.");
        assertEquals("2000-01-01", lastBook.getPublishDate().toString(), "Publish Date should match.");
        assertEquals("FICTION", lastBook.getGenre(), "Genre should match.");
        assertEquals("publisherName", lastBook.getPublisherName(), "Publisher name should match.");
        assertEquals("AVAILABLE", lastBook.getAvailabilityStatus(), "Availability Status should match.");
        assertEquals("NEW", lastBook.getCondition(), "Condition should match.");
    }

    @Test
    public void testDeleteBookButton_WithoutSelectingBook_ShouldNotDeleteBook() {
        // Simulate clicking the "Delete Book" button to open the dialog
        clickOn("#deleteBookButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Having no book selected should trigger error message.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        sleep(1000); // sleep for 1 sec

        clickOn("OK"); //Ok appear twice for some reason

        // Verification: Check if the book is added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after adding a book.");

        // Verify the details of the last book in list
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the book is at the end

        assertEquals("title", lastBook.getTitle(), "Title should match.");
        assertEquals("firstName", lastBook.getAuthorFirstName(), "Author First Name should match.");
        assertEquals("surname", lastBook.getAuthorSurname(), "Author Surname should match.");
        assertEquals("978-0453289174", lastBook.getISBN(), "ISBN should match.");
        assertEquals("2000-01-01", lastBook.getPublishDate().toString(), "Publish Date should match.");
        assertEquals("FICTION", lastBook.getGenre(), "Genre should match.");
        assertEquals("publisherName", lastBook.getPublisherName(), "Publisher name should match.");
        assertEquals("AVAILABLE", lastBook.getAvailabilityStatus(), "Availability Status should match.");
        assertEquals("NEW", lastBook.getCondition(), "Condition should match.");
    }

}

