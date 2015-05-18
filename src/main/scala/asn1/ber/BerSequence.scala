package asn1.ber

class BerSequence(value: Seq[DataValue]) extends BerConstructed(Identifier(Ber.Constructed, Ber.Sequence), value) {

  override def equals(other: Any) = other match {
    case that: BerSequence => this.value == that.value
    case _ => false
  }
  override def hashCode = 41 * (41 + value.hashCode)
  override def canEqual(other: Any) = other.isInstanceOf[BerSequence]
  override def toString = s"BerSequence($value)"

}

object BerSequence {

  def apply(value: Seq[DataValue]): DataValue = new BerSequence(value)

  def decode(value: Seq[Byte]): DataValue = new BerSequence(BerConstructed.appendBer(value, Seq()))

}
