import org.specs2.mutable.Specification

class TestDeleteEmployee extends Specification {
  "Delete Employee" >> {
    val empId = 4
    AddSalariedEmployee(empId, "Ben", "Home", 1000.00).execute()

    val e = GPayrollDatabase.getEmployee(empId).get
    e.name should_== "Ben"

    val t = DeleteEmployeeTransaction(empId)
    t.execute()
    GPayrollDatabase.getEmployee(empId) should_== None
  }

}
