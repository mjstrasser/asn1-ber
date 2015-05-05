package asn1.ber

class BerBoolean(classAndPc: Byte, val value: Boolean) extends DataValue(classAndPc, Ber.Boolean) {
  def this(value: Boolean) = this(Ber.Universal, value)
  override def toBytes = ???
  override def equals(other: Any) = other match {
    case that: BerBoolean => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  def canEqual(other: Any) = other.isInstanceOf[BerBoolean]
  override def toString = s"BerBoolean($value)"
}

object BerBoolean {

  def apply(value: Boolean) = new BerBoolean(value)

  def decode(classAndPc: Byte, valueOctets: Seq[Byte]): DataValue =
    new BerBoolean(classAndPc, (valueOctets.head & 0xFF) == 0xFF)

}
