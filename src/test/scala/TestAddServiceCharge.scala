import org.specs2.mutable.Specification

class TestAddServiceCharge extends Specification {
  "Service Charge Transaction" >> {
    val empId = 6
    AddHourlyEmployee(empId, "Betty", "Home", 50.00).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    val af = UnionAffiliation(12.5)
    e.setAffiliation(af)
    val memberId = 86
    GPayrollDatabase.addUnionMember(memberId, e)

    val sct = ServiceChargeTransaction(memberId, 20011101, 12.95)
    sct.execute()
    af.getServiceCharge(20011101).get.amount should_== 12.95
  }
}
