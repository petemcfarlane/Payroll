import org.specs2.mutable.Specification

class TestChangeClassificationTransaction extends Specification {
  "Test Change Hourly Classification" in {
    val empId = 8
    AddCommissionedEmployee(empId, "Steve", "Home", 2500, 3.2).execute()

    ChangeHourlyTransaction(empId, 27.52).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.paymentClassification should beAnInstanceOf[HourlyClassification]
    e.paymentClassification.asInstanceOf[HourlyClassification].hourlyRate should_== 27.52

    e.paymentSchedule should beAnInstanceOf[WeeklySchedule]
  }

  "Test Change Salaried Classification" in {
    val empId = 9
    AddCommissionedEmployee(empId, "Sue", "Home", 2500, 3.2).execute()

    ChangeSalariedTransaction(empId, 52000).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.paymentClassification should beAnInstanceOf[SalariedClassification]
    e.paymentClassification.asInstanceOf[SalariedClassification].salary should_== 52000

    e.paymentSchedule should beAnInstanceOf[MonthlySchedule]
  }

  "Test Change Commissioned Classification" in {
    val empId = 10
    AddSalariedEmployee(empId, "Smithers", "Work", 500).execute()

    ChangeCommissionedClassification(empId, 13000, 500).execute()

    val e = GPayrollDatabase.getEmployee(empId).get

    e.paymentClassification should beAnInstanceOf[CommissionedClassification]
    e.paymentClassification.asInstanceOf[CommissionedClassification].salary should_== 13000
    e.paymentClassification.asInstanceOf[CommissionedClassification].commissionRate should_== 500

    e.paymentSchedule should beAnInstanceOf[BiweeklySchedule]
  }
}
