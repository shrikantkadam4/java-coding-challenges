/*
We are building a program to manage equipment bookings at a sports facility. The facility has multiple pieces of equipment, such as treadmills and rowing machines, and members can reserve them for specific time periods.

Definitions:
- An "equipment" is an object representing a reservable item. It has properties for the ID and name.
- A "reservation" is an object representing a member booking for a specific piece of equipment over a time period. Times are represented as integers in minutes from the start of the day.
- A "FacilityManager" is the class used to manage all equipment and reservations.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Feel free to run it.
1-2) One of the tests is failing due to a bug in the code. Make the necessary changes to FacilityManager to fix the bug.
*/
/*
We are updating our system to support richer reservation queries. Facility managers need to look up all active reservations made by a specific member, and equipment coordinators need a quick summary of how each piece of equipment is being used.

2-1) Add getReservationsForMember() to FacilityManager.
     This function takes a member's name and returns a list of all their active reservations. If the member has no active reservations, return an empty list.

2-2) Add getEquipmentSummary() to FacilityManager.
     This function takes an equipment ID and returns a map with the following keys:
     - "total_reservations": the number of active reservations for that equipment.
     - "total_minutes": the total duration in minutes across all active reservations for that equipment.
     If the equipment has no active reservations, both values should be 0.

To assist you in testing these new functions, we have provided the testGetReservationsForMember and testGetEquipmentSummary tests.
*/


import java.util.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

public class EquipementKaratCodeChallenge {

    enum ReservationStatus {
        ACTIVE,
        CANCELLED
    }

record Equipment(int equipmentId, String name) {

    @Override
    public String toString() {
        return "Equipment ID: " + equipmentId + ", Name: " + name;
    }
}

    static class Reservation {
        final int reservationId;
        final String memberName;
        final int equipmentId;
        final int startTime;
        final int endTime;
        ReservationStatus status;

        Reservation(int reservationId, String memberName,
                    int equipmentId, int startTime, int endTime) {
            this.reservationId = reservationId;
            this.memberName = memberName;
            this.equipmentId = equipmentId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.status = ReservationStatus.ACTIVE;
        }

