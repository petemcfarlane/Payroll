package payroll

import org.specs2.mutable.Specification

class TestAddEmployee extends Specification {
  "Add Salaried payroll.Employee" >> {
    val empId = 1
    val t = AddSalariedEmployee(empId, "Bob", "Home", 1000.00)
    t.execute()

    val e = GPayrollDatabase.getEmployee(empId).get
    e.name should_== "Bob"

    val sc = e.paymentClassification
    sc should beAnInstanceOf[SalariedClassification]
    sc.asInstanceOf[SalariedClassification].salary should_== 1000.00

    e.paymentSchedule should beAnInstanceOf[MonthlySchedule]

    e.paymentMethod should beAnInstanceOf[HoldMethod]
  }

  "Add Hourly payroll.Employee" >> {
    val empId = 2
    val t = AddHourlyEmployee(empId, "David", "France", 10.00)
    t.execute()

    val e = GPayrollDatabase.getEmployee(empId).get
    e.name should_== "David"

    val hc = e.paymentClassification
    hc should beAnInstanceOf[HourlyClassification]
    hc.asInstanceOf[HourlyClassification].hourlyRate should_== 10.00

    e.paymentSchedule should beAnInstanceOf[WeeklySchedule]

    e.paymentMethod should beAnInstanceOf[HoldMethod]
  }

  "Add Commissioned payroll.Employee" >> {
    val empId = 3
    val t = AddCommissionedEmployee(empId, "Frank", "Germany", 1050.00, 35.00)
    t.execute()

    val e = GPayrollDatabase.getEmployee(empId).get
    e.name should_== "Frank"

    val cc = e.paymentClassification
    cc should beAnInstanceOf[CommissionedClassification]
    cc.asInstanceOf[CommissionedClassification].salary should_== 1050.00
    cc.asInstanceOf[CommissionedClassification].commissionRate should_== 35.00

    e.paymentSchedule should beAnInstanceOf[BiweeklySchedule]

    e.paymentMethod should beAnInstanceOf[HoldMethod]
  }
}
