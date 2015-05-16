package asn1.ber

object BerNull extends DataValue(ClassAndPC(Ber.Universal), Ber.Boolean) {
  override def toBytes = ???
  override def toString = "BerNull"
}

