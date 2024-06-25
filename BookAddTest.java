import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Book;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookAddTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

   @Test
    public void testAddBookButton_WithValidParameters_ShouldAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30,0);
        clickOn();

        //Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("NEW");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after adding a book.");

        // Verify the details of the added book
        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        assertEquals("title", addedBook.getTitle(), "Title should match.");
        assertEquals("firstName", addedBook.getAuthorFirstName(), "Author First Name should match.");
        assertEquals("surname", addedBook.getAuthorSurname(), "Author Surname should match.");
        assertEquals("978-0453289174", addedBook.getISBN(), "ISBN should match.");
        assertEquals("2000-01-01", addedBook.getPublishDate().toString(), "Publish Date should match.");
        assertEquals("FICTION", addedBook.getGenre(), "Genre should match.");
        assertEquals("publisherName", addedBook.getPublisherName(), "Publisher name should match.");
        assertEquals("Available", addedBook.getAvailabilityStatus(), "Availability Status should match.");
        assertEquals("NEW", addedBook.getCondition(), "Condition should match.");
    }

    @Test
    public void testCancelAddBookButton_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30,0);
        clickOn();

        //Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("FICTION");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("NEW");

        // Click the "Cancel" button to cancel process
        clickOn("Cancel");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the book is added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);
        assertFalse(booksTable.getItems().isEmpty(), "Book table should not be empty after adding a book.");

        // Verify the details of the added book
        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        assertNotEquals("title", addedBook.getTitle(), "Title should match.");
        assertNotEquals("firstName", addedBook.getAuthorFirstName(), "Author First Name should match.");
        assertNotEquals("surname", addedBook.getAuthorSurname(), "Author Surname should match.");
        assertNotEquals("978-0453289174", addedBook.getISBN(), "ISBN should match.");
        assertNotEquals("2000-01-01", addedBook.getPublishDate().toString(), "Publish Date should match.");
        assertNotEquals("FICTION", addedBook.getGenre(), "Genre should match.");
        assertNotEquals("publisherName", addedBook.getPublisherName(), "Publisher name should match.");
        assertNotEquals("Available", addedBook.getAvailabilityStatus(), "Availability Status should match.");
        assertNotEquals("NEW", addedBook.getCondition(), "Condition should match.");
    }

    @Test
    public void testAddBookButton_EmptyTitleField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30,0);
        clickOn();

        //Enter sample information into the text fields
        clickOn("#titleField").write("");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

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

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end
        assertNotEquals("", addedBook.getTitle(), "Title should not match.");
    }

    @Test
    public void testAddBookButton_EmptyAuthorFirstNameField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30,0);
        clickOn();

        //Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

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

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", addedBook.getAuthorFirstName(), "Author First Name should not match.");
    }

    @Test
    public void testAddBookButton_EmptyAuthorSurnameField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

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

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", addedBook.getAuthorSurname(), "Author Surname should not match.");
    }


    @Test
    public void testAddBookButton_EmptyPublishDateField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Set the PublishDate field to be empty
        clickOn("#publishDateField").write("");

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

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

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertEquals(null, addedBook.getPublishDate(), "Publish Date should be null.");
    }

    @Test
    public void testAddBookButton_InvalidPublishDateField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Set the PublishDate field to be invalid Value
        clickOn("#publishDateField").write("invalidVal");

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

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

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("invalidVal", addedBook.getPublishDate(), "Publish Date should not match.");
    }

    @Test
    public void testAddBookButton_FuturePublishDateField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(3023, 11, 21)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Future Publish Date should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("3023-11-21", addedBook.getPublishDate().toString(), "Publish Date should not match.");
    }
    
    @Test
    public void testAddBookButton_EmptyGenreField_ShouldNotAddBook() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 01, 01)));

        clickOn("#genreField").write("");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Genre should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", addedBook.getGenre(), "Genre should not match.");
    }

    @Test
    public void testAddBookButton_InvalidGenreField_ShouldNotAddBook() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 01, 01)));

        clickOn("#genreField").write("invalidVal");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Genre should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("invalidVal", addedBook.getGenre(), "Genre should not match.");
    }

    @Test
    public void testAddBookButton_EmptyPublisherNameField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in the centre fails to register input, this is the workaround
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("New");

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

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", addedBook.getPublisherName(), "Publisher name should not match.");
    }

    @Test
    public void testAddBookButton_EmptyAvailabilityStatusField_ShouldNotAddBook() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 01, 01)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("");
        clickOn("#conditionField").write("New");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Availability Status should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", addedBook.getAvailabilityStatus(), "Availability Status should not match.");
    }

    @Test
    public void testAddBookButton_InvalidAvailabilityStatusField_ShouldNotAddBook() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in centre fails to register input, this is the work around
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 01, 01)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("invalidVal");
        clickOn("#conditionField").write("New");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Availability Status should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("invalidVal", addedBook.getAvailabilityStatus(), "Availability Status should not match.");
    }

    @Test
    public void testAddBookButton_EmptyConditionField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in the centre fails to register input, this is the workaround
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Condition Field should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("", addedBook.getCondition(), "Condition should not match.");
    }

    @Test
    public void testAddBookButton_InvalidConditionField_ShouldNotAddBookToGUI() {
        // Simulate clicking the "Add Book" button to open the dialog
        // The add book button when clicked in the centre fails to register input, this is the workaround
        moveTo("#addBookButton");
        moveBy(30, 0);
        clickOn();

        // Enter sample information into the text fields
        clickOn("#titleField").write("title");
        clickOn("#authorFirstNameField").write("firstName");
        clickOn("#authorSurnameField").write("surname");
        clickOn("#isbnField").write("978-0453289174");

        // Retrieve the DatePicker control
        DatePicker publishDateField = lookup("#publishDateField").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> publishDateField.setValue(LocalDate.of(2000, 1, 1)));

        clickOn("#genreField").write("Fiction");
        clickOn("#publisherNameField").write("publisherName");
        clickOn("#availabilityStatusField").write("Available");
        clickOn("#conditionField").write("invalidVal");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Condition Field should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the book is not added to the TableView
        TableView<Book> booksTable = lookup("#booksTable").queryAs(TableView.class);

        Book addedBook = booksTable.getItems().get(booksTable.getItems().size() - 1); // Assuming the new book is at the end

        assertNotEquals("invalidVal", addedBook.getCondition(), "Condition should not match.");
    }
}