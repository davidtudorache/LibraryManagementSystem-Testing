import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Member;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import daos.MemberDAO;

import java.nio.channels.AsynchronousCloseException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberAddTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    public void testAddMemberButton_WithValidParameters_ShouldAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);
        assertFalse(membersTable.getItems().isEmpty(), "Members table should not be empty after adding a member.");

        // Reload to update the table (Defect Log ID: 027)
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertEquals("firstName", addedMember.getFirstName(), "First Name name should match.");
        assertEquals("lastName", addedMember.getLastName(), "Last Name should match.");
        assertEquals("email@email.com", addedMember.getEmail(), "Email should match.");
        assertEquals("0453289174", addedMember.getPhone(), "Phone Number should match.");
        assertEquals("123 Street", addedMember.getAddressLine1(), "Address Line 1 should match.");
        assertEquals("Building 1", addedMember.getAddressLine2(), "Address Line 2 should match.");
        assertEquals("town", addedMember.getTownOrCity(), "Town Or City should match.");
        assertEquals("county", addedMember.getCounty(), "County should match.");
        assertEquals("A1 1AA", addedMember.getPostCode(), "Post Code should match.");
        assertEquals("2000-01-01", addedMember.getDateRegistered().toString(), "Date Registered should match.");
    }

    @Test
    public void testCancelAddMemberButton_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");
        // Simulate selecting last member in the list
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(membersTable.getItems().size() - 1).query());

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        sleep(1000); // sleep for 1 sec

        // Click the "Cancel" button to cancel process
        clickOn("Cancel");

        sleep(1000);

        // Verify the details of the last member
        Member lastMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the last member's details are this
        assertEquals("firstName", lastMember.getFirstName(), "First Name name should match.");
        assertEquals("lastName", lastMember.getLastName(), "Last Name should match.");
        assertEquals("email@email.com", lastMember.getEmail(), "Email should match.");
        assertEquals("0453289174", lastMember.getPhone(), "Phone Number should match.");
        assertEquals("123 Street", lastMember.getAddressLine1(), "Address Line 1 should match.");
        assertEquals("Building 1", lastMember.getAddressLine2(), "Address Line 2 should match.");
        assertEquals("town", lastMember.getTownOrCity(), "Town Or City should match.");
        assertEquals("county", lastMember.getCounty(), "County should match.");
        assertEquals("A1 1AA", lastMember.getPostCode(), "Post Code should match.");
        assertEquals("2000-01-01", lastMember.getDateRegistered().toString(), "Date Registered should match.");
    }

    @Test
    public void testAddMemberButton_WithEmptyFirstNameField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty First Name should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("", addedMember.getFirstName(), "First Name name shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithEmptyLastNameField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Last Name should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("", addedMember.getLastName(), "Last Name name shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithEmptyEmailField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Email should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("", addedMember.getEmail(), "Email shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithNonUniqueEmailField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("john.smith@example.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Email should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("john.smith@example.com", addedMember.getEmail(), "Email shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithEmptyPhoneField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Phone number should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("", addedMember.getPhone(), "Phone number shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithEmptyPostCodeField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(2000, 1, 1)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Post Code should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("", addedMember.getPostCode(), "Post Code shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithEmptyDateRegisteredField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Empty Date Registered should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("", addedMember.getDateRegistered(), "Date Registered shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithInvalidDateRegisteredField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");
        clickOn("#dateRegisteredPicker").write("invalidVal");

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Invalid Date Registered should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("invalidVal", addedMember.getDateRegistered(), "Date Registered shouldn't match.");
    }

    @Test
    public void testAddMemberButton_WithFutureDateRegisteredField_ShouldNotAddMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate clicking the "Add Member" button to open the dialog
        clickOn("#addMemberButton");

        //Enter sample information into the text fields
        clickOn("#firstNameField").write("firstName");
        clickOn("#lastNameField").write("lastName");
        clickOn("#emailField").write("email@email.com");
        clickOn("#phoneField").write("0453289174");
        clickOn("#addressLine1Field").write("123 Street");
        clickOn("#addressLine2Field").write("Building 1");
        clickOn("#townOrCityField").write("town");
        clickOn("#countyField").write("county");
        clickOn("#postCodeField").write("A1 1AA");

        // Retrieve the DatePicker control
        DatePicker dateRegisteredPicker = lookup("#dateRegisteredPicker").queryAs(DatePicker.class);
        // Interact with UI thread to set the date value
        interact(() -> dateRegisteredPicker.setValue(LocalDate.of(3023, 11, 21)));

        // Click the "Save" button to save the new book
        clickOn("Save");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Future Date Registered should trigger error.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);

        // Reload to update the table
        MemberDAO memberDAO = new MemberDAO();
        List<Member> allMembers = memberDAO.getAllMembers();
        membersTable.getItems().setAll(allMembers);

        // Verify the details of the added book
        Member addedMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end

        assertNotEquals("3023-11-21", addedMember.getDateRegistered(), "Date Registered shouldn't match.");
    }
}
