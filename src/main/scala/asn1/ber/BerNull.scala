package asn1.ber

object BerNull extends DataValue(Identifier(Ber.Universal, Ber.Null)) {
  override def toString = "BerNull"
  override def contentBytes = Seq(0)
}

object BerEndOfContent extends DataValue(Identifier(Ber.Universal, Ber.EndOfContent)) {
  override def toString = "BerEndOfContent"
  override def contentBytes = Seq(0)
}
