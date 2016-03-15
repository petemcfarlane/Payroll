trait Affiliation

case class UnionAffiliation(memberId: Int, dues: Double) extends Affiliation {
  var serviceCharges: Map[Long,ServiceCharge] = Map()

  def addServiceCharge(sc: ServiceCharge): Unit = serviceCharges = serviceCharges + (sc.date -> sc)

  def getServiceCharge(date: Long): Option[ServiceCharge] = serviceCharges.get(date)
}