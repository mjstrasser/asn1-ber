package asn1.ber

class BerBoolean(val value: Boolean) extends DataValue(Identifier(Ber.Universal, Ber.Boolean)) {

  override def equals(other: Any) = other match {
    case that: BerBoolean => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  def canEqual(other: Any) = other.isInstanceOf[BerBoolean]
  override def toString = s"BerBoolean($value)"

  override def contentBytes = Seq(1, if (value) 0xFF.toByte else 0x00)
}

object BerBoolean {

  def apply(value: Boolean) = new BerBoolean(value)

  def decode(valueOctets: Seq[Byte]): DataValue =
    new BerBoolean((valueOctets.head & 0xFF) == 0xFF)

}
