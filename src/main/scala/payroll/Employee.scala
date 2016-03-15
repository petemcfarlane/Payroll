package payroll


case class Employee(empId: Int, var name: String, address: String) {
  var paymentClassification: PaymentClassification = null

  var paymentSchedule: PaymentSchedule = null
  var paymentMethod: PaymentMethod = null
  var affiliation: Affiliation = null
  def setClassification(classification: PaymentClassification) = paymentClassification = classification

  def setSchedule(schedule: PaymentSchedule) = paymentSchedule = schedule
  def setMethod(method: PaymentMethod) = paymentMethod = method
  def setAffiliation(a: Affiliation) = affiliation = a
  def setName(newName: String) = name = newName
}
