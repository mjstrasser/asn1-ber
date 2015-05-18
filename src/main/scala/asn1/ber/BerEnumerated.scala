package asn1.ber

class BerEnumerated(identifier: Identifier, value: BigInt) extends BerInteger(identifier, value) {
  def this(value: BigInt) = this(Identifier(Ber.Universal, Ber.Enumerated), value)

  override def equals(other: Any) = other match {
    case that: BerEnumerated => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  override def canEqual(other: Any) = other.isInstanceOf[BerEnumerated]
  override def toString = s"BerEnumerated($value)"
}

object BerEnumerated {

  def apply(value: Int) = new BerEnumerated(value)

  def decode(identifier: Identifier, valueOctets: Seq[Byte]) =
    new BerEnumerated(identifier, BerInteger.decodeValue(valueOctets))
}
