trait Transaction {
  def execute()
}

abstract class AddEmployeeTransaction extends Transaction {
  val empId: Int
  val name: String
  val address: String

  def getClassification: PaymentClassification
  def getSchedule: PaymentSchedule

  def execute(): Unit = {
    val pc = getClassification
    val ps = getSchedule
    val pm = new HoldMethod
    val e = Employee(empId, name, address)

    e.setClassification(pc)
    e.setSchedule(ps)
    e.setMethod(pm)
    GPayrollDatabase.addEmployee(empId, e)
  }
}

case class AddSalariedEmployee(empId: Int, name: String, address: String, salary: Double) extends AddEmployeeTransaction {
  def getClassification = SalariedClassification(salary)
  def getSchedule = new MonthlySchedule
}

case class AddHourlyEmployee(empId: Int, name: String, address: String, hourlyRate: Double) extends AddEmployeeTransaction {
  def getClassification = HourlyClassification(hourlyRate)
  def getSchedule = new WeeklySchedule
}

case class AddCommissionedEmployee(empId: Int, name: String, address: String, salary: Double, commissionRate: Double) extends AddEmployeeTransaction {
  def getClassification = CommissionedClassification(salary, commissionRate)
  def getSchedule = new BiweeklySchedule
}

case class DeleteEmployeeTransaction(empId: Int) extends Transaction {
  def execute(): Unit = GPayrollDatabase.deleteEmployee(empId)
}

case class TimeCardTransaction(date: Long, hours: Double, empId: Int) extends Transaction {
  def execute(): Unit = {
    GPayrollDatabase.getEmployee(empId) match {
      case e: Some[Employee] => e.get.paymentClassification match {
        case hc: HourlyClassification => hc.addTimeCard(TimeCard(date, hours))
        case _ => throw new Exception("Tried to add time card to non-hourly employee")
      }
      case None => throw new IllegalArgumentException("No such employee")
    }
  }
}

case class ServiceChargeTransaction(memberId: Int, date: Long, charge: Double) extends Transaction {
  def execute(): Unit = {
    GPayrollDatabase.getUnionMember(memberId) match {
      case e: Some[Employee] => e.get.affiliation match {
        case a: UnionAffiliation => a.addServiceCharge(ServiceCharge(date, charge))
        case _ => throw new Exception("No union affiliation for member")
      }
      case None => throw new Exception("No such member found")
    }
  }
}

abstract class ChangeEmployeeTransaction extends Transaction {
  val empId: Int

  protected def change(e: Employee): Employee

  def execute(): Unit = {
    GPayrollDatabase.getEmployee(empId) match {
      case e: Some[Employee] => {
        val employee = e.get
        GPayrollDatabase.addEmployee(employee.empId, change(employee))
      }
      case None => throw new IllegalArgumentException("No such employee")
    }
  }
}

case class ChangeNameTransaction(empId: Int, newName: String) extends ChangeEmployeeTransaction {
  protected def change(employee: Employee): Employee = {
    employee.setName(newName)
    employee
  }
}