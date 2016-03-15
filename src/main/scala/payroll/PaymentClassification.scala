package payroll

trait PaymentClassification

case class SalariedClassification(salary: Double) extends PaymentClassification

case class TimeCard(date: Long, hours: Double)

case class HourlyClassification(hourlyRate: Double) extends PaymentClassification {
  var timeCards: Map[Long,TimeCard] = Map()

  def addTimeCard(tc: TimeCard) = timeCards = timeCards + (tc.date -> tc)
  def getTimeCard(date: Long): Option[TimeCard] = timeCards.get(date)
}

case class CommissionedClassification(salary: Double, commissionRate: Double) extends PaymentClassification
