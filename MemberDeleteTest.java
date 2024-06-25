import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Member;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


class MemberDeleteTest extends ApplicationTest {
    public  void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    public void testDeleteMemberButton_ShouldDeleteMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");

        // Simulate selecting last member in the list
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(membersTable.getItems().size() - 1).query());

        // Simulate clicking the "Delete Member" button to open the dialog
        clickOn("#deleteMemberButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Prompt not opened correctly.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        Member lastMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the last member is at the end
        //Verification: Check if the member is deleted from the TableView
        if(membersTable.getItems().isEmpty()) {
            assertTrue(membersTable.getItems().isEmpty(), "Member deleted.");
        } else {
            // Verify the details of the last member don't match
            assertNotEquals("firstName", lastMember.getFirstName(), "First Name name shouldn't match.");
            assertNotEquals("lastName", lastMember.getLastName(), "Last Name shouldn't match.");
            assertNotEquals("email@email.com", lastMember.getEmail(), "Email shouldn't match.");
            assertNotEquals("0453289174", lastMember.getPhone(), "Phone Number shouldn't match.");
            assertNotEquals("123 Street", lastMember.getAddressLine1(), "Address Line 1 shouldn't match.");
            assertNotEquals("Building 1", lastMember.getAddressLine2(), "Address Line 2 shouldn't match.");
            assertNotEquals("town", lastMember.getTownOrCity(), "Town Or City shouldn't match.");
            assertNotEquals("county", lastMember.getCounty(), "County shouldn't match.");
            assertNotEquals("A1 1AA", lastMember.getPostCode(), "Post Code shouldn't match.");
            assertNotEquals("2000-01-01", lastMember.getDateRegistered().toString(), "Date Registered shouldn't match.");
        }
    }

    @Test
    public void testCancelDeleteMemberButton_ShouldNotDeleteMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");
        // Simulate selecting last member in the list
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);
        clickOn((Node) lookup(".table-row-cell").nth(membersTable.getItems().size() - 1).query());

        // Simulate clicking the "Delete Member" button to open the dialog
        clickOn("#deleteMemberButton");

        sleep(1000); // sleep for 1 sec

        // Click the "Cancel" button to cancel process
        clickOn("Cancel");

        sleep(1000);

        // Verify the details of the last member
        Member lastMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the new member is at the end
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
    public void testDeleteMemberButton_WithoutSelectingMember_ShouldNotDeleteMember() {
        //Simulate switching to the "Member Manager" tab
        clickOn("Member Manager");
        // Simulate clicking the "Delete Member" button to open the dialog
        clickOn("#deleteMemberButton");

        sleep(1000); // sleep for 1 sec

        // Check if the "OK" node is present
        if (lookup("OK").tryQuery().orElse(null) == null) {
            // "OK" node not found, then
            System.err.println("Having no member selected should trigger error message.");
        } else {
            // "OK" node found, proceed with clicking it
            clickOn("OK");
        }

        sleep(1000); // sleep for 1 sec

        // Verification: Check if the member is added to the TableView
        TableView<Member> membersTable = lookup("#membersTable").queryAs(TableView.class);
        assertFalse(membersTable.getItems().isEmpty(), "Member table should not be empty after adding a member.");

        // Verify the details of the last member in list
        Member lastMember = membersTable.getItems().get(membersTable.getItems().size() - 1); // Assuming the member is at the end

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

}

