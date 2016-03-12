trait PayrollDatabase {
  def getEmployee(empId: Int): Option[Employee]
  def addEmployee(empId: Int, e: Employee)
  def deleteEmployee(empId: Int)
  def addUnionMember(memberId: Int, e: Employee)
  def getUnionMember(memberId: Int): Option[Employee]
}

object GPayrollDatabase extends PayrollDatabase {
  var employees: Map[Int,Employee] = Map()
  var unionMembers: Map[Int,Employee] = Map()

  def getEmployee(empId: Int): Option[Employee] = employees.get(empId)

  def addEmployee(empId: Int, e: Employee): Unit = employees = employees + (empId -> e)

  def deleteEmployee(empId: Int): Unit = employees = employees - empId

  def addUnionMember(memberId: Int, e: Employee) = unionMembers = unionMembers + (memberId -> e)

  def getUnionMember(memberId: Int): Option[Employee] = unionMembers.get(memberId)
}