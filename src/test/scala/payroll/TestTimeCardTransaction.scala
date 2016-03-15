package payroll

import org.specs2.mutable.Specification

class TestTimeCardTransaction extends Specification {
  "Time Card payroll.Transaction" >> {
    val empId = 5
    AddHourlyEmployee(empId, "Bill", "Home", 50.00).execute()

    val t = TimeCardTransaction(20011031, 8.0, empId)
    t.execute()

    val e = GPayrollDatabase.getEmployee(empId).get
    val hc = e.paymentClassification.asInstanceOf[HourlyClassification]
    val tc = hc.getTimeCard(20011031).get
    tc.hours should_== 8.0
  }
}