        int getDuration() {
            return endTime - startTime;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Reservation r)) return false;
            return reservationId == r.reservationId;
        }

        @Override public int hashCode() {
            return Integer.hashCode(reservationId);
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId
                    + ", Member: " + memberName
                    + ", Equipment ID: " + equipmentId
                    + ", Start: " + startTime
                    + ", End: " + endTime
                    + ", Status: " + status;
        }
    }

    static class FacilityManager {
        final ArrayList<Equipment> equipmentList = new ArrayList<>();
        final ArrayList<Reservation> reservations = new ArrayList<>();

        /** Adds a piece of equipment to the facility inventory. */
        void addEquipment(Equipment equipment) {
            equipmentList.add(equipment);
        }

        /**
         * Makes a reservation if the equipment is available for the requested period.
         * Returns true if successfully reserved, false if the equipment is unavailable.
         */
        boolean makeReservation(Reservation reservation) {
            if (!isAvailable(reservation.equipmentId,
                    reservation.startTime,
                    reservation.endTime)) {
                return false;
            }
            reservations.add(reservation);
            return true;
        }

        boolean isAvailable(int equipmentId, int startTime, int endTime) {
            for (Reservation res : reservations) {
                /*if (res.equipmentId == equipmentId) {*/
                if (res.status==ReservationStatus.ACTIVE && res.equipmentId == equipmentId) {
                    if (startTime < res.endTime && endTime > res.startTime) {
                        return false;
                    }
                }
            }
            return true;
        }

        boolean cancelReservation(int reservationId) {
            for (Reservation res : reservations) {
                if (res.reservationId == reservationId) {
                    res.status = ReservationStatus.CANCELLED;
                    return true;
                }
            }
            return false;
        }

        public List<Reservation> getReservationsForMember(String memberName) {
            List<Reservation> result = new ArrayList<>();

            for (Reservation res : reservations) {
                if (res.status == ReservationStatus.ACTIVE && res.memberName.equals(memberName)) {
                    result.add(res);
                }
            }

            return result;
        }

        public Map<String, Integer> getEquipmentSummary(int equipmentId) {
            Map<String, Integer> summary = new HashMap<>();

            int totalReservations = 0;
            int totalMinutes = 0;

            for (Reservation res : reservations) {
                if (res.status == ReservationStatus.ACTIVE && res.equipmentId == equipmentId) {
                    totalReservations++;
                    totalMinutes += res.getDuration();
                }
            }

            summary.put("total_reservations", totalReservations);
            summary.put("total_minutes", totalMinutes);

            return summary;
        }
    }

    // These tests are not meant to be exhaustive, and primarily show usage.
    public static class TestSuite {

        @Test
        public void testMakeReservation() {
            FacilityManager manager = new FacilityManager();
            Reservation res = new Reservation(101, "Alice Johnson", 1, 480, 600);
            assertTrue(manager.makeReservation(res));
            assertEquals(1, manager.reservations.size());
        }

        @Test
        public void testConflictDetected() {
            FacilityManager manager = new FacilityManager();
            Reservation res1 = new Reservation(101, "Alice Johnson", 1, 480, 600);
            Reservation res2 = new Reservation(102, "Bob Smith", 1, 540, 660);
            manager.makeReservation(res1);
            assertFalse(manager.makeReservation(res2));
        }

        @Test
        public void testDifferentEquipmentNoConflict() {
            FacilityManager manager = new FacilityManager();
            Reservation res1 = new Reservation(101, "Alice Johnson", 1, 480, 600);
            Reservation res2 = new Reservation(102, "Bob Smith", 2, 480, 600);
            assertTrue(manager.makeReservation(res1));
            assertTrue(manager.makeReservation(res2));
        }

        @Test
        public void testCancelledReservationFreesSlot() {
            FacilityManager manager = new FacilityManager();
            Reservation res1 = new Reservation(101, "Alice Johnson", 1, 480, 600);
            manager.makeReservation(res1);
            assertTrue(manager.cancelReservation(101));
            assertEquals(1, manager.reservations.size());
            assertEquals(ReservationStatus.CANCELLED, res1.status);
            // After cancellation, the same slot should be bookable again
            Reservation res2 = new Reservation(102, "Bob Smith", 1, 480, 600);
            assertTrue(manager.makeReservation(res2));
            assertEquals(2, manager.reservations.size());
        }

        @Test
        public void testGetReservationsForMember() {
            FacilityManager manager = new FacilityManager();
            Reservation res1 = new Reservation(101, "Alice Johnson", 1, 480, 600);
            Reservation res2 = new Reservation(102, "Bob Smith", 1, 660, 720);
            Reservation res3 = new Reservation(103, "Alice Johnson", 2, 720, 840);
            Reservation res4 = new Reservation(104, "Carol White", 2, 480, 540);
            manager.makeReservation(res1);
            manager.makeReservation(res2);
            manager.makeReservation(res3);
            manager.makeReservation(res4);

            List<Reservation> aliceRes = manager.getReservationsForMember("Alice Johnson");
            assertEquals(2, aliceRes.size());
            assertTrue(aliceRes.contains(res1));
            assertTrue(aliceRes.contains(res3));

            // new String(...) ensures comparison uses equals, not ==
            List<Reservation> aliceResByNewString =
                    manager.getReservationsForMember("Alice Johnson");
            assertEquals(2, aliceResByNewString.size());
            assertTrue(aliceResByNewString.contains(res1));
            assertTrue(aliceResByNewString.contains(res3));

            List<Reservation> bobRes = manager.getReservationsForMember("Bob Smith");
            assertEquals(1, bobRes.size());
            assertTrue(bobRes.contains(res2));

            // Member with no reservations
            assertEquals(0, manager.getReservationsForMember("David Brown").size());

            // Cancelled reservations should not appear
            manager.cancelReservation(101);
            List<Reservation> aliceResAfter = manager.getReservationsForMember("Alice Johnson");
            assertEquals(1, aliceResAfter.size());
            assertTrue(aliceResAfter.contains(res3));
        }

        @Test
        public void testGetEquipmentSummary() {
            FacilityManager manager = new FacilityManager();
            Reservation res1 = new Reservation(101, "Alice Johnson", 1, 480, 540);
            Reservation res2 = new Reservation(102, "Bob Smith", 1, 660, 720);
            Reservation res3 = new Reservation(103, "Carol White", 1, 720, 780);
            Reservation res4 = new Reservation(104, "David Brown", 2, 480, 600);
            manager.makeReservation(res1);
            manager.makeReservation(res2);
            manager.makeReservation(res3);
            manager.makeReservation(res4);

            Map<String, Integer> summaryE1 = manager.getEquipmentSummary(1);
            assertEquals(3, (int) summaryE1.get("total_reservations"));
            assertEquals(180, (int) summaryE1.get("total_minutes"));

            Map<String, Integer> summaryE2 = manager.getEquipmentSummary(2);
            assertEquals(1, (int) summaryE2.get("total_reservations"));
            assertEquals(120, (int) summaryE2.get("total_minutes"));

            // Equipment with no reservations
            Map<String, Integer> summaryE3 = manager.getEquipmentSummary(3);
            assertEquals(0, (int) summaryE3.get("total_reservations"));
            assertEquals(0, (int) summaryE3.get("total_minutes"));

            // Cancelled reservations should not be counted
            manager.cancelReservation(101);
            Map<String, Integer> summaryE1After = manager.getEquipmentSummary(1);
            assertEquals(2, (int) summaryE1After.get("total_reservations"));
            assertEquals(120, (int) summaryE1After.get("total_minutes"));
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }
        if (result.wasSuccessful()) {
            System.out.println("All tests passed successfully.");
        } else {
            System.err.println("Tests failed: " + result.getFailureCount() + " test(s) failed.");
            System.exit(1);
        }
    }
}