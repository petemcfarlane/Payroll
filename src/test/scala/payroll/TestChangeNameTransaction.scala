package payroll

import org.specs2.mutable.Specification

class TestChangeNameTransaction extends Specification {
  "Test Change Name" >> {
    val empId = 7
    AddHourlyEmployee(empId, "Geoff", "Home", 15.25).execute()

    ChangeNameTransaction(empId, "Jeff").execute()

    GPayrollDatabase.getEmployee(empId).get.name should_== "Jeff"
  }
}
