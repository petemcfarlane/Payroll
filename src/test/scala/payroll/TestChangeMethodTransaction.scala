package payroll

import org.specs2.mutable.Specification

class TestChangeMethodTransaction extends Specification {
  "Test Change Direct Payment" in {
    val empId = 11
    AddCommissionedEmployee(empId, "Steve", "Home", 2500, 3.2).execute()

    ChangeDirectTransaction(empId).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.paymentMethod should beAnInstanceOf[DirectMethod]
  }

  "Test Change Mail Payment" in {
    val empId = 12
    AddCommissionedEmployee(empId, "Sue", "Home", 2500, 3.2).execute()

    ChangeMailTransaction(empId).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.paymentMethod should beAnInstanceOf[MailMethod]
  }

  "Test Change Hold Payment" in {
    val empId = 13
    AddSalariedEmployee(empId, "Smithers", "Work", 500).execute()

    ChangeHoldTransaction(empId).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.paymentMethod should beAnInstanceOf[HoldMethod]
  }
}
