package asn1.ber

object BerNull extends DataValue(ClassAndPC(Ber.Universal), Ber.Boolean) {
  override def toBytes = ???
  override def toString = "BerNull"
}

object BerEndOfContent extends DataValue(ClassAndPC(Ber.Universal), Ber.EndOfContent) {
  override def toBytes = ???
  override def toString = "BerEndOfContent"
}
