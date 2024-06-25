import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import models.Book;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookEditTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    public void removeExistingInfoInField() {
        //Remove existing text in fields
        Platform.runLater(() -> {
            TextField titleField = lookup("#titleField").query();
            titleField.setText("");

            TextField authorFirstNameField = lookup("#authorFirstNameField").query();
            authorFirstNameField.setText("");

            TextField authorSurnameField = lookup("#authorSurnameField").query();
            authorSurnameField.setText("");

            TextField isbnField = lookup("#isbnField").query();
            isbnField.setText("");

            TextField publisherNameField = lookup("#publisherNameField").query();
            publisherNameField.setText("");
        });
    }

    @Test
    public void testEditBookButton_WithoutSelectingBook_ShouldNotEditBook() {
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Having no book selected should trigger error message.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

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
    public void testEditBookButton_WithValidParameters_ShouldEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());

        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertEquals("title", editedBook.getTitle(), "Title should match.");
        assertEquals("firstName", editedBook.getAuthorFirstName(), "Author First Name should match.");
        assertEquals("surname", editedBook.getAuthorSurname(), "Author Surname should match.");
        assertEquals("978-0453289174", editedBook.getISBN(), "ISBN should match.");
        assertEquals("2000-01-01", editedBook.getPublishDate().toString(), "Publish Date should match.");
        assertEquals("FICTION", editedBook.getGenre(), "Genre should match.");
        assertEquals("publisherName", editedBook.getPublisherName(), "Publisher name should match.");
        assertEquals("AVAILABLE", editedBook.getAvailabilityStatus(), "Availability Status should match.");
        assertEquals("NEW", editedBook.getCondition(), "Condition should match.");
    }

    @Test
    public void testCancelEditBookButton_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Cancel" button to cancel process
        clickOn("Cancel");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is added to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the added book
        Book lastBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        assertNotEquals("title", lastBook.getTitle(), "Title should match.");
        assertNotEquals("firstName", lastBook.getAuthorFirstName(), "Author First Name should match.");
        assertNotEquals("surname", lastBook.getAuthorSurname(), "Author Surname should match.");
        assertNotEquals("978-0453289174", lastBook.getISBN(), "ISBN should match.");
        assertNotEquals("2000-01-01", lastBook.getPublishDate().toString(), "Publish Date should match.");
        assertNotEquals("FICTION", lastBook.getGenre(), "Genre should match.");
        assertNotEquals("publisherName", lastBook.getPublisherName(), "Publisher name should match.");
        assertNotEquals("AVAILABLE", lastBook.getAvailabilityStatus(), "Availability Status should match.");
        assertNotEquals("NEW", lastBook.getCondition(), "Condition should match.");
    }

    @Test
    public void testEditBookButton_EmptyTitleField_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty title should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", editedBook.getTitle(), "Title shouldn't match.");
    }

    @Test
    public void testEditBookButton_EmptyAuthorFirstNameField_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Author First Name should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", editedBook.getAuthorFirstName(), "Author First Name shouldn't match.");
    }

    @Test
    public void testEditBookButton_EmptyAuthorSurnameField_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("");
        clickOn("#isbnField").write("978-0453289174");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Author Surname should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", editedBook.getAuthorSurname(), "Author Surname shouldn't match.");
    }

    //Don't run this test, it bricks the system
    @Test
    public void testEditBookButton_EmptyPublishDateField_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");
        clickOn("#publisherNameField").write("publisherName");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class); // Set the PublishDate field to be empty
        publishDateField.setValue(null);

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Publish Date should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", editedBook.getPublishDate().toString(), "Publish Date shouldn't match.");
    }

    //Don't run this test, it bricks the system
    @Test
    public void testEditBookButton_InvalidPublishDateField_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Set the PublishDate field to be invalid
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class); // Set the PublishDate field to be empty
        publishDateField.setValue(null);
        clickOn("#publishDateField").write("invalidVal");

        clickOn("#publisherNameField").write("publisherName");
        clickOn("#genreField").clickOn("FICTION");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Publish Date should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("invalidVal", editedBook.getPublishDate().toString(), "Publish Date shouldn't match.");
    }

    @Test
    public void testEditBookButton_FuturePublishDateField_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("Surname");
        clickOn("#isbnField").write("978-0453289174");
        clickOn("#publisherNameField").write("publisherName");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(3023, 11, 21)));

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Click the "Save" button to save the new book
        try {
            clickOn("Save");
        } catch (Exception e) {
            // Catch any exception that occurs after clicking the "Save" button
            System.err.println("Exception occurred after clicking Save: " + e.getMessage());
        }

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Future Publish Date should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("3023-11-21", editedBook.getPublishDate().toString(), "Publish Date should not match.");
    }

    @Test
    public void testEditBookButton_EmptyPublisherName_ShouldNotEditBook() {
        // Simulate selecting last book in the list
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(booksTable.getItems().size() - 1).query());
        // Simulate clicking the "Edit Book" button to open the dialog
        moveBy(0,-300); //To avoid hovering over delete button
        clickOn("#editBookButton");

        //Remove existing text in fields
        removeExistingInfoInField();

        //Enter sample information into the text field
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        clickOn("#genreField").clickOn("FICTION");
        clickOn("#publisherNameField").write("");
        clickOn("#availabilityStatusField").clickOn("AVAILABLE");
        clickOn("#conditionField").clickOn("NEW");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Publisher Name should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is edited to the TableView
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after editing a book.");

        // Verify the details of the edited book
        Book editedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", editedBook.getPublisherName(), "Publisher Name shouldn't match.");
    }

}

