package asn1.ber

class BerBytes(identifier: Identifier, val value: Seq[Byte]) extends DataValue(identifier) {

  override def equals(other: Any) = other match {
    case that: BerBytes => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  def canEqual(other: Any) = other.isInstanceOf[BerBytes]
  override def toString = s"BerBytes($identifier,$value)"

  override def contentBytes = value.length.toByte +: value
}

object BerBytes {
  def apply(identifier: Identifier, value: Seq[Byte]) = new BerBytes(identifier, value)
  def apply(tag: Int, value: Seq[Byte]) = new BerBytes(Identifier(Ber.Universal, tag), value)
}
