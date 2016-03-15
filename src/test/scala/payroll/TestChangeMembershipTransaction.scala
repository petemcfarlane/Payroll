package payroll

import org.specs2.mutable.Specification

class TestChangeMembershipTransaction extends Specification {
  "Test Change Membership" in {
    val empId = 14
    val memberId = 7734

    AddHourlyEmployee(empId, "Charles", "Home", 15.25).execute()

    ChangeMemberTransaction(empId, memberId, 99.42).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.affiliation.asInstanceOf[UnionAffiliation].dues should_== 99.42

    GPayrollDatabase.getUnionMember(empId).get should_== e
  }
}
